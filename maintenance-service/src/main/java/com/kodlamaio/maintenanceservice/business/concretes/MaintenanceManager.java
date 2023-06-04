package com.kodlamaio.maintenanceservice.business.concretes;

import com.kodlamaio.commonpackage.events.maintenance.MaintenanceCompletedEvent;
import com.kodlamaio.commonpackage.utils.dto.ChangeCarStateRequest;
import com.kodlamaio.commonpackage.utils.enums.CarState;
import com.kodlamaio.commonpackage.utils.kafka.producer.KafkaProducer;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.maintenanceservice.api.clients.CarClient;
import com.kodlamaio.maintenanceservice.api.clients.FilterClient;
import com.kodlamaio.maintenanceservice.business.abstracts.MaintenanceService;
import com.kodlamaio.maintenanceservice.business.dto.requests.CreateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.requests.UpdateMaintenanceRequest;
import com.kodlamaio.maintenanceservice.business.dto.responses.CreateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.GetAllMaintenancesResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.GetMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.dto.responses.UpdateMaintenanceResponse;
import com.kodlamaio.maintenanceservice.business.rules.MaintenanceRules;
import com.kodlamaio.maintenanceservice.entities.Maintenance;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceManager implements MaintenanceService {

    private final MaintenanceRepository repository;
    private final ModelMapperService mapper;
    private final MaintenanceRules rules;
    private final CarClient carClient;
    private final FilterClient filterClient;
    private final KafkaProducer kafkaProducer;

    @Override
    public List<GetAllMaintenancesResponse> getAll() {
        List<Maintenance> maintenanceList = repository.findAll();
        List<GetAllMaintenancesResponse> response = maintenanceList
                .stream()
                .map(maintenance -> mapper.forResponse().map(maintenance, GetAllMaintenancesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetMaintenanceResponse getById(UUID id) {
        rules.checkIfMaintenanceExistsById(id);
        Maintenance maintenance = repository.findById(id).orElseThrow();
        GetMaintenanceResponse response = mapper.forResponse().map(maintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public GetMaintenanceResponse returnCarFromMaintenance(UUID carId) {
        rules.checkIfCarIsNotUnderMaintenance(carId);
        Maintenance maintenance = repository.findByCarIdAndIsCompletedIsFalse(carId);
        maintenance.setCompleted(true);
        maintenance.setEndDate(LocalDateTime.now());
        Maintenance completedMaintenance = repository.save(maintenance);
        kafkaProducer.sendMessage(new MaintenanceCompletedEvent(carId), "maintenance-completed");
        GetMaintenanceResponse response = mapper.forResponse().map(completedMaintenance, GetMaintenanceResponse.class);
        return response;
    }

    @Override
    public CreateMaintenanceResponse add(CreateMaintenanceRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());
        rules.checkIfCarUnderMaintenance(request.getCarId());
        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(UUID.randomUUID());
        maintenance.setStartDate(LocalDateTime.now());
        maintenance.setCompleted(false);
        maintenance.setEndDate(null);
        carClient.changeCarState(new ChangeCarStateRequest(request.getCarId(), CarState.Maintenance));
        filterClient.changeCarState(new ChangeCarStateRequest(request.getCarId(), CarState.Maintenance));
        Maintenance createdMaintenance = repository.save(maintenance);
        CreateMaintenanceResponse response = mapper.forResponse().map(createdMaintenance, CreateMaintenanceResponse.class);
        return response;
    }

    @Override
    public UpdateMaintenanceResponse update(UUID id, UpdateMaintenanceRequest request) {
        rules.checkIfMaintenanceExistsById(id);
        rules.checkIfCarIsNotUnderMaintenance(request.getCarId());
        Maintenance maintenance = mapper.forRequest().map(request, Maintenance.class);
        maintenance.setId(id);
        Maintenance updatedMaintenance = repository.save(maintenance);
        UpdateMaintenanceResponse response = mapper.forResponse().map(updatedMaintenance, UpdateMaintenanceResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfMaintenanceExistsById(id);
        makeCarAvailableIfIsCompletedFalse(id);
        repository.deleteById(id);
    }

    private void makeCarAvailableIfIsCompletedFalse(UUID id) {
        UUID carId = repository.findById(id).get().getCarId();
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            kafkaProducer.sendMessage(new MaintenanceCompletedEvent(carId), "maintenance-completed");
        }
    }

}
