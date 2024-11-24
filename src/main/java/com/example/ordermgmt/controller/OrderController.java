package com.example.ordermgmt.controller;

import com.example.ordermgmt.dto.OrderInfo;
import com.example.ordermgmt.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    //주문 데이터 조회
    @GetMapping("/getOrderInfo/{orderNo}")
    public List<OrderInfo> getOrderInfo(@PathVariable String orderNo) {
        return orderService.getOrderInfo(orderNo);
    }

    //주문 데이터 조회(전체)
    @GetMapping("/getAllOrderInfo")
    public List<OrderInfo> getAllOrderInfo() {
        return orderService.getAllOrderInfo();
    }

    //주문 데이터 저장
    @PostMapping("/saveOrderInfo")
    public ResponseEntity<String> saveOrderInfo(@RequestBody OrderInfo orderInfo) {
        return orderService.saveOrder(orderInfo);
    }

    //외부 시스템 연동(내부로 받아서 저장)
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveOrders() {
        return orderService.fetchAndSaveOrderData();
    }

    //외부 시스템 연동(내부 시스템 주문정보 외부시스템 전송)
    @PostMapping("/sendOrder")
    public ResponseEntity<String> sendOrderToExternalSystem(@RequestBody List<String> orderNoList) {
        return orderService.sendOrderToExternalSystem(orderNoList);
    }
}
