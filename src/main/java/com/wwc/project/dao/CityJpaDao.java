package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaCityPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityJpaDao extends JpaRepository<JpaCityPo, String> {

}
