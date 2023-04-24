## [최주호] 스프링 강의 - MVC 패턴

> ### MVC 소프트웨어 디자인 패턴
> 1. 모델: 데이터와 비즈니스 로직을 관리한다.
> 2. 뷰: 레이아웃과 화면을 처리한다.
> 3. 컨트롤러: 명령을 모델과 뷰 부분으로 라우팅한다.

#### * FrontController의 책임: 적절한 다른 Controller를 찾아주는 책임 (Dispatcher Servlet)

- 최초의 요청은 `FrontController`에게 한다.
- `FrontController`는 공통처리를 하고, 적절한 컨트롤러를 요청한다.
- 컨트롤러에서 Data가 필요하면 Model의 도움을 받은 뒤 Data를 `request`에 저장하고 View를 찾아서 응답한다.
- 컨트롤러에서 Data가 필요하면 Model의 도움을 받은 뒤 Data를 그대로 응답한다.
- 컨트롤러에서 Data가 필요없으면 View를 찾아서 응답한다.
- View를 찾을 때는 재요청(`request`)이 필요한데, 이를 Redirect라고 한다.
- 즉 `request`가 두 번 만들어진다.
- 이 때 문제가 발생한다. `request`에 저장된 데이터가 날아간다.
- `RequestDispatcher`를 사용하여 `request`를 유지해야 한다.

### MVC 패턴

```text
1. 주소
http://localhost:8080/board/save.do

2. 바디
title=입력한값&content=입력한값

3. 헤더
method : POST
content-type : "application/x-www-form-urlencoded; charset=utf-8"
```

### User MVC 만들기

1. 인증 MVC 만들기
   - views/user/loginForm.jsp 만들기
   - views/user/joinForm.jsp 만들기
   - model/User.java 모델 만들기
   - model/UserRepository.java 만들기
   - DispatcherServlet에 아래의 요구사항에 맞는 switch문을 만들고 적절한 Controller를 요청하기
   ```text
   1. POST 요청 -> http://localhost:8080/user/login.do
   x-www-form-urlencoded
   username=ssar&password=1234


   2. POST 요청 -> http://localhost:8080/user/join.do
   x-www-form-urlencoded
   username=ssar&password=1234&email=ssar@nate.com

   3. GET 요청 -> http://localhost:8080/user/joinForm.do
   4. GET 요청 -> http://localhost:8080/user/loginForm.do
   ```
   - controller/UserController.java 만들기
   - join이 요청되고 나면 DB 클래스에 한건의 User가 추가되게 하기
   - UserController에서 login 요청이 되고 나면 session에 키값 "user", 값 User 오브젝트를 담기
   - login이 완료되면, /board/list.do 를 리다이렉션하기

2. 심화
   - /board/list.do 요청이 있을 때, session 값을 체크한 뒤 user 값이 없으면 /user/login.do 로 리다이렉션 시키기
   - POST 요청시 유효성 검사를 진행하는데, 유효성 검사 실패시에 에러페이지를 응답해주기
   ```text
   /WEB-INF/views/err/badrequest.jsp
   ```
