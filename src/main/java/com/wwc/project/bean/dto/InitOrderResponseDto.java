package com.wwc.project.bean.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InitOrderResponseDto {
    private String orderId;
    private LocalDateTime expiredDatetime;
}
