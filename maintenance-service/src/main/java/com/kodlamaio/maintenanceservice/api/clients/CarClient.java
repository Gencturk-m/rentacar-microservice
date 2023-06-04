package com.kodlamaio.maintenanceservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ChangeCarStateRequest;
import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
public interface CarClient {

    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    @Retry(name = "carAvailability")
    ClientResponse checkIfCarAvailable(@PathVariable UUID carId);

    @PutMapping(value = "/api/cars/change-car-state")
    @Retry(name = "carAvailability")
    void changeCarState(@RequestBody ChangeCarStateRequest request);
}
