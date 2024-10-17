package com.miki.animestylebackend.dto.response;

import com.miki.animestylebackend.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopDto extends BaseDto<ShopData> {

}
