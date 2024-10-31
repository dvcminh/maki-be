package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.FileStorageMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.FileStorageRepository;
import com.miki.animestylebackend.service.FileStorageService;
import com.miki.animestylebackend.service.TicketService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController extends BaseController {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileStorageRepository fileStorageRepository;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private FileStorageMapper fileStorageMapper;
    @PostMapping("/create")
    public Ticket createTicket(@RequestParam("file") MultipartFile file,
                               @RequestParam("ticketType") TicketType ticketType,
                               @RequestParam("title") String titleFile,
                               @RequestParam(value = "shopid") @Nullable UUID shopId,
                               @RequestParam(value = "driverid") @Nullable UUID driverId) {
        User user = getCurrentUser();

        return ticketService.saveTicket(user, ticketType, shopId, driverId, titleFile, file);
    }

    @GetMapping("/getAll")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/filterTicket")
    public PageData<Ticket> filterTicket(@RequestParam("ticketType") TicketType ticketType,
                                         @RequestParam("ticketStatus") RequestStatus ticketStatus,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                         @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy) {
        return ticketService.filterTicket(ticketType, ticketStatus, page, size, sort, sortBy);
    }

    @PostMapping("/approveTicket")
    public Ticket approveTicket(@RequestParam("ticketId") UUID ticketId) {
        return ticketService.approveTicket(ticketId);
    }
}