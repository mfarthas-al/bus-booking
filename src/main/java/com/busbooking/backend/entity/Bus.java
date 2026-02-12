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

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private BusRoute route;
}
