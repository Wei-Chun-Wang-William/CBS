package com.wwc.project.assembler;

import com.wwc.project.bean.bo.InitOrderBo;
import com.wwc.project.bean.bo.LoginBo;
import com.wwc.project.bean.dto.InitOrderRequestDto;
import com.wwc.project.bean.dto.LoginRequestDto;
import com.wwc.project.bean.dto.LoginResponseDto;
import com.wwc.project.bean.po.*;
import com.wwc.project.bean.vo.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class CbsAssembler {
    public LoginResponseDto toLoginResponseDto(LoginBo loginBo) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setIsLoginSuccess(loginBo.getIsLoginSuccess());
        if (loginBo.getDbJpaUserPo() != null) {
            loginResponseDto.getUserInfo().setUserId(loginBo.getDbJpaUserPo().getUserId() == null ? "" : loginBo.getDbJpaUserPo().getUserId());
            loginResponseDto.getUserInfo().setUserName(loginBo.getDbJpaUserPo().getUserName() == null ? "" : loginBo.getDbJpaUserPo().getUserName());
            loginResponseDto.getUserInfo().setEmail(loginBo.getDbJpaUserPo().getEmail() == null ? "" : loginBo.getDbJpaUserPo().getEmail());
        }
        return loginResponseDto;
    }

    public LoginBo toLoginBo(LoginRequestDto loginRequestDto) {
        LoginBo loginBo = new LoginBo();
        loginBo.setInputJpaUserPo(loginRequestDto.getUserInfo());
        return loginBo;
    }

    public ConcertVo toConcertVo(JpaConcertPo concert){
        return ConcertVo.builder()
                .concertId(concert.getConcertId())
                .title(concert.getTitle())
                .description(concert.getDescription())
                .concertDate(concert.getConcertDate())
                .performerName(concert.getPerformer().getName())
                .venueName(concert.getVenue().getName())
                .cityName(concert.getVenue().getCity().getName())
                .address(concert.getVenue().getAddress())
                .categoryName(concert.getConcertCategory().getName())
                .statusName(concert.getConcertStatus().getName())
                .statusCode(concert.getConcertStatus().getCode())
                .build();
    }

    public VenueVo toVenueVo(JpaVenuePo jpaVenuePo){
        return VenueVo.builder()
                .venueId(jpaVenuePo.getVenueId())
                .name(jpaVenuePo.getName())
                .address(jpaVenuePo.getAddress())
                .build();
    }

    public ConcertStatusVo toConcertStatusVo(JpaConcertStatusPo jpaConcertStatusPo){
        return ConcertStatusVo.builder()
                .concertStatusId(jpaConcertStatusPo.getConcertStatusId())
                .name(jpaConcertStatusPo.getName())
                .code(jpaConcertStatusPo.getCode())
                .build();
    }

    public PaymentMethodVo toPaymentMethodVo(JpaPaymentMethodPo jpaPaymentMethodPo){
        return PaymentMethodVo.builder()
                .paymentMethodId(jpaPaymentMethodPo.getPaymentMethodId())
                .paymentMethodName(jpaPaymentMethodPo.getName())
                .paymentMethodCode(jpaPaymentMethodPo.getCode())
                .build();
    }

    public UserVo toUserVo(JpaUserPo jpaUserPo){
        UserVo userVo = new UserVo();
        userVo.setUserId(jpaUserPo.getUserId());
        userVo.setUserName(jpaUserPo.getUserName());
        userVo.setEmail(jpaUserPo.getEmail());
        userVo.setPhone(jpaUserPo.getPhone());
        return userVo;
    }

    public ConcertTicketPriceVo toConcertTicketPriceVo(JpaConcertTicketPricePo jpaConcertTicketPricePo){
        return ConcertTicketPriceVo.builder()
                .concertTicketPriceId(jpaConcertTicketPricePo.getConcertTicketPriceId())
                .concertId(jpaConcertTicketPricePo.getConcert().getConcertId())
                .areaName(jpaConcertTicketPricePo.getAreaName())
                .price(jpaConcertTicketPricePo.getPrice())
                .availableQuantity(jpaConcertTicketPricePo.getAvailableQuantity())
                .build();
    }

    public InitOrderBo toInitOrderBo(InitOrderRequestDto initOrderRequestDto){
        LocalDateTime now = LocalDateTime.now();

        InitOrderBo initOrderBo = new InitOrderBo();
        JpaConOrderPo jpaConOrderPo = new JpaConOrderPo();

        //設定下單user
        JpaUserPo jpaUserPo = new JpaUserPo();
        jpaUserPo.setUserId(initOrderRequestDto.getUserId());
        //設定訂單狀態為待付款
        JpaOrderStatusPo jpaOrderStatusPo = new JpaOrderStatusPo();
        jpaOrderStatusPo.setCode("PENDING_PAYMENT");
        //設定購買票種
        JpaConcertTicketPricePo jpaConcertTicketPricePo = new JpaConcertTicketPricePo();
        jpaConcertTicketPricePo.setConcertTicketPriceId(initOrderRequestDto.getConcertTicketPriceId());

        JpaPaymentMethodPo jpaPaymentMethodPo = new JpaPaymentMethodPo();
        jpaPaymentMethodPo.setCode(initOrderRequestDto.getPaymentMethodCode());

        jpaConOrderPo.setOrderId(UUID.randomUUID().toString().replace("-", ""));
        jpaConOrderPo.setJpaUserPo(jpaUserPo);
        jpaConOrderPo.setJpaOrderStatusPo(jpaOrderStatusPo);
        jpaConOrderPo.setJpaConcertTicketPricePo(jpaConcertTicketPricePo);
        jpaConOrderPo.setJpaPaymentMethodPo(jpaPaymentMethodPo);
        jpaConOrderPo.setUnitPrice(initOrderRequestDto.getTotalPrice());
        // 暫不需設定付款日期時間
        jpaConOrderPo.setPaymentDatetime(null);
        // 訂單10分鐘後過期
        jpaConOrderPo.setExpiredDatetime(now.plusMinutes(10));
        jpaConOrderPo.setCrtDatetime(now);
        jpaConOrderPo.setVer(now);

        initOrderBo.setConOrderPo(jpaConOrderPo);
        initOrderBo.setConcertTicketPriceId(initOrderRequestDto.getConcertTicketPriceId());

        return initOrderBo;
    }

}
