package com.example.ordermgmt.service;

import com.example.ordermgmt.dto.OrderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String EXTERNAL_API_URL = "http://externalsystem.com/api/orders";

    // 외부 시스템에서 주문 데이터를 가져오기
    public List<OrderInfo> fetchOrdersFromExternalSystem() {
        try {
            String jsonResponse = restTemplate.getForObject(EXTERNAL_API_URL, String.class);
            return Arrays.asList(objectMapper.readValue(jsonResponse, OrderInfo[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("외부 주문데이터를 가져오는데 실패 했습니다.", e);
        }
    }

    // 주문 데이터를 외부 시스템으로 전송
    public void sendOrderToExternalSystem(List<OrderInfo> orderList) {
        for (OrderInfo orderInfo : orderList) {
            String json = serializeOrder(orderInfo);
            restTemplate.postForObject(EXTERNAL_API_URL, json, String.class);
        }
    }

    // OrderInfo 객체를 JSON 문자열로 변환
    public String serializeOrder(OrderInfo orderInfo) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(orderInfo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize order", e);
        }
    }
}
