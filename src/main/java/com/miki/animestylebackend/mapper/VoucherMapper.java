package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.VoucherData;
import com.miki.animestylebackend.dto.response.VoucherDto;
import com.miki.animestylebackend.model.Voucher;

public interface VoucherMapper {
    VoucherDto toDto(Voucher voucher, String message);

    VoucherData toVoucherData(Voucher voucher);
}
