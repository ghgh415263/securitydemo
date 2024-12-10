<h1>스프링 시큐리티 default</h1>

1. 기본 login page
   - 아무 설정을 안해주면 기본 login page로 이동한다.
   - DefaultLoginPageGeneratingFilter를 참고하면, 로그인페이지를 띄워줘야하는 경우, 기본 로그인 페이지를 띄워준다.
   - 추후 커스텀 로그인 페이지 설정 or 인증에 대한 설정을 통해서 사용하지 않을 수 있음.
  
2. 전체 흐름
   - 기본 로그인 페이지에서 로그인 요청
   - UsernamePasswordAuthenticationFilter에서 요청안에서 username과 password를 꺼냄. 그리고 UsernamePasswordAuthenticationToken 생성함 (Authentication 인터페이스 구현체 중에 하나임)
   - ProviderManager의 authentivate() 메서드를 호출. ProviderManager에서 UsernamePasswordAuthenticationToken를 처리할 수 있는 provider를 찾음.
   - 찾아낸 DaoAuthenticationPovider가 UserDetailsService과 passwordEncoder를 이용해서 인증을 진행함
   - 인증 결과를 ProviderManager에게 리턴.
   - 인증 결과가 true면 UsernamePasswordAuthenticationFilter가 결과를 받고, 인증 결과가 false면 다른 provider가 처리하게 함.

3. SecurityFilterChain을 리턴하는 config를 통해서 url에 인증을 진행할지 안할지 설정가능하다.

4. Basic AuthN도 가능하다. 물론 disable()로 막을 수도 있다.

5. AuthenticationPovider or UserDetailsService 같은 것들은 구현체를 직접 커스텀하는 경우가 많다.
<br>

<h1>UserDetailsService 인터페이스</h1>

1. UserDetailsService 인터페이스는 loadUserByUsername 즉, r만 구현하면 됨

2. UserDetailManager 인터페이스는 cud랑 exist에 대한 추상함수도 가지고 있음
   - 기본구현체 InMemoryUserDetailsManager 메모리에 올려있는 회원정보를 이용.
   - 기본구현체 JdbcUserDetailsManager jdbc를 이용해서 db에 회원정보를 이용.

3. 필터에서 회원 crud를 모두 할꺼라면 UserDetailManager를 사용한다. 인증만 필터에서 할꺼면 UserDetailsService를 사용한다.
<br>

<h1>UserDetail & Authentication</h1>

1. UserDetail는 회원 정보를 로딩하는 용도로 사용한다. 주로 UserDetailsService나 UserDetailManager에서 사용된다.
2. Authentication 인증 여부가 담긴다. 주로 AuthenticationProvider나 AuthenticationManager에서 사용된다.
<br>

<h1>Encoding & Encryption & Hashing</h1>

1. Encoding이란, 다른 형태의 데이터로 바꾸는 것을 말한다. 원래 시크릿키같은 것 없이 데이터로 다시 돌아올 수 있다. UTF-8 같은 것들을 말한다.

2. Encryption이란, 기밀성을 충족하는 데이터 변환을 말한다. 키를 사용하여 데이터를 암호화하고 복호화하는 것들을 말한다.
   - 대칭 암호화란, 암호화와 복호화에 같은 키가 쓰이는 것을 말한다. 주로 데이터 저장에 쓰인다.
   - 비대칭 암호화란, 암호화와 복호화에 쓰는 키가 다른 것을 말한다. 주로 데이터 전송에 쓰인다. 전송할 데이터를 개인키로 암호화해서 보내면 받는 쪽에서는 공개키로 복호화한다.

3. Hashing이란, 해시함수를 통해서 데이터를 해시값으로 변환하는 것을 말한다. 해시값은 원래 데이터로 돌아올 수 없다.
   - 다시 되돌릴 수 없다는 특성 덕분에, 주로 비밀번호 저장에 사용된다.
   - 무차별 대입 공격과 레인보우테이블 공격을 막기 위해서 일부러 해시값 계산을 느리게 만들거나, salt를 사용함.
   - 스프링 시큐리티에서는 PasswordEncoder 구현체에서 해싱알고리즘을 이용한다.
<br>

<h1>Exception Handling</h1>

1. AuthenticationEntryPoint 구현체가 AuthenticationException이 발생하면 호출된다.
   - http basic 일때, 기본으로 등록되는 AuthenticationEntryPoint 구현체가 호출된다.
   - 만약 커스텀 AuthenticationEntryPoint가 등록되면 커스텀한 구현체가 호출된다.

2. AccessDeniedHandler
   - 구현체 등록하면 인가 실패시 호출된다.
  
3. ExceptionTranslationFilter에서 등록된 구현체를 실행시킨다.
<br>

<h1>Session Management</h1>

1. 세션 고정 공격 방어
   - 로그인시에 새로운 세션을 발급하게 함 (newSession 정책)
   - changeSessionId, migrateSession 정책도 있음

2. 인증시 이벤트 생성
   - DefaultAuthenticationEventPublisher가 인증이 실패하거나 성공했을때, 이벤트를 발행
<br>

<h1> ModelAndView에서 인증 </h1>

1. login 설정들 설정가능
   - 로그인 페이지 url
   - usernameParameter 명, passwordParameter명
   - 성공시 url, 실패시 url를 다 설정가능

2. logout 관련된 설정들도 다 설정가능
   - 로그아웃 성공시 url
   - http세션 비활성화
   - Authentication 비우기
   - 쿠키 지우기

3. 타임리프와의 통합도 가능 (통합용 디팬던시 추가해야함)
   - isAuthenticationed() 같은 것들을 타임리프에서 사용가능
<br>

<h1>SecurityContext</h1>

1. Authentication 객체가 저장되는 보관소

2. 기본으로는 ThreadLocal에 저장된다. 물론, 다른 전략들도 있다.
   - 비동기에서도 사용가능한 MODE_INHERITABLETHREADLOCAL
   - 모든 쓰레드에서 접근가능한 MODE_GLOBAL

3. SecurityContextHolder는 SecurityContext를 쉽게 접근하기 위한 헬퍼 클래스이다.
   - Bean들은 SecurityContextHolder에서 SecurityContext를 가져온다.
   - controller에서 argument로 Authentication을 가져온다.
<br>

<h1>CORS & CSRF</h1>

1. CORS (CROSS-ORIGIN RESOURCE SHARING)
   - 브라우저의 기본 기능. 클라이언트에서 HTTP요청의 헤더에 Origin을 담아 전달 -> 서버는 응답헤더에 Access-Control-Allow-Origin을 담아 클라이언트로 전달 -> 클라이언트에서 비교
   - 다른 오리진으로 API콜하면 해당 콜을 블락시켜버림
   - 같은 오리진은 scheme, domain, port가 같아야한다.
   - 서버의 cors 설정을 통해서 다른 오리진의 요청받을 수 있게 한다.

2. CSRF (CROSS-SITE REQUEST FORGERY)
   - A사이트에서 로그인한 사용자가 악성사이트에 들어감 -> 악성사이트 내의 폼으로 이메일 변경 같은 요청보내면 현 도메인에 상관없이 현 쿠키를 보내버린다. -> A사이트의 세션용쿠키가 보내지면서 인증이되어버린다.
   - 해커는 굳이 쿠키를 탈취할 필요도 없음
   - CSRF 토큰을 추가로 발급하는 것으로 설정하여, 공격을 막는다.
   - CSRF 토큰은 세션이나 쿠키에 저장한다. 현재는 주로 쿠키에 사용된다.
   - 요청시에 CSRF 토큰을 헤더 같은 곳에 담아서 전달하면 토큰저장소에서 CSRF 토큰을 가져와서 두개를 비교한다. 만약 다르면, 403 응답을 보낸다.
   - 만약 타임리프를 사용한다면 input tag로 csrf 토큰을 프론트로 보낸다.
   - https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html 참고
<br>

<h1>AUTHORITY & ROLE</h1>

1. AUTHORITY
   - 특정 액션마다의 권한을 말한다. GET ACCOUNT 이런 것들..
   - 시큐리티 설정에서 URL마다 설정가능하다.
   - 실패시 권한거부이벤트 발생

2. ROLE
   - 특정 액션들의 모음에 대한 권한을 말한다. USER, ADMIN 같은 것들..
   - 역시 설정가능하다.
   - 보통 prefix가 붙는다. (ROLE_). prefix는 커스텀 가능하다.
   - 실패시 권한거부이벤트 발생
<br>

<h1>Filter</h1>

1. 필터는 주로 input validation, logging, 암호화, tracing 등에 사용한다.

2. @EnableWebSecurity(debug=true) 시큐리티 필터들을 모두 로그로 확인가능

3. FilterChain이란 필터의 collection을 말한다.

4. 시큐리티 필터는 addFilterBefore, after, at 등으로 추가 가능하다.
   - at의 경우에는 특정 필터를 오버라이드하는 게 아니라 같은 위치로 들어간다고 보면된다. 같은 위치에 있는 필터들은 호출 순서를 예측할 수 없다

6. GenericFilterBean
   - Filter를 확장하여 Spring에서 제공하는 filter. 기존 Filter에서 얻어올 수 없는 정보였던 Spring의 설정 정보를 가져올 수 있게 확장된 추상 클래스

7. OncePerRequestFilter
   - GenericFilterBean를 확장한 filter. 한 요청 내에서 한번만 호출되는 것을 보장하게 만듬.
<br>

<h1>JWT 토큰</h1>

1. Opaque 토큰 vs Jwt 토큰
   - Opaque 토큰은 랜덤 string으로 아무런 정보를 담고 있지 않다. 만약 토큰을 validate할려면 발급한 서버에 요청을 보내야한다.
   - Jwt 토큰은 정보를 담고 있다. stateless하므로 발급한 서버에 요청을 안보내도 validate 가능.

2. 토큰의 장점
   - 만료시간 설정 가능
   - 정보를 지닐 수 있음. (jwt같은 토큰이라면)
   - 다양한 플랫폼에서 활용가능. 웹, 모바일 등등
   - 다양한 도메인, 서비스에서 재활용가능. sso 같은 것들..
   - 토큰으로 인증하면, 모든 회원 정보를 노출하지도 않음.

3. JWT 특징
   - 인증/인가에 사용가능
   - STATELESS하다
   - 형태는 해더.페이로드.시그니처(OPTIONAL)
   - 해더와 페이로드는 BASE64로 인코딩
   - 시그니처는 BASE64(해더).BASE64(페이로드)를 시크릿키로 암호화한다.
   - 인증시에는 PUBLIC KEY로 복호화해서 인증한다.

4. JWT 토큰 적용
   - 토큰으로 인증하는 AuthenticationProvider를 구현한다.
<br>

<h1>메서드 수준 시큐리티</h1>

1. 스프링 AOP로 구현되어 있음
   - @PreAuthorize 호출전에 권한 검사
   - @PostAuthorize 호출후에 리턴값에 관련된 권한 검사 ("returnObject.owner == authentication.name")
   - @PreFilter 호출전에 파라미터 필터링 -> 걸러냄
   - @PostFilter

2. @EnableMethodSecurity를 해줘야함.

3. 같은 어노테이션은 여러개 적용 불가능.
