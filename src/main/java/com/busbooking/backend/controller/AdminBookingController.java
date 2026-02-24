package com.busbooking.backend.controller;

import com.busbooking.backend.dto.AdminBookingResponseDTO;
import com.busbooking.backend.repository.BookingRepository;
import com.busbooking.backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminBookingController {

    private final BookingService bookingService;

    public AdminBookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
    }

    //view all bookings
    @GetMapping
    public List<AdminBookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

//    @PutMapping("/{id}")
//    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking updateBooking) {
//        Booking booking = bookingRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//
//        booking.setPassengerName(updateBooking.getPassengerName());
//        booking.setPhoneNumber(updateBooking.getPhoneNumber());
//    }

    @GetMapping("/by-seat/{seatId}")
    public Long getBookingBySeat(@PathVariable Long seatId) {
        return bookingService.getBookingIdBySeat(seatId);
    }

    //update seat
    @PutMapping("/{id}/change-seat")
    public void changeSeat(
        @PathVariable Long id,
        @RequestBody Map<String, Long> request) {

        bookingService.changeSeat(
            id,
            request.get("newSeatId")
        );
    }
    //cancel booking
    @DeleteMapping("/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "Booking cancelled successfully";
    }
}
