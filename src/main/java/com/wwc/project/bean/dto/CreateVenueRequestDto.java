package com.wwc.project.bean.dto;

import com.wwc.project.bean.po.JpaVenuePo;
import lombok.Data;

@Data
public class CreateVenueRequestDto {
    JpaVenuePo venueInfo;
}
