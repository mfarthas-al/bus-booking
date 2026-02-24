package com.busbooking.backend.controller;

import com.busbooking.backend.dto.ApiResponse;
import com.busbooking.backend.dto.BookingResponseDTO;
import com.busbooking.backend.dto.BookingSearchResponseDTO;
import com.busbooking.backend.entity.Booking;
import com.busbooking.backend.repository.BookingRepository;
import com.busbooking.backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<?> searchBooking(
            @RequestParam String bookingCode) {
        try {
            BookingSearchResponseDTO result = bookingService.findByBookingCode(bookingCode);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "No booking found with that code", null));
        }
    }
}
