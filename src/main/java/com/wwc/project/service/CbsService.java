package com.wwc.project.service;

import com.wwc.project.bean.po.JpaUserPo;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.bean.vo.PageVo;

import java.util.List;

public interface CbsService {

    List<UserPo> queryUsers() throws Exception;

    UserPo queryUser(UserPo userPo) throws Exception;

    JpaUserPo queryUserJpa(JpaUserPo jpaUserPo) throws Exception;

    UserPo updateUser(UserPo userPo) throws Exception;

    UserPo insertUser(UserPo userPo) throws Exception;

    UserPo deleteUser(UserPo userPo) throws Exception;

    List<JpaUserPo> queryUserOrderByVerJpa(PageVo pageVo) throws Exception;
}
