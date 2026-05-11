package com.wwc.project.bean.dto;

import com.wwc.project.bean.po.JpaUserPo;
import lombok.Data;

@Data
public class LoginRequestDto {
    JpaUserPo userInfo;
}
