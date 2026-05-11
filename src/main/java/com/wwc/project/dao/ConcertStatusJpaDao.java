package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaConcertStatusPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertStatusJpaDao extends JpaRepository<JpaConcertStatusPo, String> {
}
