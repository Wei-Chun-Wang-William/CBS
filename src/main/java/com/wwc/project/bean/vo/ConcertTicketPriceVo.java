package com.wwc.project.bean.vo;

import com.wwc.project.bean.po.JpaConcertPo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertTicketPriceVo {

    private String concertTicketPriceId;

    private String concertId;

    private String areaName;

    private BigDecimal price;

    private int availableQuantity;

}
