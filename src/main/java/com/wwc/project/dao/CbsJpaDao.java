package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaUserPo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CbsJpaDao extends JpaRepository<JpaUserPo, Long> {
    List<JpaUserPo> findAll();
    List<JpaUserPo> findByUserId(String userId);

    Page<JpaUserPo> findAllByOrderByVerDesc(Pageable pageable);
}

