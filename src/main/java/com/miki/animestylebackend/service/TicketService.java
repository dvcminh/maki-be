package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.RequestStatus;
import com.miki.animestylebackend.model.Ticket;
import com.miki.animestylebackend.model.TicketType;
import com.miki.animestylebackend.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    Ticket saveTicket(User userId, TicketType ticketType, UUID shopID, UUID driverId, String title, MultipartFile file);

    List<Ticket> getAllTickets();

    PageData<Ticket> filterTicket(TicketType ticketType, RequestStatus ticketStatus, int page, int size, Sort.Direction sort, String sortBy);

    Ticket approveTicket(UUID ticketId);
}
