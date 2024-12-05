package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.mapper.FileStorageMapper;
import com.miki.animestylebackend.mapper.TicketMapper;
import com.miki.animestylebackend.mapper.UserMapper;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TicketMapper ticketMapper;
    @Override
    public TicketDto saveTicket(User user, String image, TicketType ticketType, UUID shopID, UUID driverId, String title, UUID file) {
    Shop shop = shopID != null ? shopRepository.findById(shopID)
            .orElse(null) : null;

    Driver driver = driverId != null ? driverRepository.findById(driverId)
            .orElse(null) : null;

    // Retrieve the existing FileStorage entity
    FileStorage fileStorage = fileStorageService.findById(file);

    Ticket ticket = Ticket.builder()
            .user(user)
            .ticketType(ticketType)
            .imageUrl(image)
            .shop(shop)
            .ticketStatus(TicketStatus.PENDING)
            .driver(driver)
            .fileStorageList(List.of(fileStorage))
            .build();
    return ticketMapper.toDto(ticketRepository.save(ticket));
}

    @Override
    public TicketDto getTicketsById(UUID ticketId) {
        return ticketRepository.findById(ticketId)
                .map(ticketMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
    }

    @Override
    public PageData<TicketDto> filterTicket(TicketType ticketType, String ticketStatus, int page, int size, Sort.Direction sort, String sortBy) {
        TicketStatus ticketStatus1 = switch (ticketStatus) {
            case "Pending" -> TicketStatus.PENDING;
            case "Approved" -> TicketStatus.APPROVED;
            case "Rejected" -> TicketStatus.REJECTED;
            default -> null;
        };
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort, sortBy));
        Page<Ticket> tickets = ticketRepository.findByTicketTypeAndTicketStatus(ticketType, ticketStatus1, pageable);
        return new PageData<>(tickets.map(ticketMapper::toDto), "Filter ticket successfully");
    }

    @Override
    public TicketDto approveTicket(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        if (ticket.getTicketStatus() == TicketStatus.APPROVED) {
            throw new RuntimeException("Ticket already approved");
        }

        if (ticket.getTicketType() == TicketType.SHOP_REQUEST) {
            Shop shop = shopRepository.findById(ticket.getShop().getId())
                    .orElseThrow(() -> new NotFoundException("Shop not found"));
            shop.setVerified(true);
            shopRepository.save(shop);
        } else {
            Driver driver = driverRepository.findById(ticket.getDriver().getId())
                    .orElseThrow(() -> new NotFoundException("Driver not found"));
            driver.setVerified(true);
            driverRepository.save(driver);
        }

        ticket.setTicketStatus(TicketStatus.APPROVED);
        return ticketMapper.toDto(ticketRepository.save(ticket));
    }
}