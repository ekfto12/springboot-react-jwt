# springboot-react-jwt

## springboot + react 를 이용한 jwt 로그인 토이 프로젝트 개발


* ### 개발환경 
  * Front : React(VS Code)
  * back : Springboot 2.4.4(STS)
  * DataBase : Maria DB(DBeaver)
  * 관련 기술 : JWT, Spring Security, JPA 

* ### 목적
  * JWT를 이해하고 Refresh Token 재발급까지 가능하도록 개발
  * React와 연동하기 때문에 Spring은 REST API 구조로 개발

* ### 추후 개선 방안
  * Refresh Token 저장소를 RDB에서 인메모리 방식에 키-값 데이터 저장소인 Redis로 관리하여 효율적으로 관리하기

* ### 참고 자료
  * https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt#
  * https://velog.io/@dsunni/Spring-Boot-React-JWT%EB%A1%9C-%EA%B0%84%EB%8B%A8%ED%95%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
  * https://bcp0109.tistory.com/entry/Spring-Boot-JWT-Security-%EA%B2%89%ED%95%A5%EA%B8%B0
