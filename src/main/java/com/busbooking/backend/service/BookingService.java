package com.busbooking.backend.service;

import com.busbooking.backend.dto.BookingResponseDTO;
import com.busbooking.backend.entity.*;
import com.busbooking.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.busbooking.backend.exception.SeatAlreadyBookedException;


@Service
public class BookingService {
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public BookingResponseDTO bookSeat(Long seatId,
                                       String passengerName,
                                       String phoneNumber) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getIsBooked()) {
            throw new SeatAlreadyBookedException("Seat already booked");
        }

        seat.setIsBooked(true);

        Booking booking = new Booking();
        booking.setPassengerName(passengerName);
        booking.setPhoneNumber(phoneNumber);
        booking.setSeat(seat);

        Booking savedBooking = bookingRepository.save(booking);

        return new BookingResponseDTO(
                savedBooking.getBookingCode(),
                savedBooking.getPassengerName(),
                seat.getSeatNumber(),
                seat.getBusSchedule().getTravelDate().toString(),
                seat.getBusSchedule().getDepartureTime().toString(),
                seat.getBusSchedule().getArrivalTime().toString()
        );
    }
}
