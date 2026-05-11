package com.wwc.project.bean.po;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "con_order", schema = "dbo")
public class JpaConOrderPo {
    @Id
    @Column(name = "order_id", length = 32, nullable = false)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private JpaUserPo jpaUserPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", referencedColumnName = "code", nullable = false)
    private JpaOrderStatusPo jpaOrderStatusPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private JpaPaymentMethodPo jpaPaymentMethodPo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_ticket_price_id", nullable = false)
    private JpaConcertTicketPricePo jpaConcertTicketPricePo;

    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "payment_datetime")
    private LocalDateTime paymentDatetime;

    @Column(name = "expired_datetime", nullable = false)
    private LocalDateTime expiredDatetime;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime;

    @Version // 若此欄位用於樂觀鎖 (Optimistic Locking)
    @Column(name = "ver", nullable = false)
    private LocalDateTime ver;
}
