package com.wwc.project.bean.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetConcertDto {
    private String title;
    private String cityId;
    private String venueId;
    private String categoryId;
    private String statusCode;
    private String performerId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
