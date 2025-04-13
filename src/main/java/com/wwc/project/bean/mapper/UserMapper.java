package com.wwc.project.bean.mapper;

import com.wwc.project.bean.po.UserPo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserPo> {

    @Override
    public UserPo mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserPo userPo = new UserPo();
        if (rs.getObject("ver") != null) {
            userPo.setVer(rs.getDate("ver"));
        }
        if (rs.getObject("crt_datetime") != null) {
            userPo.setCrtDatetime(rs.getDate("crt_datetime"));
        }
        if (rs.getString("user_name") != null) {
            userPo.setUserName(rs.getString("user_name"));
        }
        if (rs.getString("email") != null) {
            userPo.setEmail(rs.getString("email"));
        }
        if (rs.getString("phone") != null) {
            userPo.setPhone(rs.getString("phone"));
        }
        if (rs.getString("user_id") != null) {
            userPo.setId(rs.getString("user_id"));
        }
        return userPo;
    }
}

