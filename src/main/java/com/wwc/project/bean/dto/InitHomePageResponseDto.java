package com.wwc.project.bean.dto;

import com.wwc.project.bean.vo.CityVo;
import com.wwc.project.bean.vo.ConcertStatusVo;
import com.wwc.project.bean.vo.PaymentMethodVo;
import com.wwc.project.bean.vo.VenueVo;
import lombok.Data;

import java.util.List;

@Data
public class InitHomePageResponseDto {
    List<CityVo> cityVoList;
    List<VenueVo> venueVoList;
    List<ConcertStatusVo> concertStatusVoList;
    List<PaymentMethodVo> paymentMethodVoList;
}
