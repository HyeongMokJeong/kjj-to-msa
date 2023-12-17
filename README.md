# kjj-to-msa
- 프로젝트 KJJ에 대한 MSA 전환 프로젝트입니다.  
- 2023.11 ~

## 개요
- 이전에 개발한 구내식당 예약 시스템의 백엔드 부분에 대한 리팩토링 프로젝트 입니다.  
- 기존의 모놀리식 아키텍처에서 탈피하여, MSA로의 전환을 목표로 하고 있습니다.

## 조건
- API를 이용하던 클라이언트의 코드를 수정하지 않고도 동일하게 동작해야 한다.

## 아키텍처
<img src="https://github.com/HyeongMokJeong/kjj-to-msa/assets/94634916/32d3c1da-cb67-4106-99e8-b47ae42eee5c"/>

## 프로젝트 구조
📦kjj-to-msa  
 ┣ 📂api-gateway : Spring Cloud Gateway 기반 라우팅 서버  
 ┣ 📂calculate : 정산 관련 도메인을 다루는 서버  
 ┣ 📂client : 마이크로 서비스 간 통신을 처리하는 Proxy 서버  
 ┣ 📂image : 이미지 관련 서비스를 수행하는 서버  
 ┣ 📂menu : 메뉴 관련 도메인을 다루는 서버  
 ┣ 📂no-auth : 로그인, 회원가입 등 JWT 인증이 불필요한 로직을 담당하는 서버  
 ┣ 📂planner : 식당 영업 관련 도메인을 다루는 서버  
 ┣ 📂store : 식당 관련 도메인을 다루는 서버  
 ┗ 📂user : 유저 관련 도메인을 다루는 서버  
