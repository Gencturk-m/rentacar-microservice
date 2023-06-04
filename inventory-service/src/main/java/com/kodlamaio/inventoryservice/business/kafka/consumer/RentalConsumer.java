package com.kodlamaio.inventoryservice.business.kafka.consumer;

import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.commonpackage.utils.enums.CarState;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private final CarService service;

    @KafkaListener(
            topics = "rental-deleted",
            groupId = "inventory-rental-delete"
    )
    public void consume(RentalDeletedEvent event) {
        service.changeStateByCarId(event.getCarId(), CarState.Available);
        log.info("Rental deleted event consumed {}", event);
    }
}
