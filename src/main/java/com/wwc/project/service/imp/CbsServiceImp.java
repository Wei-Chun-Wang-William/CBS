package com.wwc.project.service.imp;

import com.wwc.project.assembler.CbsAssembler;
import com.wwc.project.bean.bo.InitOrderBo;
import com.wwc.project.bean.bo.LoginBo;
import com.wwc.project.bean.dto.*;
import com.wwc.project.bean.po.*;
import com.wwc.project.bean.vo.ConcertVo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.bean.vo.UserVo;
import com.wwc.project.component.CbsComponent;
import com.wwc.project.service.CbsService;
import com.wwc.project.util.SecurityConfig;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Data
public class CbsServiceImp implements CbsService {

    private static final Logger logger = LoggerFactory.getLogger(CbsServiceImp.class);
    private CbsComponent cbsComponent;
    CbsAssembler cbsAssembler = new CbsAssembler();
    private final SecurityConfig securityConfig;

    @Override
    public List<UserVo> queryUsers() throws Exception {
        List<JpaUserPo> jpaUserPoList = cbsComponent.queryUsers();
        return jpaUserPoList.stream().map(cbsAssembler::toUserVo).collect(Collectors.toList());
    }

    @Override
    public JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception {
        return cbsComponent.queryUserJpa(jpaUserPo);
    }

    @Override
    public UserPo queryUser(UserPo userPo) throws Exception {
        return cbsComponent.queryUser(userPo);
    }

    @Override
    public UserPo updateUser(UserPo userPo) throws Exception {
        return cbsComponent.updateUser(userPo);
    }

    @Override
    public UserPo insertUser(UserPo userPo) throws Exception {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        userPo.setPassword(passwordEncoder.encode(userPo.getPassword()));
        return cbsComponent.insertUser(userPo);
    }

    @Override
    public UserPo deleteUser(UserPo userPo) throws Exception {
        return cbsComponent.deleteUser(userPo);
    }

    @Override
    public List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception {
        return cbsComponent.queryUserOrderByVerJpa(pageVo);
    }

    @Override
    public LoginResponseDto validateUserInfo(LoginRequestDto loginRequestDto) throws Exception {
        LoginBo loginBo = cbsAssembler.toLoginBo(loginRequestDto);
        loginBo.setDbJpaUserPo(cbsComponent.validateUserInfo(loginBo.getInputJpaUserPo()));
        loginBo.setIsLoginSuccess(loginBo.getDbJpaUserPo() != null);
        return cbsAssembler.toLoginResponseDto(loginBo);
    }

    @Override
    public CreateVenueResponseDto createVenue(CreateVenueRequestDto createVenueRequestDto) throws Exception {
        CreateVenueResponseDto createVenueResponseDto = new CreateVenueResponseDto();
        boolean isSuccess = false;
        JpaVenuePo jpaVenuePo = cbsComponent.createVenue(createVenueRequestDto.getVenueInfo());
        if (jpaVenuePo != null) {
            isSuccess = true;
            logger.info("createVenue success, venueId: {}", jpaVenuePo.getVenueId());
        } else {
            logger.error("createVenue failed, venueId: {}", createVenueRequestDto.getVenueInfo().getVenueId());
        }
        createVenueResponseDto.setIsCreateSuccess(isSuccess);
        return createVenueResponseDto;
    }

    @Override
    public List<UserPo> queryUsersAsync() throws Exception {

        long startTime = System.currentTimeMillis();

        CompletableFuture<List<UserPo>> usersFuture = cbsComponent.queryUsersAsync();
        CompletableFuture<List<UserPo>> usersFuture2 = cbsComponent.queryUsersAsync();
        CompletableFuture<List<UserPo>> usersFuture3 = cbsComponent.queryUsersAsync();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(usersFuture, usersFuture2, usersFuture3);
        allFutures.get();
        List<UserPo> users = usersFuture.get();
        List<UserPo> users2 = usersFuture2.get();
        List<UserPo> users3 = usersFuture3.get();

        users.addAll(users2);
        users.addAll(users3);

        long endTime = System.currentTimeMillis();
        logger.info("queryUsersAsync completed, total users: {}, {}, {}, cost: {} ms", users.size(), users2.size(), users3.size(), (endTime - startTime));
        return users;
    }

    @Override
    public List<ConcertVo> queryConcerts(GetConcertDto getConcertDto) throws Exception {
        List<JpaConcertPo> concertList = cbsComponent.queryConcerts(getConcertDto);
        return concertList.stream().map(cbsAssembler::toConcertVo).collect(Collectors.toList());
    }

    @Override
    public InitHomePageResponseDto initHomePage() throws Exception {
        InitHomePageResponseDto initHomePageResponseDto = new InitHomePageResponseDto();
        initHomePageResponseDto.setVenueVoList(cbsComponent.queryAllVenue().stream().map(cbsAssembler::toVenueVo).collect(Collectors.toList()));
        initHomePageResponseDto.setConcertStatusVoList(cbsComponent.queryAllConcertStatus().stream().map(cbsAssembler::toConcertStatusVo).collect(Collectors.toList()));
        initHomePageResponseDto.setPaymentMethodVoList(cbsComponent.queryAllPaymentMethod().stream().map(cbsAssembler::toPaymentMethodVo).collect(Collectors.toList()));
        return initHomePageResponseDto;
    }

    @Override
    public UserVo queryUserByEmail(String email) throws Exception {
        List<JpaUserPo> userPoList = cbsComponent.queryUserByEmail(email);
        if (!userPoList.isEmpty()) {
            return cbsAssembler.toUserVo(userPoList.get(0));
        }else {
            return null;
        }
    }

    @Override
    public GetConcertTicketPriceResponseDto getConcertTicketPrice(GetConcertTicketPriceRequestDto getConcertTicketPriceRequestDto) throws Exception {
        GetConcertTicketPriceResponseDto getConcertTicketPriceResponseDto = new GetConcertTicketPriceResponseDto();
        List<JpaConcertTicketPricePo>  concertTicketPriceList = cbsComponent.queryConcertTicketPrice(getConcertTicketPriceRequestDto.getConcertId());
        getConcertTicketPriceResponseDto.setConcertTicketPriceVoList(concertTicketPriceList.stream().map(cbsAssembler::toConcertTicketPriceVo).collect(Collectors.toList()));
        return getConcertTicketPriceResponseDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InitOrderResponseDto initOrder(InitOrderRequestDto initOrderRequestDto) throws Exception {
        try{
            InitOrderResponseDto initOrderResponseDto = new InitOrderResponseDto();
            InitOrderBo initOrderBo = cbsAssembler.toInitOrderBo(initOrderRequestDto);

            if (1 == cbsComponent.decreaseTicketAvailableQuantity(initOrderBo.getConcertTicketPriceId())){
                logger.info("decrease Ticket quantity success, concertTicketPriceId: {}, decrease available quantity by 1, userId: {}",
                        initOrderBo.getConcertTicketPriceId(),initOrderBo.getConOrderPo().getJpaUserPo().getUserId());
            }else {
                logger.error("decrease Ticket quantity failed, concertTicketPriceId: {}, userId: {}",
                        initOrderBo.getConcertTicketPriceId(),initOrderBo.getConOrderPo().getJpaUserPo().getUserId());
                throw new Exception("decrease Ticket quantity failed");
            }

            if (1 == cbsComponent.saveConOrder(initOrderBo.getConOrderPo())){
                logger.info("initial Order success, concertTicketPriceId: {}, userId: {}",
                        initOrderBo.getConcertTicketPriceId(),initOrderBo.getConOrderPo().getJpaUserPo().getUserId());
            }else {
                logger.info("initial Order failed, concertTicketPriceId: {}, userId: {}",
                        initOrderBo.getConcertTicketPriceId(),initOrderBo.getConOrderPo().getJpaUserPo().getUserId());
                throw new Exception("initial Order failed");
            }

            initOrderResponseDto.setOrderId(initOrderBo.getConOrderPo().getOrderId());
            initOrderResponseDto.setExpiredDatetime(initOrderBo.getConOrderPo().getExpiredDatetime());
            return initOrderResponseDto;

        }catch (Exception e){
            logger.error("initOrder error:{}",e.getMessage());
            throw e;
        }
    }

    @Override
    public GetOrderByUserResponseDto getOrderByUser(GetOrderByUserRequestDto getOrderByUserRequestDto) throws Exception {
        GetOrderByUserResponseDto getOrderByUserResponseDto = new GetOrderByUserResponseDto();
        getOrderByUserResponseDto.setOrderVoList(cbsComponent.queryConOrdersByUserId(getOrderByUserRequestDto.getUserVo().getUserId()));
        return getOrderByUserResponseDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExpiredOrder() throws Exception {
        return cbsComponent.updateExpiredOrder();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrderResponseDto payOrder(PayOrderRequestDto payOrderRequestDto) throws Exception {
        PayOrderResponseDto payOrderResponseDto = new PayOrderResponseDto();
        try{
            if (1 == cbsComponent.payOrder(payOrderRequestDto.getOrderId())){
                payOrderResponseDto.setPaySuccess(true);
                logger.info("pay Order success, orderId: {}", payOrderRequestDto.getOrderId());
            } else {
                payOrderResponseDto.setPaySuccess(false);
                logger.warn("pay Order failed, orderId: {}", payOrderRequestDto.getOrderId());
            }
            return payOrderResponseDto;
        }catch (Exception e){
            logger.error("payOrder error:{}",e.getMessage());
            throw e;
        }
    }

    @Override
    public CreateUserResponseDto createUser(CreateUserRequestDto createUserRequestDto) throws Exception {
        CreateUserResponseDto createUserResponseDto = new CreateUserResponseDto();
        try{
            // hash 雜湊密碼
            PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
            JpaUserPo jpaUserPo = new JpaUserPo();
            jpaUserPo.setUserName(createUserRequestDto.getUserName());
            jpaUserPo.setEmail(createUserRequestDto.getEmail());
            jpaUserPo.setPhone(createUserRequestDto.getPhone());
            jpaUserPo.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
            createUserResponseDto.setSuccess(cbsComponent.createUser(jpaUserPo) == 1);
            createUserResponseDto.setMessage(createUserResponseDto.isSuccess() ? "成功建立帳戶" : "建立帳戶失敗");
            return createUserResponseDto;
        } catch (DataIntegrityViolationException e){
            createUserResponseDto.setSuccess(false);
            createUserResponseDto.setMessage("建立帳戶失敗，因為email已經存在");
            return createUserResponseDto;
        } catch (Exception e){
            logger.error("createUser error:{}",e.getMessage());
            throw e;
        }
    }

}
