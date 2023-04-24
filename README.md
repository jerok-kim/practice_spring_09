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
