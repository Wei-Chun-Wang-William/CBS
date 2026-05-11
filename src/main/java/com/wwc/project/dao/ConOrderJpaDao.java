package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaConOrderPo;
import com.wwc.project.bean.vo.OrderVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ConOrderJpaDao extends JpaRepository<JpaConOrderPo, String> {
    @Modifying
    @Query(value = "INSERT INTO dbo.con_order (" +
            "order_id, user_id, order_status_id, payment_method_id, " +
            "concert_ticket_price_id, unit_price, expired_datetime, " +
            "crt_datetime, ver) " +
            "VALUES (:orderId, :userId, (SELECT s.order_status_id FROM dbo.order_status s WHERE s.code = :orderStatusCode), " +
            "(SELECT pm.payment_method_id FROM dbo.payment_method pm WHERE pm.code = :payMethodCode), "+
            ":concertTicketPriceId, :unitPrice, :expiredDateTime, :crtDateTime, :ver)",
            nativeQuery = true)
    int insertOrderNative(
            @Param("orderId") String orderId,
            @Param("userId") String userId,
            @Param("orderStatusCode") String orderStatusCode,
            @Param("payMethodCode") String payMethodCode,
            @Param("concertTicketPriceId") String concertTicketPriceId,
            @Param("unitPrice") BigDecimal unitPrice,
            @Param("expiredDateTime") LocalDateTime expiredDateTime,
            @Param("crtDateTime")  LocalDateTime crtDateTime,
            @Param("ver") LocalDateTime ver
    );


    @Query(value = "select " +
            "co.order_id as orderId," +
            "co.unit_price as unitPrice," +
            "co.crt_datetime as crtDateTime," +
            "co.payment_datetime as paymentDateTime," +
            "co.expired_datetime as expiredDateTime," +
            "os.code as orderStatusCode," +
            "pm.name as paymentMethodName," +
            "c.title as concertName," +
            "ctp.area_name as areaName " +
            "from dbo.con_order co " +
            "inner join dbo.payment_method pm on co.payment_method_id = pm.payment_method_id " +
            "inner join dbo.order_status os on co.order_status_id = os.order_status_id " +
            "inner join dbo.concert_ticket_price ctp on co.concert_ticket_price_id = ctp.concert_ticket_price_id " +
            "inner join dbo.concert c on ctp.concert_id = c.concert_id " +
            "where co.user_id = :userId " +
//            "AND co.crt_datetime >= NOW() - INTERVAL 1 year " +
            "ORDER BY co.crt_datetime DESC", nativeQuery = true)
    List<OrderVo> findByJpaUserPoOrderByCrtDatetimeDesc(@Param("userId") String userId);


    @Modifying
    @Query(value = "update dbo.con_order set " +
            "order_status_id = (select s.order_status_id from dbo.order_status s where s.code = 'PAYMENT_TIMEOUT') " +
            "where expired_datetime <= now() " +
            "and order_status_id = (select s.order_status_id from dbo.order_status s where s.code = 'PENDING_PAYMENT')"
            ,nativeQuery = true)
    int updateExpiredOrder();

    @Modifying
    @Query(value = "update dbo.con_order set " +
            "order_status_id = (select s.order_status_id from dbo.order_status s where s.code = 'PAID') " +
            "where order_id = :orderId and order_status_id = (select s.order_status_id from dbo.order_status s where s.code = 'PENDING_PAYMENT')"
            ,nativeQuery = true)
    int payOrder(@Param("orderId") String orderId);
}
