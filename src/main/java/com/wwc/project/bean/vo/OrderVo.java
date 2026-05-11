package com.wwc.project.bean.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 對接顧客查詢訂單的介面
 */
public interface OrderVo {
    String getOrderId();
    BigDecimal getUnitPrice();
    LocalDateTime getCrtDateTime();
    LocalDateTime getPaymentDateTime();
    LocalDateTime getExpiredDateTime();
    String getOrderStatusCode();
    String getPaymentMethodName();
    String getConcertName();
    String getAreaName();
}
