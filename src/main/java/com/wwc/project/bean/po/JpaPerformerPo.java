package com.wwc.project.bean.po;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(name = "performer", schema = "dbo")
@Data
public class JpaPerformerPo {
    @Id
    @Column(name = "performer_id", length = 32)
    private String performerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer_type_id", nullable = false)
    private JpaPerformerTypePo performerType;

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
