package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.CarInfoResponse;
import com.kodlamaio.commonpackage.utils.dto.ChangeCarStateRequest;
import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
public interface CarClient {
    @GetMapping(value = "/api/cars/check-car-available/{carId}")
    @Retry(name = "flightSearch")
    ClientResponse checkIfCarAvailable(@PathVariable UUID carId);

    @PutMapping(value = "/api/cars/change-car-state")
    @Retry(name = "flightSearch")
    void changeCarState(@RequestBody ChangeCarStateRequest request);

    @GetMapping(value = "/api/cars/getCarInfo/{carId}")
    CarInfoResponse getCarInfo(@PathVariable UUID carId);
}
