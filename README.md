# SampleSecurityLogin
개발목적 : 스프링의 시큐리티 로그인을 사용하기 위한 샘플 코드입니다.

# SecurityConfig
비추천의 WebSecurityConfigurerAdapter 사용을 배제하고SecurityFilterChain을 사용

# Security 로그인 참고자료
[3-05 스프링 시큐리티](https://wikidocs.net/162150)  
[3-06 회원가입](https://wikidocs.net/162141)  
[3-07 로그인과 로그아웃](https://wikidocs.net/162255)  

# 사용가능한 기능 일람
1. 멤버 등록
   + 멤버 등록페이지 유효성 검사
   

2. 멤버 로그인
   + 스프링 시큐리티 로그인
   + 로그인 세션을 확인하여 로그인 유지


3. 멤버 로그아웃
   + 스프링 시큐리티 로그아웃

# Mustache 템플릿에서 제공 되어지지 않는 기능을 추가구현
1. form태그 내부에 _csrf 토큰값 추가 
   + application.properties에 리퀘스트 속성 값 사용을 true로 설정
     ```properties
     # 스프링부트 2.7 이전
     spring.mustache.expose-request-attributes=true
     # 스프링부트 2.7 이후
     spring.mustache.servlet.expose-request-attributes=true
     ```
   + 템플릿의 form태크 내부에 아래의 input 태그를 추가
     ```html
     <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
     ``` 
   + 컨트롤러 내부에서 모델을 통한 값 지정은 불필요
   + 참고자료:https://stackoverflow.com/questions/26397168/how-to-use-spring-security-with-mustache  


2. 로그인 예외처리 메세지 표시처리
   + 인증 프로바이더를 작성하여 예외 패턴별 Exception 작성
     > [config.AuthProvider](./src/main/java/com/example/SampleSecurityLogin/config/AuthProvider.java)
   + 인증 실패 핸들러를 작성하여 Exception 별 에러 메세지 설정
     > [config.AuthFailuerHandler](./src/main/java/com/example/SampleSecurityLogin/config/AuthFailuerHandler.java)
   + GET으로 리다이렉트 되어지며 설정된 RequestParam를 취득하여 모델에 설정
     > [web.MemberController](./src/main/java/com/example/SampleSecurityLogin/web/MemberController.java)
   + 참고자료:https://dev-coco.tistory.com/126, https://dabok407.tistory.com/37
