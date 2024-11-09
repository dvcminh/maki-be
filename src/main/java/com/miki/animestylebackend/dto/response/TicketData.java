package com.miki.animestylebackend.dto.response;

import com.miki.animestylebackend.dto.FileStorageDto;
import com.miki.animestylebackend.model.TicketStatus;
import com.miki.animestylebackend.model.TicketType;
import lombok.Data;

import java.util.List;

@Data
public class TicketData {
    private UserData user;
    private ShopData shop;
    private DriverData driver;
    private TicketType ticketType;
    private TicketStatus ticketStatus;
    private List<FileStorageDto> fileStorageList;
}
