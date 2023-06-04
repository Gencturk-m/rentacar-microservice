package com.kodlamaio.maintenanceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.commonpackage.utils.exceptions.EntityAlreadyExistsException;
import com.kodlamaio.commonpackage.utils.exceptions.EntityNotFoundException;
import com.kodlamaio.maintenanceservice.api.clients.CarClient;
import com.kodlamaio.maintenanceservice.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MaintenanceRules {

    private final MaintenanceRepository repository;
    private final CarClient carClient;


    public void checkIfMaintenanceExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Maintenance not found");
        }
    }

    public void checkIfCarUnderMaintenance(UUID carId) {
        if (repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new EntityAlreadyExistsException("Car is already under maintenance");
        }
    }

    public void checkIfCarIsNotUnderMaintenance(UUID carId) {
        if (!repository.existsByCarIdAndIsCompletedIsFalse(carId)) {
            throw new EntityNotFoundException("Car is not under maintenance");
        }
    }
    public void ensureCarIsAvailable(UUID carId) {
        var response = carClient.checkIfCarAvailable(carId);
        if (!response.isSuccess()) {
            throw new BusinessException(response.getMessage());
        }
    }
}
