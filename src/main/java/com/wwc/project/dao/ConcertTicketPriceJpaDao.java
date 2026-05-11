package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaConcertTicketPricePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertTicketPriceJpaDao extends JpaRepository<JpaConcertTicketPricePo, String> {
    List<JpaConcertTicketPricePo> findByConcertConcertId(String concertId);

    List<JpaConcertTicketPricePo> findByConcertConcertIdOrderByPriceDesc(String concertId);

    /**
     * 庫存-1的方法
     * @param id
     * @return
     */
    @Modifying
    @Query("UPDATE JpaConcertTicketPricePo p " +
            "SET p.availableQuantity = p.availableQuantity - 1, " +
            "    p.ver = CURRENT_TIMESTAMP " +
            "WHERE p.concertTicketPriceId = :id " +
            "AND p.availableQuantity > 0") // 關鍵：確保庫存夠才扣
//    @Modifying
//    @Query(value = "UPDATE dbo.concert_ticket_price SET available_quantity = available_quantity - 1, ver = CURRENT_TIMESTAMP " +
//            "WHERE concert_ticket_price_id = :id AND available_quantity > 0" , nativeQuery = true)
    int decreaseAvailableQuantity(@Param("id") String id);
}
