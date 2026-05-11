package com.wwc.project.bean.dto;

import com.wwc.project.bean.vo.OrderVo;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderByUserResponseDto {
    List<OrderVo> orderVoList;
}
