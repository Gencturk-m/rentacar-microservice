package com.kodlamaio.filterservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final FilterService service;

    @KafkaListener(
            topics = "rental-deleted",
            groupId = "filter-rental-delete"
    )
    public void consume(RentalDeletedEvent event) {
        var filter = service.getByCarId(event.getCarId());
        filter.setState("Available");
        service.add(filter);
        log.info("Rental deleted event consumed {}", event);
    }
}
