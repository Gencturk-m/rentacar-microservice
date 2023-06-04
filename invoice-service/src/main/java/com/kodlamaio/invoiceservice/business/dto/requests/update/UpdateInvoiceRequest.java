package com.kodlamaio.invoiceservice.business.dto.requests.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceRequest {
    @NotBlank
    private String cardHolder;
    @NotBlank
    private String modelName;
    @NotBlank
    private String brandName;
    @NotBlank
    private String plate;
    @Min(1996)
    private int modelYear;
    @Min(0)
    private double dailyPrice;
    @Min(0)
    private double totalPrice;
    @Min(0)
    private int rentedForDays;
    private LocalDateTime rentedAt;
}