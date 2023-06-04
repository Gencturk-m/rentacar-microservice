package com.kodlamaio.inventoryservice.entities;

import com.kodlamaio.commonpackage.utils.enums.CarState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int modelYear;
    private String plate;
    @Enumerated(EnumType.STRING)
    private CarState state;
    private double dailyPrice;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
}
