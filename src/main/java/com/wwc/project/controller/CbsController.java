package com.wwc.project.controller;

import java.util.List;

import com.wwc.project.bean.ReponseBodyMapper;
import com.wwc.project.bean.dto.*;
import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.bean.vo.ConcertVo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.bean.vo.UserVo;
import com.wwc.project.service.CbsService;
import com.wwc.project.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cbsController")
public class CbsController {

    Logger logger = LoggerFactory.getLogger(CbsController.class);

    @Autowired
    private CbsService cbsServiceImp;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/queryUsers")
    public @ResponseBody ReponseBodyMapper queryUsers(){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            List<UserVo> userPoList = cbsServiceImp.queryUsers();
            reponseBodyMapper.setData(userPoList);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryUserJpa")
    public @ResponseBody ReponseBodyMapper queryUserJpa(@RequestBody JpaUserPo jpaUserPo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            jpaUserPo = cbsServiceImp.queryUserJpa(jpaUserPo);
            reponseBodyMapper.setData(jpaUserPo);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }


    @PostMapping("/queryUserOrderByVerJpa")
    public @ResponseBody ReponseBodyMapper queryUserOrderByVerJpa(@RequestBody PageVo pageVo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            List<JpaUserPo> jpaUserPoList = cbsServiceImp.queryUserOrderByVerJpa(pageVo);
            reponseBodyMapper.setData(jpaUserPoList);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryUser")
    public @ResponseBody ReponseBodyMapper queryUser(@RequestBody UserPo userPo){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.queryUser(userPo);
            reponseBodyMapper.setData(userPo);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/updateUser")
    public @ResponseBody ReponseBodyMapper updateUser(@RequestBody UserPo userPo){
        ReponseBodyMapper reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.updateUser(userPo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/createUser")
    public @ResponseBody ReponseBodyMapper createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        CreateUserResponseDto createUserResponseDto;
        try {
            createUserResponseDto = cbsServiceImp.createUser(createUserRequestDto);
            reponseBodyMapper.setData(createUserResponseDto);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            logger.error(e.getMessage());
            createUserResponseDto = new CreateUserResponseDto();
            createUserResponseDto.setSuccess(false);
            createUserResponseDto.setMessage("建立使用者時發生不可預期的錯誤，請稍後再試");
            reponseBodyMapper.setData(createUserResponseDto);
            reponseBodyMapper.setMessage("Create user failed");
        }
        return reponseBodyMapper;
    }

    @PostMapping("/deleteUser")
    public @ResponseBody ReponseBodyMapper deleteUser(@RequestBody UserPo userPo){
        ReponseBodyMapper reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            userPo = cbsServiceImp.deleteUser(userPo);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/login")
    public @ResponseBody ReponseBodyMapper login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        LoginResponseDto loginResponseDto;
        try {
            loginResponseDto = cbsServiceImp.validateUserInfo(loginRequestDto);
            reponseBodyMapper.setData(loginResponseDto);
            if (loginResponseDto.getIsLoginSuccess()) {
                String token = jwtUtil.createToken(loginResponseDto.getUserInfo().getEmail());

                // 2. 建立 Session Cookie (不要調用 .maxAge() 即可)
                ResponseCookie cookie = ResponseCookie.from("access_token", token)
                        .httpOnly(true)    // 防止 JS 讀取
                        .path("/")         // 全站有效
                        .secure(true)     // 開發環境 http 設 false，生產環境 https 設 true
                        .sameSite("None")   // 基本防禦 CSRF
                        .build();

                response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

                reponseBodyMapper.setMessage("Login successful");
                logger.info("User logged in: " + loginResponseDto.getUserInfo().getUserId());
            } else {
                reponseBodyMapper.setMessage("Invalid email or password");
                logger.warn("Login failed for email: " + loginRequestDto.getUserInfo().getEmail());
            }
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during login");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("Login error: " + e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/logout")
    public @ResponseBody ReponseBodyMapper logout(HttpServletResponse response) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();

        try {
            // 建立一個立即過期的 Cookie
            ResponseCookie cookie = ResponseCookie.from("access_token", "")
                    .httpOnly(true)
                    .path("/")
                    .secure(true)    // 需與 login 時一致
                    .sameSite("None") // 需與 login 時一致
                    .maxAge(0)       // 關鍵：設置為 0 會命令瀏覽器立即刪除該 Cookie
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            reponseBodyMapper.setMessage("Logout successful");
            logger.info("User logged out successfully");

        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during logout");
            logger.error("Logout error: " + e.getMessage());
        }

        return reponseBodyMapper;
    }

    @PostMapping("/createVenue")
    public @ResponseBody ReponseBodyMapper createVenue(@RequestBody CreateVenueRequestDto createVenueRequestDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        CreateVenueResponseDto createVenueResponseDto;
        try {
            createVenueResponseDto = cbsServiceImp.createVenue(createVenueRequestDto);
            reponseBodyMapper.setData(createVenueResponseDto);
            if (createVenueResponseDto.getIsCreateSuccess()) {
                reponseBodyMapper.setMessage("Create Venue successful");
            } else {
                reponseBodyMapper.setMessage("Create Venue failed");
            }
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during venue creation");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("Create Venue error: " + e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryUsersAsync")
    public @ResponseBody ReponseBodyMapper queryUsersAsync(){
        ReponseBodyMapper<Object> reponseBodyMapper =new ReponseBodyMapper<>();
        try {
            List<UserPo> userPoList = cbsServiceImp.queryUsersAsync();
            reponseBodyMapper.setData(userPoList);
            logger.info(reponseBodyMapper.getData().toString());
        }catch (Exception e){
            reponseBodyMapper.setData(e.getMessage());
            logger.error(e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/queryConcert")
    public @ResponseBody ReponseBodyMapper queryConcert(@RequestBody GetConcertDto getConcertDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        List<ConcertVo> concertVoList;
        try {
            concertVoList = cbsServiceImp.queryConcerts(getConcertDto);
            reponseBodyMapper.setData(concertVoList);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during query concert");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("Query concert error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/initHomePage")
    public @ResponseBody ReponseBodyMapper initHomePage() {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        InitHomePageResponseDto initHomePageResponseDto;
        try {
            initHomePageResponseDto = cbsServiceImp.initHomePage();
            reponseBodyMapper.setData(initHomePageResponseDto);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during initHomePage");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("initHomePage error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/getCurrentUser")
    public @ResponseBody ReponseBodyMapper<Object> getCurrentUser(HttpServletRequest request) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();

        try {
            // 1. 從 Cookie 中取得 token
            String token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("access_token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            // 2. 驗證 Token 是否存在且有效
            if (token != null && jwtUtil.validateToken(token)) {
                // 3. 從 token 解析出 email
                Claims claims = jwtUtil.parseToken(token);
                String email = claims.getSubject();

                // 4. 去資料庫查出最新的使用者資訊
                UserVo userVo = cbsServiceImp.queryUserByEmail(email);
                if (userVo != null) {
                    reponseBodyMapper.setData(userVo);
                    reponseBodyMapper.setMessage("Success");
                    logger.info("目前進來的是" + userVo.toString());
                }else {
                    throw new RuntimeException("getCurrentUser 查無使用者：email=" + email);
                }
            } else {
                reponseBodyMapper.setMessage("Unauthorized");
            }
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during getCurrentUser");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("getCurrentUser error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/getConcertTicketPrice")
    public @ResponseBody ReponseBodyMapper getConcertTicketPrice(@RequestBody GetConcertTicketPriceRequestDto getConcertTicketPriceRequestDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        GetConcertTicketPriceResponseDto getConcertTicketPriceResponseDto;
        try {
            getConcertTicketPriceResponseDto = cbsServiceImp.getConcertTicketPrice(getConcertTicketPriceRequestDto);
            reponseBodyMapper.setData(getConcertTicketPriceResponseDto);
        logger.info(reponseBodyMapper.getData().toString());
    } catch (Exception e) {
        reponseBodyMapper.setMessage("An error occurred during getConcertTicketPrice");
        reponseBodyMapper.setData(e.getMessage());
        logger.error("getConcertTicketPrice error: {}", e.getMessage());
    }
        return reponseBodyMapper;
    }


    @PostMapping("/initOrder")
    public @ResponseBody ReponseBodyMapper initOrder(@RequestBody InitOrderRequestDto initOrderRequestDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        InitOrderResponseDto initOrderResponseDto;
        try {
            initOrderResponseDto = cbsServiceImp.initOrder(initOrderRequestDto);
            reponseBodyMapper.setData(initOrderResponseDto);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during initOrder");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("initOrder error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/getOrderByUser")
    public @ResponseBody ReponseBodyMapper getOrderByUser(@RequestBody GetOrderByUserRequestDto getOrderByUserRequestDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        GetOrderByUserResponseDto getOrderByUserResponseDto;
        try {
            getOrderByUserResponseDto = cbsServiceImp.getOrderByUser(getOrderByUserRequestDto);
            reponseBodyMapper.setData(getOrderByUserResponseDto);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during getOrderByUser");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("getOrderByUser error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }

    @PostMapping("/updateExpiredOrder")
    public @ResponseBody ReponseBodyMapper updateExpiredOrder() {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        try {
            int updatedExpiredOrder = cbsServiceImp.updateExpiredOrder();
            reponseBodyMapper.setData(updatedExpiredOrder);
            logger.info("本次更新超逾未付款訂單數量: {}", updatedExpiredOrder);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during updateExpiredOrder");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("updateExpiredOrder error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }


    @PostMapping("/payOrder")
    public @ResponseBody ReponseBodyMapper payOrder(@RequestBody PayOrderRequestDto payOrderRequestDto) {
        ReponseBodyMapper<Object> reponseBodyMapper = new ReponseBodyMapper<>();
        PayOrderResponseDto payOrderResponseDto;
        try {
            payOrderResponseDto = cbsServiceImp.payOrder(payOrderRequestDto);
            reponseBodyMapper.setData(payOrderResponseDto);
            logger.info(reponseBodyMapper.getData().toString());
        } catch (Exception e) {
            reponseBodyMapper.setMessage("An error occurred during payOrder");
            reponseBodyMapper.setData(e.getMessage());
            logger.error("payOrder error: {}", e.getMessage());
        }
        return reponseBodyMapper;
    }



}
