package com.kodlamaio.invoiceservice.business.kafkaConsumer;

import com.kodlamaio.commonpackage.events.rental.RentalCreatedEvent;
import com.kodlamaio.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalConsumer {

    private final ModelMapperService mapper;
    private final InvoiceRepository repository;

    @KafkaListener(
            topics = "rental-created",
            groupId = "invoice-rental-create"
    )
    public void consume(RentalCreatedEvent event) {

        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        invoice.setId(UUID.randomUUID());
        repository.save(invoice);

        log.info("Rental created event consumed {}", event);
    }
}
