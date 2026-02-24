package com.busbooking.backend.dto;

public class BookingResponseDTO {

    private String bookingCode;
    private String passengerName;
    private String busNumber;
    private String route;
    private Integer seatNumber;
    private String travelDate;
    private String departureTime;
    private String arrivalTime;

    public BookingResponseDTO(String bookingCode,
                              String passengerName,
                              String busNumber,
                              String route,
                              Integer seatNumber,
                              String travelDate,
                              String departureTime,
                              String arrivalTime) {
        this.bookingCode = bookingCode;
        this.passengerName = passengerName;
        this.busNumber = busNumber;
        this.route = route;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getBookingCode() { return bookingCode; }
    public String getPassengerName() { return passengerName; }
    public String getBusNumber() { return busNumber; }
    public String getRoute() { return route; }
    public Integer getSeatNumber() { return seatNumber; }
    public String getTravelDate() { return travelDate; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
}
