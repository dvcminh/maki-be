package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.mapper.FileStorageMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.FileStorageRepository;
import com.miki.animestylebackend.service.FileStorageService;
import com.miki.animestylebackend.service.TicketService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
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
    public TicketDto createTicket(@RequestParam("file") UUID file,
                                  @RequestParam("ticketType") TicketType ticketType,
                                  @RequestParam("title") String titleFile,
                                  @RequestParam("iamge") String image,
                                  @RequestParam(value = "shopid") @Nullable UUID shopId,
                                  @RequestParam(value = "driverid") @Nullable UUID driverId) {
        User user = getCurrentUser();

        return ticketService.saveTicket(user, image, ticketType, shopId, driverId, titleFile, file);
    }

    @GetMapping("/getById/{ticketId}")
    public TicketDto getTicketsById(@PathVariable("ticketId") UUID ticketId) {
        return ticketService.getTicketsById(ticketId);
    }

    @GetMapping("/filterTicket")
    public PageData<TicketDto> filterTicket(@RequestParam("ticketType") TicketType ticketType,
                                         @RequestParam("ticketStatus") TicketStatus ticketStatus,
                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @RequestParam(value = "sort", required = false, defaultValue = "ASC") Sort.Direction sort,
                                         @RequestParam(value = "sortBy", required = false, defaultValue = "createdAt") String sortBy) {
        return ticketService.filterTicket(ticketType, ticketStatus, page, size, sort, sortBy);
    }

    @PostMapping("/approveTicket/{ticketId}")
    public TicketDto approveTicket(@PathVariable("ticketId") UUID ticketId) {
        return ticketService.approveTicket(ticketId);
    }
}