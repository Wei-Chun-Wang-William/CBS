package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert_category", schema = "dbo")
@Data
public class JpaConcertCategoryPo {
    @Id
    @Column(name = "concert_category_id", length = 32)
    private String concertCategoryId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private LocalDateTime ver;
}
