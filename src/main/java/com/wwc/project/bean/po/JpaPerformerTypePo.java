package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "performer_type", schema = "dbo")
@Data
public class JpaPerformerTypePo {
    @Id
    @Column(name = "performer_type_id", length = 32)
    private String performerTypeId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private LocalDateTime ver;
}
