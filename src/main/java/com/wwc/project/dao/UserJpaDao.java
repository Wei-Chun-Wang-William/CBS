package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaUserPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface UserJpaDao extends JpaRepository<JpaUserPo, Long> {
    List<JpaUserPo> findAll();

    List<JpaUserPo> findByUserId(String userId);

    Page<JpaUserPo> findAllByOrderByVerDesc(Pageable pageable);

    List<JpaUserPo> findByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO dbo.con_users (user_id, user_name, email, phone, password, crt_datetime, ver) " +
            "VALUES (:userId, :userName, :email, :phone, :password, :crtDatetime, :ver)",
            nativeQuery = true)
    int createUser(
            @Param("userId") String userId,
            @Param("userName") String userName,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("password") String password,
            @Param("crtDatetime") LocalDateTime crtDatetime,
            @Param("ver") LocalDateTime ver
    );

}

