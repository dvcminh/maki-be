package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.mapper.FileStorageMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.DriverRepository;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import com.miki.animestylebackend.repository.jpa.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileStorageMapper fileStorageMapper;

    @Override
    public Ticket saveTicket(User user, TicketType ticketType, UUID shopID, UUID driverId, String title, MultipartFile file) {
        Shop shop = shopRepository.findById(shopID)
                .orElseThrow(() -> new NotFoundException("Shop not found"));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new NotFoundException("Driver not found"));

        FileStorageDto fileStorage = fileStorageService.saveFile(user, file, title);

        Ticket ticket = Ticket.builder()
                .user(user)
                .ticketType(ticketType)
                .shop(shop)
                .driver(driver)
                .fileStorageList(List.of(fileStorageMapper.toModel(fileStorage)))
                .build();
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public PageData<Ticket> filterTicket(TicketType ticketType, RequestStatus ticketStatus, int page, int size, Sort.Direction sort, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<Ticket> tickets = ticketRepository.findByTicketTypeAndTicketStatus(ticketType, ticketStatus, pageable);
        return new PageData<>(tickets, "Filter ticket successfully");
    }

    @Override
    public Ticket approveTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        ticket.setTicketStatus(RequestStatus.APPROVED);
        return ticketRepository.save(ticket);
    }
}