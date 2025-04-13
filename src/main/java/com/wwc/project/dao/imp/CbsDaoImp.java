package com.wwc.project.dao.imp;

import com.wwc.project.bean.mapper.UserMapper;
import com.wwc.project.bean.po.UserPo;
import com.wwc.project.controller.CbsController;
import com.wwc.project.dao.CbsDao;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Data
public class CbsDaoImp implements CbsDao {

    JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(CbsController.class);

    @Override
    public List<UserPo> queryUsers() throws Exception {
        List<UserPo> userPoList;
        try {
            String sql = "select user_id, user_name, email,phone, crt_datetime, ver from dbo.con_users";
            userPoList = jdbcTemplate.query(sql,new UserMapper());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return userPoList;
    }

    @Override
    public List<UserPo> queryUser(UserPo userPo) throws Exception {
        List<UserPo> userPoList;
        try {
            String sql = "select user_id, user_name, email,phone, crt_datetime, ver from dbo.con_users where user_id = ?";
            userPoList = jdbcTemplate.query(sql, new UserMapper(),userPo.getId());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return userPoList;
    }

    @Override
    public UserPo updateUsers(UserPo userPo) throws Exception {
        try {
            int a = jdbcTemplate.update("update dbo.con_users set user_name = ?, email = ?, phone= ?, password = ?, ver = ? where user_id = ?",
                    userPo.getUserName(), userPo.getEmail(), userPo.getPhone(), userPo.getPassword(), userPo.getVer(), userPo.getId());
            logger.info(String.valueOf(a));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return userPo;
    }

    @Override
    public UserPo insertUser(UserPo userPo) throws Exception{
        try {
            jdbcTemplate.update("insert into dbo.con_users (user_id, user_name, email, phone, \"password\", crt_datetime, ver) VALUES(?,?,?,?,?,?,?)",
                    userPo.getId(), userPo.getUserName(), userPo.getEmail(), userPo.getPhone(), userPo.getPassword(), userPo.getCrtDatetime(), userPo.getVer());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return userPo;
    }

    @Override
    public UserPo deleteUser(UserPo userPo) throws Exception{
        try{
            jdbcTemplate.update("delete from dbo.con_users where user_id = ?", userPo.getId());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
        return null;
    }


}
