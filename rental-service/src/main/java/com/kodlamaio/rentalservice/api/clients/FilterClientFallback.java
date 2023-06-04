package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.commonpackage.utils.dto.ChangeCarStateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FilterClientFallback implements FilterClient {

    @Override
    public void changeCarState(ChangeCarStateRequest request) {
        log.info("FILTER SERVICE IS DOWN");
        throw new RuntimeException("FILTER SERVICE IS DOWN");
    }
}
