package com.wwc.project.bean.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class JpaOrderStatusPo{
    @Id
    @Column(name = "order_status_id", length = 32, nullable = false)
    private String orderStatusId;

    @Column(name = "\"name\"", length = 100, nullable = false)
    private String name;

    @Column(name = "code", length = 100, nullable = false)
    private String code;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime;

    @Column(name = "ver", nullable = false)
    private LocalDateTime ver;
}
