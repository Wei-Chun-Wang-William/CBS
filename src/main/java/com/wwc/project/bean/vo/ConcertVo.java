package com.wwc.project.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertVo {
    private String concertId;
    private String title;
    private String description;
    private LocalDateTime concertDate;

    // 演出者資訊
    private String performerName;

    // 場館資訊 (打平處理，前端好讀取)
    private String venueName;
    private String cityName;
    private String address;

    // 分類與狀態
    private String categoryName;
    private String statusName;
    private String statusCode;
}
