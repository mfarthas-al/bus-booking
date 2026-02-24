package com.busbooking.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminBookingResponseDTO {

    private Long id;
    private String bookingCode;
    private String passengerName;
    private String phoneNumber;
    private String busNumber;
    private String route;
    private Integer seatNumber;
    private String travelDate;
    private String departureTime;
    private String arrivalTime;
}