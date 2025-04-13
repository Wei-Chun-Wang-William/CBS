package com.wwc.project.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class RequestBodyMapper<T> {
    private T data;
}
