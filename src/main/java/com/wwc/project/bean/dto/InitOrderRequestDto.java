package com.wwc.project.bean.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InitOrderRequestDto {
    private String userId;

    private String concertTicketPriceId;

    /**
     * 演唱會單價
     */
    private BigDecimal price;

    /**
     * 手續費
     */
    private BigDecimal handlingFee;

    /**
     * 總價
     */
    private BigDecimal totalPrice;

    private String paymentMethodId;

    private String paymentMethodCode;
}
