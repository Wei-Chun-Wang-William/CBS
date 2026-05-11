package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaPaymentMethodPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodJpaDao extends JpaRepository<JpaPaymentMethodPo, String> {

}
