package com.busbooking.backend.controller;

import com.busbooking.backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/seats")
public class AdminSeatController {

    private final BookingService bookingService;

    public AdminSeatController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PutMapping("/{seatId}/reserve")
    public void reserveSeat(@PathVariable Long seatId) {
        bookingService.reserveSeat(seatId);
    }

    @PutMapping("/{seatId}/available")
    public void makeSeatAvailable(@PathVariable Long seatId) {
        bookingService.makeSeatAvailable(seatId);
    }
}