package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.dto.requests.update.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.dto.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.get.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.update.UpdateInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {

    private final InvoiceRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        System.out.println("inside getall");
        List<Invoice> invoices = repository.findAll();
        List<GetAllInvoicesResponse> response = invoices
                .stream()
                .map(invoice -> mapper.forRequest().map(invoice, GetAllInvoicesResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetInvoiceResponse getById(UUID id) {
        return null;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.forRequest().map(request, Invoice.class);
        invoice.setId(null);
        repository.save(invoice);
        CreateInvoiceResponse response = mapper.forRequest().map(invoice, CreateInvoiceResponse.class);

        return response;
    }

    @Override
    public UpdateInvoiceResponse update(UUID id, UpdateInvoiceRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}