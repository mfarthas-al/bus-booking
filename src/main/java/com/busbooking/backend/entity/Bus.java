package com.busbooking.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "buses")
@Getter
@Setter
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String busNumber;

    @Column(nullable = false)
    private Integer seatCapacity;

    @Column(nullable = false)
    private String busType; // AC / Non-AC

    @Column(nullable = false)
    private String routeNumber;

    @Column(nullable = false)
    private String fromCity;

    @Column(nullable = false)
    private String toCity;
}
