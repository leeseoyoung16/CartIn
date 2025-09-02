# 📘 쇼핑몰 프로젝트

Spring Boot 기반으로 개발된 **쇼핑몰 프로젝트**입니다.  

---

## ⚙️ 기술 스택

- Java 17
- Spring Boot 3.5.3
- Spring Web / Spring Data JPA
- JWT / Spring Security
- Lombok / Validation
- MySQL
- Postman (API 테스트용)
- JUnit 5 / AssertJ / Spring Boot Test (테스트 코드 작성용)

---

## 🛠️ 진행 상황

| 기능 항목 | 상태 | 비고 |
|-----------|------|------|
| 사용자 인증 | ✅ | 회원가입, 로그인 기능, jwt 인증 |
| 상품 CRUD | ✅ | 상품 등록 및 삭제 (관리자 기능), 조회 |
| 사진 첨부 | ✅ | String -> MultipartFile  교체 |
| 주문 | ✅ | 상품 주문 및 주문 취소 |
| 장바구니 | 🔄️ | 장바구니 담기 |

---

## 🔗 주요 API 명세

### 🔐 사용자 인증
| 메서드 | URI            | 설명             |
|--------|----------------|------------------|
| POST   | `/auth/signup`   | 회원 가입      |
| POST   | `/auth/login`   | 로그인      |
| GET   | `/auth/me`   |  내 정보 조회    |

### 🛍️ 상품
| 메서드 | URI            | 설명             |
|--------|----------------|------------------|
| POST   | `/products`   | 상품 등록      |
| PATCH   | `/products/{productId}`   | 상품 수정      |
| DELETE   | `/products/{productId}`   | 상품 삭제      |
| GET   | `/products`   | 전체 상품 조회      |
| GET   | `/products/{productId}`   | 상품 단건 조회       |

### 📦 주문
| 메서드    | URI                        | 설명                  |
| ------ | -------------------------- | ------------------- |
| POST   | `/orders`                  | 주문 생성               |
| PUT    | `/orders/{orderId}`        | 주문 수정 (`ORDERED` 상태만) |
| DELETE | `/orders/{orderId}`        | 주문 취소               |
| PATCH  | `/orders/{orderId}/status` | 주문 상태 변경 (판매자용)     |
| GET    | `/orders/me`               | 내 주문 내역 조회          |
| GET    | `/orders/seller/me`        | 판매자 주문 내역 조회        |


---
## 📖 프로젝트 정리
[프로젝트 정리](https://longhaired-stove-0a0.notion.site/Cartin-23ac595094988085b144e15e4644cfa7)
