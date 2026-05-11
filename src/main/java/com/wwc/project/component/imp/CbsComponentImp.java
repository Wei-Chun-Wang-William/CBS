package com.wwc.project.component.imp;

import com.wwc.project.bean.dto.GetConcertDto;
import com.wwc.project.bean.po.*;

import com.wwc.project.bean.vo.OrderVo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.dao.*;
import com.wwc.project.dao.imp.CbsDaoImp;
import com.wwc.project.component.CbsComponent;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Data
public class CbsComponentImp implements CbsComponent {

    private CbsDaoImp cbsDaoImp;

    Logger logger = LoggerFactory.getLogger(CbsComponentImp.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserJpaDao userJpaDao;

    @Autowired
    private VenueJpaDao venueJpaDao;

    @Autowired
    private ConcertJpaDao concertJpaDao;

    @Autowired
    private ConcertStatusJpaDao concertStatusJpaDao;

    @Autowired
    private ConcertTicketPriceJpaDao concertTicketPriceJpaDao;

    @Autowired
    private PaymentMethodJpaDao paymentMethodJpaDao;

    @Autowired
    private ConOrderJpaDao conOrderJpaDao;

    @Override
    public List<JpaUserPo> queryUsers() throws Exception {
       return userJpaDao.findAll();
    }

    @Override
    public UserPo queryUser(UserPo userPo) throws Exception {
        List<UserPo> userPoList = cbsDaoImp.queryUser(userPo);
        if (userPoList.size() == 1) {
            userPo = userPoList.get(0);
        }else {
            throw new Exception("查詢單筆user失敗，筆數不為1");
        }
        return userPo;
    }

    @Override
    public JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception {
        List<JpaUserPo> jpaUserPoList = userJpaDao.findByUserId(jpaUserPo.getUserId());
        if (jpaUserPoList.size() == 1) {
            jpaUserPo = jpaUserPoList.get(0);
        }else {
            throw new Exception("查詢單筆user失敗，筆數不為1");
        }
        return jpaUserPo;
    }


    @Override
    public UserPo updateUser(UserPo userPo) throws Exception {
        Date date = new Date();
        userPo.setVer(date);
        return cbsDaoImp.updateUsers(userPo);
    }

    @Override
    public UserPo insertUser(UserPo userPo) throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-","");
        userPo.setId(uuid);
        Date date = new Date();
        userPo.setVer(date);
        userPo.setCrtDatetime(date);
        return cbsDaoImp.insertUser(userPo);
    }

    @Override
    public UserPo deleteUser(UserPo userPo) throws Exception {
        return cbsDaoImp.deleteUser(userPo);
    }

    @Override
    public List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception {
        int pageSize = pageVo.getPageSize();
        int pageNumber = pageVo.getPageNumber();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userJpaDao.findAllByOrderByVerDesc(pageable).getContent();
    }

    @Override
    public JpaUserPo validateUserInfo(JpaUserPo jpaUserPo) throws Exception {
        List<JpaUserPo> jpaUserPoList = userJpaDao.findByEmail(jpaUserPo.getEmail());
        JpaUserPo jpaUserPo1;
        if (jpaUserPoList.size() == 1) {
            jpaUserPo1 = jpaUserPoList.get(0);
            boolean isPassWordMatch = passwordEncoder.matches(jpaUserPo.getPassword(), jpaUserPo1.getPassword());
            logger.info(jpaUserPo1.toString());
            return isPassWordMatch ? jpaUserPo1 : null;
        }else if (jpaUserPoList.isEmpty()) {
            logger.error("查詢單筆user失敗，筆數為0");
            return null;
        }else {
            logger.error("查詢單筆user失敗，筆數不為1");
            return null;
        }
    }

    @Override
    public JpaVenuePo createVenue(JpaVenuePo jpaVenuePo) throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        jpaVenuePo.setVenueId(uuid);
        return venueJpaDao.save(jpaVenuePo);
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<UserPo>> queryUsersAsync() throws Exception {
        try {
            Thread.sleep(3000);
            List<UserPo> userPoList = cbsDaoImp.queryUsers();
            return CompletableFuture.completedFuture(userPoList);
        } catch (Exception e) {
            logger.error("Error querying users asynchronously: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<JpaConcertPo> queryConcerts(GetConcertDto getConcertDto) throws Exception {
        return concertJpaDao.findByDynamicCriteria(
                getConcertDto.getTitle(),
                getConcertDto.getCityId(),
                getConcertDto.getVenueId(),
                getConcertDto.getCategoryId(),
                getConcertDto.getStatusCode(),
                getConcertDto.getPerformerId(),
                getConcertDto.getStartDate(),
                getConcertDto.getEndDate()
        );
    }

    @Override
    public List<JpaVenuePo> queryAllVenue() throws Exception {
        return venueJpaDao.findAll();
    }

    @Override
    public List<JpaConcertStatusPo> queryAllConcertStatus() throws Exception {
        return concertStatusJpaDao.findAll();
    }

    @Override
    public List<JpaUserPo> queryUserByEmail(String email) throws Exception {
        return userJpaDao.findByEmail(email);
    }

    @Override
    public List<JpaConcertTicketPricePo> queryConcertTicketPrice(String concertId) throws Exception {
        return concertTicketPriceJpaDao.findByConcertConcertIdOrderByPriceDesc(concertId);
    }

    @Override
    public List<JpaPaymentMethodPo> queryAllPaymentMethod() throws Exception {
        return paymentMethodJpaDao.findAll();
    }

    @Override
    public int saveConOrder(JpaConOrderPo jpaConOrderPo) throws Exception {

        return conOrderJpaDao.insertOrderNative(
                jpaConOrderPo.getOrderId(),
                jpaConOrderPo.getJpaUserPo().getUserId(),
                jpaConOrderPo.getJpaOrderStatusPo().getCode(),
                jpaConOrderPo.getJpaPaymentMethodPo().getCode(),
                jpaConOrderPo.getJpaConcertTicketPricePo().getConcertTicketPriceId(),
                jpaConOrderPo.getUnitPrice(),
                jpaConOrderPo.getExpiredDatetime(),
                jpaConOrderPo.getCrtDatetime(),
                jpaConOrderPo.getVer()
        );

    }

    @Override
    public int decreaseTicketAvailableQuantity(String ticketId) throws Exception {
        return concertTicketPriceJpaDao.decreaseAvailableQuantity(ticketId);
    }

    @Override
    public List<OrderVo> queryConOrdersByUserId(String userId) throws Exception {
        return conOrderJpaDao.findByJpaUserPoOrderByCrtDatetimeDesc(userId);
    }

    @Override
    public int updateExpiredOrder() throws Exception {
        return conOrderJpaDao.updateExpiredOrder();
    }

    @Override
    public int payOrder(String orderId) throws Exception {
        return conOrderJpaDao.payOrder(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createUser(JpaUserPo jpaUserPo) throws Exception {
        String uuid = UUID.randomUUID().toString().replace("-","");
        jpaUserPo.setUserId(uuid);
        LocalDateTime localDateTime = LocalDateTime.now();
        jpaUserPo.setVer(localDateTime);
        jpaUserPo.setCrtDatetime(localDateTime);
        return userJpaDao.createUser(jpaUserPo.getUserId(), jpaUserPo.getUserName(), jpaUserPo.getEmail(), jpaUserPo.getPhone(), jpaUserPo.getPassword(), jpaUserPo.getCrtDatetime(), jpaUserPo.getVer());
    }

}
