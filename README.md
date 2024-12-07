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
4. Hashing이란, 해시함수를 통해서 데이터를 해시값으로 변환하는 것을 말한다. 해시값은 원래 데이터로 돌아올 수 없다.
