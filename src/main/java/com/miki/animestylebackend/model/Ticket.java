package com.miki.animestylebackend.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseEntity {
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.STRING)
    private TicketType ticketType;
    @OneToMany(mappedBy = "ticket")
    private List<FileStorage> fileStorageList;
    @OneToOne
    @Nullable
    private Shop shop;
    @OneToOne
    @Nullable
    private Driver driver;
}
