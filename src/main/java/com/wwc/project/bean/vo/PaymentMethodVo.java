package com.wwc.project.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodVo {
    private String paymentMethodId;
    private String paymentMethodName;
    private String paymentMethodCode;
}
