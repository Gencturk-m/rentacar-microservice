package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ChangeCarStateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "FILTER-SERVICE", fallback = FilterClientFallback.class)
public interface FilterClient {

    @PutMapping("/api/filters/change-car-state")
    void changeCarState(@RequestBody ChangeCarStateRequest request);
}

