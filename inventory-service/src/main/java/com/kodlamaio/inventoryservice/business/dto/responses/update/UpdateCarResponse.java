package com.kodlamaio.inventoryservice.business.dto.responses.update;

import com.kodlamaio.commonpackage.utils.enums.CarState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarResponse {
    private UUID id;
    private UUID modelId;
    private int modelYear;
    private String plate;
    private CarState state;
    private double dailyPrice;
}
