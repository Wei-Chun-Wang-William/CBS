package com.wwc.project.component;

import com.wwc.project.bean.dto.GetConcertDto;
import com.wwc.project.bean.po.*;
import com.wwc.project.bean.vo.OrderVo;
import com.wwc.project.bean.vo.PageVo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CbsComponent {
/**
     * 查詢所有用戶
     * @return 用戶列表
     * @throws Exception 查詢異常
     */
    List<JpaUserPo> queryUsers() throws Exception;

    /**
     * 根據條件查詢單一用戶
     * @param userPo 用戶查詢條件
     * @return 用戶資訊
     * @throws Exception 查詢異常
     */
    UserPo queryUser(UserPo userPo) throws Exception;

    /**
     * 使用JPA查詢單一用戶
     * @param jpaUserPo JPA用戶查詢條件
     * @return JPA用戶資訊
     * @throws Exception 查詢異常
     */
    JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception;

    /**
     * 更新用戶資訊
     * @param userPo 用戶資訊
     * @return 更新後的用戶資訊
     * @throws Exception 更新異常
     */
    UserPo updateUser(UserPo userPo) throws Exception;

    /**
     * 新增用戶
     * @param userPo 用戶資訊
     * @return 新增後的用戶資訊
     * @throws Exception 新增異常
     */
    UserPo insertUser(UserPo userPo) throws Exception;

    /**
     * 刪除用戶
     * @param userPo 用戶資訊
     * @return 刪除後的用戶資訊
     * @throws Exception 刪除異常
     */
    UserPo deleteUser(UserPo userPo) throws Exception;

    /**
     * 使用JPA分頁查詢用戶並按版本排序
     * @param pageVo 分頁參數
     * @return 用戶列表
     * @throws Exception 查詢異常
     */
    List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception;

    /**
     * 驗證用戶資訊
     * @param jpaUserPo 用戶資訊
     * @return 驗證後的用戶資訊
     * @throws Exception 驗證異常
     */
    JpaUserPo validateUserInfo(JpaUserPo jpaUserPo) throws Exception;

    /**
     * 創建場館
     * @param jpaVenuePo 場館資訊
     * @return 創建後的場館資訊
     * @throws Exception 創建異常
     */
    JpaVenuePo createVenue(JpaVenuePo jpaVenuePo) throws Exception;

    /**
     * 非同步查詢所有用戶 template
     * @return 用戶列表的CompletableFuture
     * @throws Exception 查詢異常
     */
    CompletableFuture<List<UserPo>> queryUsersAsync() throws Exception;


    List<JpaConcertPo> queryConcerts(GetConcertDto getConcertDto) throws Exception;

    List<JpaVenuePo> queryAllVenue() throws Exception;


    List<JpaConcertStatusPo> queryAllConcertStatus() throws Exception;

    List<JpaUserPo> queryUserByEmail(String email) throws Exception;

    List<JpaConcertTicketPricePo>  queryConcertTicketPrice(String concertId) throws Exception;

    List<JpaPaymentMethodPo> queryAllPaymentMethod() throws Exception;

    int saveConOrder(JpaConOrderPo jpaConOrderPo) throws Exception;

    int decreaseTicketAvailableQuantity(String ticketId) throws Exception;

    List<OrderVo> queryConOrdersByUserId(String userId) throws Exception;

    int updateExpiredOrder() throws Exception;

    int payOrder(String orderId) throws Exception;

    int createUser(JpaUserPo jpaUserPo) throws Exception;
}
