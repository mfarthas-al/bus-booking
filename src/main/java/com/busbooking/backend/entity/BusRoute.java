package com.busbooking.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bus_routes")
@Getter
@Setter
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String routeNumber;

    @Column(nullable = false)
    private String fromCity;

    @Column(nullable = false)
    private String toCity;
}
