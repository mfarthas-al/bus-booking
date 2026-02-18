package com.busbooking.backend.controller;

import com.busbooking.backend.dto.ApiResponse;
import com.busbooking.backend.dto.BookingResponseDTO;
import com.busbooking.backend.entity.Booking;
import com.busbooking.backend.repository.BookingRepository;
import com.busbooking.backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService,
                             BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ApiResponse<BookingResponseDTO> bookSeat(
            @RequestParam Long seatId,
            @RequestParam String passengerName,
            @RequestParam String phoneNumber
    ) {
        BookingResponseDTO response =
                bookingService.bookSeat(seatId, passengerName, phoneNumber);

        return new ApiResponse<>(
                true,
                "Seat booked successfully",
                response
        );
    }
}
