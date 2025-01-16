package com.api.EcomTracker.domain.reservations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservations, Long> {
}
