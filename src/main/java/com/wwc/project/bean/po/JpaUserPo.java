package com.wwc.project.bean.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "con_users", schema = "dbo")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JpaUserPo {
    @Id
    @Column(name = "user_id", length = 32)
    private String userId;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 10)
    private String phone;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "crt_datetime", nullable = false, updatable = false)
    private LocalDateTime crtDatetime = LocalDateTime.now();

    @Version
    @Column(nullable = false)
    private LocalDateTime ver;
}