package com.wwc.project.bean.dto;

import com.wwc.project.bean.vo.ConcertTicketPriceVo;
import lombok.Data;

import java.util.List;

@Data
public class GetConcertTicketPriceResponseDto {
    private List<ConcertTicketPriceVo> concertTicketPriceVoList;
}
