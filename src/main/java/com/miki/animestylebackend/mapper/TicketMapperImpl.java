package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.TicketData;
import com.miki.animestylebackend.dto.response.TicketDto;
import com.miki.animestylebackend.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketMapperImpl implements TicketMapper{
    private final UserMapper userMapper;
    private final ShopMapper shopMapper;
    private final DriverMapper driverMapper;
    private final FileStorageMapper fileStorageMapper;
    @Override
    public TicketDto toDto(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        TicketData ticketData = toData(ticket);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setSuccess(true);
        ticketDto.setStatus(200);
        ticketDto.setMessage("Ticket Data");
        ticketDto.setTimestamp(LocalDateTime.now());
        ticketDto.setData(ticketData);
        return ticketDto;
    }

    @Override
    public TicketData toData(Ticket ticket) {
        TicketData ticketData = new TicketData();
        ticketData.setId(ticket.getId());
        ticketData.setUser(userMapper.toUserData(ticket.getUser()));
        ticketData.setShop(shopMapper.toShopData(ticket.getShop()));
        ticketData.setDriver(driverMapper.toDriverData(ticket.getDriver()));
        ticketData.setTicketType(ticket.getTicketType());
        ticketData.setTicketStatus(ticket.getTicketStatus());
        ticketData.setFileStorageList(fileStorageMapper.toDto(ticket.getFileStorageList()));

        return ticketData;
    }

    @Override
    public Ticket toEntity(TicketDto ticketDto) {
        return null;
    }
}
