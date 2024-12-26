# 폼 로그인 샘플 프로젝트

## 프로젝트 정보

### 프로젝트 정보

- spring boot : 3.3.5
- java : 21
- build : maven
- packaging : war
- groupId : com.example
- artifactId : spring-formlogin

### 라이브러리 의존성

- spring-boot-starter-web
- tomcat-embed-jasper
- jakarta.servlet-api
- jakarta.servlet.jsp.jstl
- jakarta.servlet.jsp.jstl-api  
- spring-boot-starter-security
- spring-security-taglibs  
- h2
- spring-boot-starter-data-jpa
- spring-boot-devtools
- modelmapper
- lombok

### 패키지 및 클래스 정보
- controller
  - HomeController : 요청을 처리하는 컨트롤러 클래스
  - 다양한 컨트롤러 클래스
-  model
  - ERole.java : 사용자 권한 enum
  - User.java : 사용자 정보 엔티티 클래스
- payload.user
  - SignupUserRequest.java : 회원가입 요청 정보를 담는 클래스
- repository
  - UserRepository.java : 사용자 정보에 대한 데이터베이스 엑세스를 제공하는 인터페이스
- security
  - CustomUserDetails.java : 사용자 인증에 필요한 사용자 정보를 표현하는 클래스
  - CustomUserDetailsService.java : 사용자 인증에 필요한 사용자 정보를 제공하는 서비스 클래스
  - LoginUser.java : 로그인된 사용자 정보를 표현하는 클래스
  - SecurityConfig.java : 스프링 시큐리티 설정 클래스
- service
  - UserService.java : 사용자관련 업무로직(회원가입, 수정 등) 서비스를 제공하는 클래스

### 로그인 처리 과정

```mermaid
sequenceDiagram
  participant C as Client
  participant AF as AuthenticationFilter
  participant AM as AuthenticationManager
  participant AP as AuthenticationProvider
  participant US as UserDetailsService
  participant UR as UserRepository

  C->>AF: 1. 로그인을 요청한다.(아이디/비밀번호)
  Note right of  AF : 아이디, 비밀번호를 담은 인증 전 Authentication객체 생성
  AF->>AM: 2. AuthenticationManager에게 인증 전 Authentication객체를<br> 전달하고, 인증처리를 위임한다.
  Note right of  AM : AuthenticationManager는 인증을 처리할 <br>AuthenticationProvider를 찾는다.
  AM->>AP: 3. AuthenticationProvider에게 인증 전 Authentication객체를<br> 전달하고 실질적인 인증처리를 위임한다.
  AP->>US: 4. UserDetailsService의 loadUserByUsername(username)<br> 호출해서 인증에 필요한 사용자정보를 조회한다.
  US->>UR: 5 UserRepository의 findByUsername(username)<br>를 호출해서 데이터베이스에서 사용자정보를 조회한다.
  UR-->>US: 6. 데이터베이스에서 조회된 사용자 정보를 반환한다.
  Note left of US : 사용자정보로 UserDetails객체를 생성한다.
  US-->>AP: 7. 인증에 필요한 정보가 포함된 UserDetails객체를 반환한다.
  Note left of AP : AuthenticationProvider는 UserDetails객체의 정보를 활용해서<br> 유저 인증작업을 수행한다.
  Note left of AP : 인증이 완료되면 Authentication 객체를 생성하고,<br> UserDetails와 Authorities를 저장한다.
  AP-->>AM : 8. 인증 후 Authentication 객체를 반환한다.
  AM-->>AF : 9. 인증 후 Authentication 객체를 반환한다.
  Note left of AF : 인증 후 Authentication 객체를 SecurityContext에 저장한다.
  AF-->>C : 10. 인증 완료



```
