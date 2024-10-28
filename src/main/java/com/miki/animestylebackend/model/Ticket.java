package com.miki.animestylebackend.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
