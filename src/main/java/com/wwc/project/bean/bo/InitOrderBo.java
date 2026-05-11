package com.wwc.project.bean.bo;

import com.wwc.project.bean.po.JpaConOrderPo;
import lombok.Data;

@Data
public class InitOrderBo {
    private JpaConOrderPo conOrderPo;

    private String concertTicketPriceId;
}
