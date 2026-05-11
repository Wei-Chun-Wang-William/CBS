package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "concert", schema = "dbo")
@Data
public class JpaConcertPo {
    @Id
    @Column(name = "concert_id", length = 32)
    private String concertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_id", nullable = false)
    private JpaPerformerPo performer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private JpaVenuePo venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_category_id", nullable = false)
    private JpaConcertCategoryPo concertCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_status_id", nullable = false)
    private JpaConcertStatusPo concertStatus;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "concert_date", nullable = false)
    private LocalDateTime concertDate;

    @Column(length = 100)
    private String description;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private LocalDateTime ver;
}
