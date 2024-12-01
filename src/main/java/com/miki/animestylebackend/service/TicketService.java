package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.model.TicketStatus;
import com.miki.animestylebackend.model.Ticket;
import com.miki.animestylebackend.model.TicketType;
import com.miki.animestylebackend.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    TicketDto saveTicket(User userId, String image, TicketType ticketType, UUID shopID, UUID driverId, String title, UUID file);


    PageData<TicketDto> filterTicket(TicketType ticketType, String ticketStatus, int page, int size, Sort.Direction sort, String sortBy);

    TicketDto approveTicket(UUID ticketId);

    TicketDto getTicketsById(UUID ticketId);
}
