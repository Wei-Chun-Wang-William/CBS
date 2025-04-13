package com.wwc.project.component.imp;

import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;

import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.dao.CbsJpaDao;
import com.wwc.project.dao.imp.CbsDaoImp;
import com.wwc.project.component.CbsComponent;
import lombok.Data;
import net.sf.jsqlparser.util.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CbsComponentImp implements CbsComponent {

    private CbsDaoImp cbsDaoImp;

    @Autowired
    private CbsJpaDao cbsJpaDao;

    @Override
    public List<UserPo> queryUsers() throws Exception {
       return cbsDaoImp.queryUsers();
    }

    @Override
    public UserPo queryUser(UserPo userPo) throws Exception {
        List<UserPo> userPoList = cbsDaoImp.queryUser(userPo);
        if (userPoList.size() == 1) {
            userPo = userPoList.get(0);
        }else {
            throw new ValidationException("查詢單筆user失敗，筆數不為1");
        }
        return userPo;
    }

    @Override
    public JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception {
        List<JpaUserPo> jpaUserPoList = cbsJpaDao.findByUserId(jpaUserPo.getUserId());
        if (jpaUserPoList.size() == 1) {
            jpaUserPo = jpaUserPoList.get(0);
        }else {
            throw new ValidationException("查詢單筆user失敗，筆數不為1");
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
        return cbsJpaDao.findAllByOrderByVerDesc(pageable).getContent();
    }
}
