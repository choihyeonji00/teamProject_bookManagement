# 📚 Book Management System
Java Console Application - Hanwha Beyond SW Camp 22th Project 4팀 


## 📌 프로젝트 개요
Book Management System은 Java 기반 콘솔 환경에서 동작하는 도서 관리 프로그램으로,
아래 기능들을 포함해 실제 도서관 서비스의 핵심 로직을 학습·구현하기 위해 제작되었습니다.

#### ✔ 도서 CRUD (등록, 조회, 수정, 삭제)
#### ✔ 도서 검색 / 정렬
#### ✔ 사용자 로그인
#### ✔ 도서 대여 / 반납
#### ✔ 도서 예약(Queue 기반 선착순 처리)

본 프로젝트는 객체지향(OOP) 구조, Java Collections 활용, 예외 처리, DTO–Service–UI 계층 설계 등 자바의 핵심 개념을 실제 서비스 로직에 적용하는 것을 목표로 합니다.

## 📁 디렉토리 구조
``` bash 
src/
└── com.ohgiraffers.section01.list
      ├── comparator
      │   └── AscendingPrice.java    
      │
      ├── dto
      │   ├── BookDTO.java            
      │   └── UserDTO.java             
      │
      ├── run       
      │   └── Application3.java      
      │
      └── service
          ├── BookService.java          
          └── RentalService.java         

```


## 🛠 기술 스택
| 영역             | 기술                                   |
| -------------- | ------------------------------------ |
| Language       | Java 17                              |
| Data Structure | ArrayList, Queue(LinkedList)         |
| Architecture   | DTO → Service → UI 구조                |
| 정렬             | Comparable(기본 정렬), Comparator(가격 정렬) |
| 예외 처리          | InputMismatchException, Null Check   |
| 개발 환경          | IntelliJ IDEA, GitHub                |


## 🎯 주요 기능 
### 1️⃣ 도서 관리 (CRUD)
- 도서 등록, 조회, 수정, 삭제 기능 제공
- 삭제 시 **상세 정보 미리 보기 + Y/N Double Check** 적용
- 수정 시 제목·저자·가격 변경 가능
- BookDTO 번호는 자동 증가 방식

### 2️⃣ 도서 검색 / 정렬
#### 🔍 검색
- 제목 부분 일치 기반 검색
- Null, 공백, 대소문자 입력 예외 처리 포함

#### 🔽 정렬
- 제목 오름차순 → ``` Comparable<BookDTO>```
- 가격 오름차순 → ```AscendingPrice Comparator ```
- 정렬 시 원본 리스트 보호, Deep Copy 리스트 사용

### 3️⃣ 사용자 로그인 시스템
- UserDTO에 **사용자 정보(ID/PW)** 저장
- **ID 고유성 검사 및 중복 방지 로직 적용**
-  로그인 성공 시에만 대여/반납/예약 기능 이용 가능

### 4️⃣ 도서 대여 / 반납 / 예약 시스템 
#### 📘 도서 대여
- ``` isAvailable = false ``` → 즉시 대여 가능
- 대여 완료 시 자동으로 ```isAvailable = false ```

#### 📙 도서 반납
- 예약 Queue가 없음 → 도서 상태만 복구
- 예약 Queue가 있음 → queue.poll() 사용자에게 자동 대여

#### 📗 도서 예약 (Queue 기반)
- 이미 대여 중인 도서에 대해 예약 신청 가능
- **LinkedList 기반 Queue** 사용 → 선착순 예약 처리
- 반납 시 다음 예약자 자동 배정

## 📘BookDTO 데이터 구조

| 필드명              | 설명                           |
| ---------------- | ---------------------------- |
| number           | 도서 고유 번호(PK)                 |
| title            | 제목                           |
| author           | 저자                           |
| genre            | 분류                           |
| publisher        | 출판사                          |
| year             | 출판년도                         |
| price            | 가격                           |
| isAvailable      | 대여 가능 여부                     |
| condition        | 책 상태 (good, fine, bad 등)     |
| reservationQueue | 예약 사용자 Queue (LinkedList 기반) |



## 🔧 개선 사항 (Enhancements)
프로젝트 개발 과정에서 Issue 기반으로 적용한 개선 사항입니다.

####  ✔ 도서 상태 기반 삭제 기능
| Before                   | After                                 |
| ------------------------ | ------------------------------------- |
| 개별 삭제만 가능하여 불량 도서 관리 어려움 | `bad` 상태 도서 전체 삭제 기능 추가               |
| 상태 기준 필터링 불가능            | “특정 제목 + good 미만 상태” 조합 검색 후 선택 삭제 가능 |


#### ✔ 예약 Queue 기능 개선 
| Before                   | After                                 |
| ------------------------ | ------------------------------------- |
| 개별 삭제만 가능하여 불량 도서 관리 어려움 | `bad` 상태 도서 전체 삭제 기능 추가               |
| 상태 기준 필터링 불가능            | “특정 제목 + good 미만 상태” 조합 검색 후 선택 삭제 가능 |


#### ✔ Deep Copy 정렬 방식 적용
| Before                      | After                         |
| --------------------------- | ----------------------------- |
| 예약 기능이 없어 대여 대기자 관리 불가      | LinkedList 기반 Queue 도입        |
| 반납 시 처리자가 직접 다음 사용자를 확인해야 함 | 반납 시 Queue 첫 번째 사용자 **자동 배정** |

#### ✔ Deep Copy 정렬 방식 도입
| Before                  | After                      |
| ----------------------- | -------------------------- |
| 정렬 시 원본 리스트가 변경되는 문제 존재 | 복사 생성자를 활용한 Deep Copy 후 정렬 |
| 데이터 무결성 위험              | 원본 데이터를 그대로 유지 가능          |


#### ✔ BookDTO 구조 개선
| Before        | After                    |
| ------------- | ------------------------ |
| 예약자 관리용 필드 없음 | `reservationQueue` 필드 추가 |
| Deep Copy 불가  | 복사 생성자 추가로 안정적 정렬 수행     |




## 🔁 시스템 동작 흐름도
### 1️⃣ 전체 시스템 흐름도

``` mermaid
flowchart TD
    A[프로그램 시작] --> B[로그인 메뉴 선택]
    
    B -->|로그인 성공| C[메인 메뉴]
    B -->|로그인 실패| B
    
    C --> D[1. 도서 목록 조회]
    C --> E[2. 도서 상세 조회]
    C --> F[3. 도서 추가]
    C --> G[4. 도서 수정]
    C --> H[5. 도서 삭제]
    C --> I[6. 도서 검색]
    C --> J[7. 도서 정렬]
    C --> K[8. 도서 대여]
    C --> L[9. 도서 반납]
    C --> M[0. 프로그램 종료]

    D --> C
    E --> C
    F --> C
    G --> C
    H --> C
    I --> C
    J --> C
    K --> C
    L --> C
```
### 2️⃣ 도서 대여·반납·예약 흐름도
``` ymI
[로그인]
     ↓
[메인 메뉴]
     ↓
도서 대여 요청
     ↓
책이 available?
       │
   Yes │ → 대여 완료 (isAvailable = false)
       │
       └── No → 예약 Queue에 사용자 추가
     ↓
반납 요청
     ↓
예약 Queue 존재?
       │
   Yes │ → queue.poll() 사용자에게 자동 대여
       │
       └── No → 도서 상태 available = true 로 복구


```


## 🧠 팀 회고 (Team Retrospective)
| 👤 팀원 | 💬 회고 |
|--------|--------|
| **강성훈** | <br><br><br> |
| **임원석** | <br><br><br> |
| **조민규** | <br><br><br> |
| **최현지** | BookService 도서 상태 기반 삭제 기능을 구현하면서 작은 기능이라도 사용자 입장에서 직관적으로 동작해야 하며, 예외 상황을 고려한 안정성도 중요하다는 점을 배웠습니다. 또한 문서화를 맡아 진행하며 팀 내에서 명확한 README 구조와 기록 관리가 협업 효율을 크게 높인다는 것을 체감했습니다. 이번 프로젝트를 통해 기능 구현뿐 아니라 사용성·문서 품질·협업 구조까지 고려하는 개발자로 성장하고 싶습니다. |




## 🧩 역할 분배
| 팀원 | 역할 |
|------|------|
| **강성훈** | BookDTO 구조 확장(예약 Queue 추가), 데이터 모델 개선 및 기능 안정성 향상 |
| **임원석** | UserDTO 개발, 사용자 인증(ID 고유성·중복 검사) 및 로그인 시스템 구축 |
| **조민규** | RentalService 핵심 로직 구현(로그인·대여·반납·예약), 전체 대여 흐름 설계 |
| **최현지** | BookService 로직 개선(도서 상태 기반 삭제 기능), deleteBook UX 기능 개선(Double Check), README 프로젝트 문서 관리 |






