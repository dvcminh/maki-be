package com.miki.animestylebackend.model;

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
    private String imageUrl;
    @ManyToOne
    private User user;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    @Nullable
    private Shop shop;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    @Nullable
    private Driver driver;
    @Enumerated(value = EnumType.STRING)
    private TicketType ticketType;
    @Enumerated(value = EnumType.STRING)
    private TicketStatus ticketStatus;
    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<FileStorage> fileStorageList;
}

