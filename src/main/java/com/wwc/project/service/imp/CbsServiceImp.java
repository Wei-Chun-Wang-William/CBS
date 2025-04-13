package com.wwc.project.service.imp;

import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.bean.vo.PageVo;
import com.wwc.project.service.CbsService;
import com.wwc.project.component.imp.CbsComponentImp;
import lombok.Data;

import java.util.List;

@Data
public class CbsServiceImp implements CbsService {

    CbsComponentImp cbsComponentImp;

    @Override
    public List<UserPo> queryUsers() throws Exception{
        return cbsComponentImp.queryUsers();
    }

    @Override
    public JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception{
        return cbsComponentImp.queryUserJpa(jpaUserPo);
    }

    @Override
    public UserPo queryUser(UserPo userPo) throws Exception {
        return cbsComponentImp.queryUser(userPo);
    }

    @Override
    public UserPo updateUser(UserPo userPo) throws Exception {
        return cbsComponentImp.updateUser(userPo);
    }

    @Override
    public UserPo insertUser(UserPo userPo) throws Exception {
        return cbsComponentImp.insertUser(userPo);
    }

    @Override
    public UserPo deleteUser(UserPo userPo) throws Exception {
        return cbsComponentImp.deleteUser(userPo);
    }

    @Override
    public List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception {
        return cbsComponentImp.queryUserOrderByVerJpa(pageVo);
    }
}
