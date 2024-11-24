package com.example.ordermgmt.service;

import com.example.ordermgmt.dto.OrderInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ExternalApiService externalApiService;

    private final List<OrderInfo> orderInfos = new ArrayList<>();


    // 주문 저장
    public ResponseEntity<String> saveOrder(OrderInfo orderInfo) {
        boolean orderNoValid = orderInfos.stream()
                .anyMatch(order -> order.getOrderNo().equals(orderInfo.getOrderNo()));

        if(!orderNoValid){
            saveOrderProc(orderInfo);
            return ResponseEntity.ok(orderInfo.getOrderNo() + "주문이 저장되었습니다.");
        }else{
           return ResponseEntity.ok(orderInfo.getOrderNo() + "주문번호가 중복됩니다.");
        }
    }

    //주문 조회
    public List<OrderInfo> getOrderInfo(String orderNo) {
        return orderInfos.stream()
                .filter(order -> order.getOrderNo().equals(orderNo))
                .toList();
    }

    //주문 조회(전체)
    public List<OrderInfo> getAllOrderInfo() {
        return new ArrayList<>(orderInfos);
    }

    //외부 데이터 저장
    public ResponseEntity<String> fetchAndSaveOrderData() {
        // 외부 API에서 JSON 데이터 가져오기
        List<OrderInfo> orderList = externalApiService.fetchOrdersFromExternalSystem();

        // 가져온 주문 데이터를 메모리에 저장
        if(!orderList.isEmpty()){
            saveOrderProc(orderList);
        }
        return ResponseEntity.ok("와부 시스템 연동 성공.");
    }

    //주문 데이터 외부 시스템 전송
    public ResponseEntity<String> sendOrderToExternalSystem(List<String> orderNoList) {

        List<OrderInfo> orderInfoList = new ArrayList<>();
        for(String orderNo : orderNoList){
            OrderInfo orderInfo = orderInfos.stream()
                    .filter(order -> order.getOrderNo().equals(orderNo))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

            orderInfoList.add(orderInfo);
        }

        externalApiService.sendOrderToExternalSystem(orderInfoList);
        return ResponseEntity.ok("와부 시스템 전송 성공.");
    }

    // 주문 목록 저장
    public void saveOrderProc(List<OrderInfo> orderList) {
        for (OrderInfo order : orderList) {
            saveOrderProc(order);
        }
    }

    // 주문 저장 처리
    public void saveOrderProc(OrderInfo orderInfo) {
        orderInfos.add(orderInfo);
    }
}
