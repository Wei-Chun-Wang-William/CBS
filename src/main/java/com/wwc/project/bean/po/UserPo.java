package com.wwc.project.bean.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wwc.project.bean.CommonClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties
@Data
public class UserPo extends CommonClass {
    private String userName;
    private String email;
    private String phone;
    private String password;
    private String id;
}
