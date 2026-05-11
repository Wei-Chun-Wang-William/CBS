package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "concert_ticket_price", schema = "dbo")
@Data
public class JpaConcertTicketPricePo {
    @Id
    @Column(name = "concert_ticket_price_id", length = 32)
    private String concertTicketPriceId;

    // 多對一關聯：指向 Concert 實體
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private JpaConcertPo concert;

    @Column(name = "area_name", length = 100, nullable = false)
    private String areaName;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime;

    @Version
    @Column(name = "ver", nullable = false)
    private LocalDateTime ver;
}
