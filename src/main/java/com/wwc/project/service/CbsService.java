package com.wwc.project.service;

import com.wwc.project.bean.dto.*;
import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.bean.vo.ConcertVo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.bean.vo.UserVo;

import java.util.List;

public interface CbsService {

    /**
     * 查詢所有用戶
     * @return 用戶列表
     * @throws Exception
     */
    List<UserVo> queryUsers() throws Exception;

    /**
     * 根據條件查詢單一用戶
     * @param userPo 用戶查詢條件
     * @return 用戶資訊
     * @throws Exception
     */
    UserPo queryUser(UserPo userPo) throws Exception;

    /**
     * 使用JPA根據條件查詢單一用戶
     * @param jpaUserPo JPA用戶查詢條件
     * @return JPA用戶資訊
     * @throws Exception
     */
    JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception;

    /**
     * 更新用戶資訊
     * @param userPo 用戶資訊
     * @return 更新後的用戶資訊
     * @throws Exception
     */
    UserPo updateUser(UserPo userPo) throws Exception;

    /**
     * 新增用戶
     * @param userPo 用戶資訊
     * @return 新增後的用戶資訊
     * @throws Exception
     */
    UserPo insertUser(UserPo userPo) throws Exception;

    /**
     * 刪除用戶
     * @param userPo 用戶資訊
     * @return 刪除後的用戶資訊
     * @throws Exception
     */
    UserPo deleteUser(UserPo userPo) throws Exception;

    /**
     * 分頁查詢JPA用戶並按版本排序
     * @param pageVo 分頁參數
     * @return JPA用戶列表
     * @throws Exception
     */
    List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception;

    /**
     * 驗證用戶登入資訊
     * @param loginRequestDto 登入請求參數
     * @return 登入回應
     * @throws Exception
     */
    LoginResponseDto validateUserInfo(LoginRequestDto loginRequestDto) throws Exception;

    /**
     * 創建場館
     * @param createVenueRequestDto 創建場館請求參數
     * @return 創建場館回應
     * @throws Exception
     */
    CreateVenueResponseDto createVenue(CreateVenueRequestDto createVenueRequestDto) throws Exception;

    /**
     * 非同步查詢所有用戶
     * @return 用戶列表
     * @throws Exception
     */
    List<UserPo> queryUsersAsync() throws Exception;


    /**
     * 根據條件找出演唱會資訊
     * @param getConcertDto
     * @return
     * @throws Exception
     */
    List<ConcertVo> queryConcerts(GetConcertDto getConcertDto) throws Exception;

    /**
     * 網頁初始化要使用的API
     * @return
     * @throws Exception
     */
    InitHomePageResponseDto initHomePage()  throws Exception;

    /**
     * 使用email找出顧客資訊
     * @param email
     * @return
     * @throws Exception
     */
    UserVo queryUserByEmail(String email) throws Exception;

    /**
     * 根據concertId找出該場演唱會的票價資訊
     * @param getConcertTicketPriceRequestDto
     * @return
     * @throws Exception
     */
    GetConcertTicketPriceResponseDto getConcertTicketPrice(GetConcertTicketPriceRequestDto getConcertTicketPriceRequestDto) throws Exception;

    /**
     * 初始化訂單
     * @param initOrderRequestDto
     * @return
     * @throws Exception
     */
    InitOrderResponseDto initOrder(InitOrderRequestDto initOrderRequestDto) throws Exception;

    /**
     * 查詢顧客訂單
     * @param getOrderByUserRequestDto
     * @return
     * @throws Exception
     */
    GetOrderByUserResponseDto getOrderByUser(GetOrderByUserRequestDto getOrderByUserRequestDto) throws Exception;

    /**
     * 更新超逾未付款訂單
     * @return
     * @throws Exception
     */
    int updateExpiredOrder() throws Exception;

    /**
     * 訂單付款
     * @param payOrderRequestDto
     * @return
     * @throws Exception
     */
    PayOrderResponseDto payOrder(PayOrderRequestDto payOrderRequestDto) throws Exception;

    /**
     * 建立使用者
     * @param createUserRequestDto
     * @return
     * @throws Exception
     */
    CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) throws Exception;
}
