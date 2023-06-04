package com.kodlamaio.maintenanceservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "maintenances")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID carId;

    private String description;

    private boolean isCompleted;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
