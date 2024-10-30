package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.RequestStatus;
import com.miki.animestylebackend.model.Ticket;
import com.miki.animestylebackend.model.TicketType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Page<Ticket> findByTicketTypeAndTicketStatus(TicketType ticketType, RequestStatus ticketStatus, Pageable pageable);
}