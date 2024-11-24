package com.example.ordermgmt.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {
    //주문번호
    private String orderNo;
    //주문자
    private String customerName;
    //주문일
    private LocalDate orderDate;
    //주문상태
    private String orderStatus;
}
