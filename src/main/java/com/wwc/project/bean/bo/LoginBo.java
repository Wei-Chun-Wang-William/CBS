package com.wwc.project.bean.bo;

import com.wwc.project.bean.po.JpaUserPo;
import lombok.Data;

@Data
public class LoginBo {
    JpaUserPo inputJpaUserPo;
    JpaUserPo dbJpaUserPo;
    Boolean isLoginSuccess;
}
