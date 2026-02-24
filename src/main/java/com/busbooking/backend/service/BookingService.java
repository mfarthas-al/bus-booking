package com.busbooking.backend.service;

import com.busbooking.backend.dto.AdminBookingResponseDTO;
import com.busbooking.backend.dto.BookingResponseDTO;
import com.busbooking.backend.dto.BookingSearchResponseDTO;
import com.busbooking.backend.entity.*;
import com.busbooking.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.busbooking.backend.exception.SeatAlreadyBookedException;

import java.util.List;


@Service
public class BookingService {
    private final SeatRepository seatRepository;
    private final BookingRepository bookingRepository;

    public BookingService(SeatRepository seatRepository, BookingRepository bookingRepository) {
        this.seatRepository = seatRepository;
        this.bookingRepository = bookingRepository;
    }

    public Long getBookingIdBySeat(Long seatId) {

        Booking booking = bookingRepository.findBySeatId(seatId)
                .orElseThrow(() -> new RuntimeException("Booking not found for seat"));

        return booking.getId();
    }

    public List<AdminBookingResponseDTO> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(booking -> {

                    Seat seat = booking.getSeat();
                    BusSchedule schedule = seat.getBusSchedule();
                    Bus bus = schedule.getBus();

                    return new AdminBookingResponseDTO(
                            booking.getId(),
                            booking.getBookingCode(),
                            booking.getPassengerName(),
                            booking.getPhoneNumber(),
                            bus.getBusNumber(),
                            bus.getFromCity()
                                    + " → " +
                                    bus.getToCity(),
                            seat.getSeatNumber(),
                            schedule.getTravelDate().toString(),
                            schedule.getDepartureTime().toString(),
                            schedule.getArrivalTime().toString()
                    );
                })
                .toList();
    }

    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not Found"));

        Seat seat = booking.getSeat();

        seat.setStatus(SeatStatus.AVAILABLE);

        bookingRepository.delete(booking);
    }

    @Transactional
    public BookingResponseDTO bookSeat(Long seatId,
                                       String passengerName,
                                       String phoneNumber) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() != SeatStatus.AVAILABLE) {
            throw new SeatAlreadyBookedException("Seat not available");
        }

        seat.setStatus(SeatStatus.BOOKED);

        Booking booking = new Booking();
        booking.setPassengerName(passengerName);
        booking.setPhoneNumber(phoneNumber);
        booking.setSeat(seat);

        Booking savedBooking = bookingRepository.save(booking);

        return new BookingResponseDTO(
                savedBooking.getBookingCode(),
                savedBooking.getPassengerName(),
                seat.getBusSchedule().getBus().getBusNumber(),
                seat.getBusSchedule().getBus().getFromCity()
                        + " → " +
                        seat.getBusSchedule().getBus().getToCity(),
                seat.getSeatNumber(),
                seat.getBusSchedule().getTravelDate().toString(),
                seat.getBusSchedule().getDepartureTime().toString(),
                seat.getBusSchedule().getArrivalTime().toString()
        );
    }

    @Transactional
    public void reserveSeat(Long seatId) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() == SeatStatus.BOOKED) {
            throw new RuntimeException("Cannot reserve a booked seat");
        }

        seat.setStatus(SeatStatus.RESERVED);
    }

    @Transactional
    public void makeSeatAvailable(Long seatId) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.getStatus() == SeatStatus.BOOKED) {
            throw new RuntimeException("Cancel booking first");
        }

        seat.setStatus(SeatStatus.AVAILABLE);
    }

    @Transactional
    public void changeSeat(Long bookingId, Long newSeatId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Seat oldSeat = booking.getSeat();

        Seat newSeat = seatRepository.findById(newSeatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        // Safety: same schedule check
        if (!oldSeat.getBusSchedule().getId()
                .equals(newSeat.getBusSchedule().getId())) {
            throw new RuntimeException("Cannot change seat to different schedule");
        }

        // New seat must be AVAILABLE
        if (newSeat.getStatus() != SeatStatus.AVAILABLE) {
            throw new RuntimeException("Selected seat is not available");
        }

        // Free old seat
        oldSeat.setStatus(SeatStatus.AVAILABLE);

        // Assign new seat
        newSeat.setStatus(SeatStatus.BOOKED);

        booking.setSeat(newSeat);
    }

    public BookingSearchResponseDTO findByBookingCode(String bookingCode) {

        Booking booking = bookingRepository.findByBookingCode(bookingCode)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Seat seat = booking.getSeat();
        BusSchedule schedule = seat.getBusSchedule();
        Bus bus = schedule.getBus();

        return new BookingSearchResponseDTO(
                booking.getBookingCode(),
                booking.getPassengerName(),
                booking.getPhoneNumber(),
                bus.getBusNumber(),
                bus.getFromCity() + " → " +
                        bus.getToCity(),
                seat.getSeatNumber(),
                schedule.getTravelDate().toString(),
                schedule.getDepartureTime().toString(),
                schedule.getArrivalTime().toString()
        );
    }
}
