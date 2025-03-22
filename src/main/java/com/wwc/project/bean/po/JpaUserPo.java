package com.wwc.project.bean.po;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "con_users")
public class JpaUserPo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "crt_datetime")
    private String crtDatetime;

    @Column(name = "ver")
    private String ver;
}