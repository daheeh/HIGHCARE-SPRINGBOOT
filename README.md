# HIGHCARE-SPRINGBOOT
하이케어 그룹웨어 프로젝트 - 백엔드

<br>

## 🖥️ 프로젝트 소개
사내 효율적인 업무소통 및 협업을 위한 그룹웨어 플랫폼
<br>
<br>
<img width="700" alt="image" src="https://github.com/user-attachments/assets/f5e53f43-b07f-45c2-822e-37911e5a7c01" />
<br>
<br>
<img width="700" alt="image" src="https://github.com/user-attachments/assets/7f9704e2-67ca-470b-b697-be22ecdd1e4c" />
<br>

## 🕒 개발 기간
* 23.08. - 23.09.14

<br>

### 🧑‍🤝‍🧑 멤버구성
 - **팀원1 : 홍다희 - 실시간 채팅**
 - 팀원2 : 김나경 - 전자결재
 - 팀원3 : 전아림 - 인사관리, 조직도
 - 팀원4 : 조혜란 - 마이페이지
 - 팀원5 : 허유일 - 시설 예약, 게시판
 - 팀원6 : 황다혜 - 소셜로그인, 회원가입, 권한관리
 - 공통  : 서비스 기획 및 ERD설계, REST API 개발

<br>

### ⚙️ 개발 환경
- `Java 11`
- **IDE** : IntelliJ 2021, Postman
- **Framework** : Spring Boot(2.7.14)
- **Database** : Oracle(18c), Redis(7.x)
- **ORM** : JPA

<br>

## 📌 맡은 기능
#### [실시간 채팅](https://github.com/daheeh/HIGHCARE-SPRINGBOOT/tree/main/highcare/src/main/java/com/highright/highcare/chatting)
- **WebSocket을 통한 실시간 메세지 송수신**  
   - WebSocket과 STOMP 프로토콜을 사용해 실시간 메세지 송수신
- **Redis를 통한 채팅 데이터 저장 및 캐싱**  
   - Redis의 Pub/Sub 구조로 WebSocket과 연동하여 확장성과 안정성 확보
- **Storage를 통한 데이터 중복 캐싱으로 안정성 확보**
  - 사용자와 채팅방 데이터를 관리하는 UserStoage, ConversationStorage로 데이터 중복 방지
  - 데이터 캐싱을 통해 빠른 응답시간과 안정성 유지



<br>
