package com.wwc.project.dao;

import com.wwc.project.bean.po.UserPo;

import java.util.List;

public interface CbsDao {
    List<UserPo> queryUsers() throws Exception;

    List<UserPo> queryUser(UserPo userPo) throws Exception;

    UserPo updateUsers(UserPo userPo) throws Exception;

    UserPo insertUser(UserPo userPo) throws Exception;

    UserPo deleteUser(UserPo userPo) throws Exception;

}
