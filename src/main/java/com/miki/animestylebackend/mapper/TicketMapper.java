package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.model.Ticket;

public interface TicketMapper {
    TicketDto toDto(Ticket ticket);
    Ticket toEntity(TicketDto ticketDto);
}
