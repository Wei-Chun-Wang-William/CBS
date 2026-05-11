package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaVenuePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueJpaDao extends JpaRepository<JpaVenuePo, String> {

}
