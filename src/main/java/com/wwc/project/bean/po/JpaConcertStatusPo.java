package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert_status", schema = "dbo")
@Data
public class JpaConcertStatusPo {
    @Id
    @Column(name = "concert_status_id", length = 32)
    private String concertStatusId;

    @Column(nullable = false, length = 100)
    private String name; // 顯示名稱，如：已上架

    @Column(nullable = false, length = 100)
    private String code; // 系統代碼，如：PUBLISHED

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private LocalDateTime ver;
}
