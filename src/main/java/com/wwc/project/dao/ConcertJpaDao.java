package com.wwc.project.dao;

import com.wwc.project.bean.po.JpaConcertPo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertJpaDao extends JpaRepository<JpaConcertPo, String> {
    @Query("""
        SELECT c FROM JpaConcertPo c 
        WHERE (:title IS NULL OR c.title LIKE %:title%)
          AND (:cityId IS NULL OR c.venue.city.cityId = :cityId)
          AND (:venueId IS NULL OR c.venue.venueId = :venueId)
          AND (:categoryId IS NULL OR c.concertCategory.concertCategoryId = :categoryId)
          AND (:statusCode IS NULL OR c.concertStatus.code = :statusCode)
          AND (:performerId IS NULL OR c.performer.performerId = :performerId)
          AND (CAST(:startDate AS localdatetime) IS NULL OR c.concertDate >= :startDate)
          AND (CAST(:endDate AS localdatetime) IS NULL OR c.concertDate <= :endDate)
        ORDER BY c.concertDate ASC
        """)
    List<JpaConcertPo> findByDynamicCriteria(
            @Param("title") String title,
            @Param("cityId") String cityId,
            @Param("venueId") String venueId,
            @Param("categoryId") String categoryId,
            @Param("statusCode") String statusCode,
            @Param("performerId") String performerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
