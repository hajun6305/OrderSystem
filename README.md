# 휴머스온 Tech 과제
**ROOT Page**
URL : http://localhost:8080/

## 1. 과제 안내
[데이터 연동] 주문 데이터 연동 인터페이스 설계 

주문 관리 시스템을 설계하고, 외부 시스템과의 데이터 연동을 위한 인터페이스를 구현하세요. 이 시스템은 외부에서 주문 데이터를 가져와 저장하고, 내부 데이터를 외부로 전송하는 기능을 제공합니다. 

#### 요구사항 
- 주문 관리 시스템: 
외부 시스템으로부터 주문 데이터를 가져와 시스템에 저장하는 기능을 설계하고 구현하세요. 
주문은 주문 ID, 고객 명, 주문 날짜, 주문 상태(예: 처리 중, 배송 중, 완료) 등의 속성을 가져야 합니다. 

- 외부 시스템과의 데이터 연동
외부 시스템에서 JSON 형식의 주문 데이터를 HTTP를 통해 받아와 내부 시스템에 저장하세요. 
주문 데이터를 내부 시스템에서 JSON 형식으로 변환하여 외부 시스템으로 전송하는 기능을 구현하세요. 

- 데이터 저장 및 조회
받아온 주문 데이터를 메모리에 저장하세요. 이 데이터는 주문 ID를 통해 조회할 수 있어야 합니다. 
저장된 주문 데이터를 리스트 형식으로 반환하는 기능도 구현하세요. 

- 데이터 연동 인터페이스 설계
외부 시스템과의 데이터 연동을 담당하는 인터페이스를 설계하세요. 
인터페이스는 외부 시스템과의 통신 로직과 데이터 변환 로직을 포함해야 합니다.   

- 예외 처리
데이터 연동 시 발생할 수 있는 다양한 예외 상황(예: 네트워크 오류, 데이터 형식 오류)에 대한 처리 로직을 설계하고 구현하세요. 

#### 설계 요구사항
- 클래스 다이어그램 작성: 외부 시스템과의 데이터 연동을 담당하는 주요 클래스와 이들 간의 관계를 나타내는 클래스를 설계하세요. 각 클래스의 역할과 책임을 간단히 설명하세요. 
- 인터페이스 설계: 외부 시스템과의 데이터 통신을 위한 인터페이스를 정의하고, 이 인터페이스를 구현하는 방식을 설계하세요. 
- 확장성 고려: 향후 다른 형태의 외부 시스템과 연동할 가능성을 고려하여 설계의 유연성과 확장성을 확보하세요. 

#### 구현 요구사항
- 설계한 인터페이스를 기반으로 주문 데이터를 외부 시스템에서 가져와 저장하고, 내부 데이터를 외부 시스템으로 전송하는 기능을 구현하세요.
- 데이터는 메모리에 저장하는 방식으로 처리하며, 별도의 데이터베이스 연동은 필요하지 않습니다.

## 2. API LIST

#### 2.1 주문 데이터 조회

> URL : /api/orders/getOrderInfo/{orderNo}
>
> Method : GET

#### Parameter 

| 구분  | Type   |
| :---- | :----- |
| orderNo | String |

#### 2.2 주문 데이터 조회(전체)

> URL : /api/orders/getAllOrderInfo
>
> Method : GET

#### 2.3 주문 데이터 저장

> URL : /api/orders/saveOrderInfo
>
> Method : POST

#### Parameter 

| 구분  | Type   |
| :---- | :----- |
| orderNo | String |
| customerName | String |
| orderDate | LocalDate |
| orderStatus | String |

#### 2.4 외부 시스템 연동(내부로 저장)

> URL : /api/orders/fetch
>
> Method : POST

#### 2.5 외부 시스템 연동(내부 주문정보 외부시스템 전송)

> URL : /api/orders/sendOrder
>
> Method : POST

| 구분  | Type   |
| :---- | :----- |
| orderNoList | List(String) |

## 3. Service 클래스

#### 3.1 OrderService
##### 주문 관리 서비스이며 내부시스템의 데이터를 관리하고 외부 서비스에 접근할때 사용
- saveOrder : 주문 저장 하는 매서드
- getOrderInfo :주문 조회(단일)하는 매서드
- getAllOrderInfo :주문 조회(전체)하는 매서드
- fetchAndSaveOrderData : 외부 데이터를 저장할 때 사용하는 매서드
- sendOrderToExternalSystem(List(String) orderNoList) : 주문 데이터 외부 시스템 전송 하기 위한 매서드
- saveOrderProc(List(OrderInfo) orderList) : 주문 목록을 저장 하기 위한 매서드
- saveOrderProc(OrderInfo orderInfo) : 주문 저장 처리하는 매서드(실질적인 저장 처리)
  
#### 3.2 ExternalApiService
##### 외부 시스템에 접근할때 사용하는 서비스
- fetchOrdersFromExternalSystem : 외부 시스템에서 주문 데이터를 가져오기 위한 매서드
- sendOrderToExternalSystem(List(OrderInfo) orderList) :주문 데이터 목록을 외부 시스템으로 전송하는 매서드
- serializeOrder(OrderInfo orderInfo) : 연동 데이터 변환하는 매서드
