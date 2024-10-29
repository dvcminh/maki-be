package com.miki.animestylebackend.model;

import jakarta.persistence.ManyToOne;

public class Notification extends BaseEntity {
    @ManyToOne
    private User user;
}
