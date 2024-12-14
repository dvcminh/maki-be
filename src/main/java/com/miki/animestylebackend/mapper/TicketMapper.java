package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.TicketData;
import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.model.Ticket;

public interface TicketMapper {
    TicketDto toDto(Ticket ticket);
    TicketData toData(Ticket ticket);
    Ticket toEntity(TicketDto ticketDto);
}
