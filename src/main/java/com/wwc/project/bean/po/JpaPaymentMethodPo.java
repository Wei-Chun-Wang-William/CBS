package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment_method", schema = "dbo")
public class JpaPaymentMethodPo {
    @Id
    @Column(name = "payment_method_id", length = 32, nullable = false)
    private String paymentMethodId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime;

    @Version
    @Column(name = "ver", nullable = false)
    private LocalDateTime ver;
}
