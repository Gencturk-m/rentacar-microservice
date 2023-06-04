package com.kodlamaio.invoiceservice.business.rules;

import com.kodlamaio.commonpackage.utils.exceptions.BusinessException;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {
    private final InvoiceRepository repository;

    public void checkIfInvoiceExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Invoice Not Found");
        }
    }


}
