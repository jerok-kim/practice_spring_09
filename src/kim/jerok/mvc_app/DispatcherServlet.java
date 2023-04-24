package kim.jerok.mvc_app;

import kim.jerok.mvc_app.controller.BoardController;
import kim.jerok.mvc_app.controller.UserController;
import kim.jerok.mvc_app.model.BoardRepository;
import kim.jerok.mvc_app.model.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// GET -> http://localhost:8080/board/list.do
// GET -> http://localhost:8080/board/saveForm.do
// POST -> http://localhost:8080/board/save.do
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 버퍼로 들어오는 모든 값을 UTF-8로 인코딩해서 받기
        req.setCharacterEncoding("utf-8");

        // 2. path를 파싱 (MVC의 공간을 구분)
        String path = getPath(req);
        System.out.println("path : " + path);

        // 3. action 파싱
        String action = getAction(req);
        System.out.println("action : " + action);

        // 4. 컨트롤러 객체 생성
        // BoardRepository boardRepository = new BoardRepository();
        BoardController boardController = new BoardController(new BoardRepository());  // DI (의존성 주입)
        UserController userController = new UserController(new UserRepository());

        // 5. 라우팅하기
        if (path.equals("board")) {
            switch (action) {
                case "saveForm":
                    String saveFormView = boardController.saveForm();
                    req.getRequestDispatcher(saveFormView).forward(req, resp);
                    break;
                case "save":  // POST로 게시글 쓰기 요청 (title, content)
                    String method = req.getMethod();
                    if (!method.equals("POST")) {
                        resp.setContentType("text/html; charset=utf-8");
                        resp.getWriter().println("POST로 요청해야 합니다");
                        break;
                    }
                    String title = req.getParameter("title");
                    String content = req.getParameter("content");
                    String saveRedirect = boardController.save(title, content);
                    resp.sendRedirect(saveRedirect);
                    break;
                case "list":
                    // MVC에 위임하는 코드
                    String listView = boardController.list(req);
                    req.getRequestDispatcher(listView).forward(req, resp);
                    break;
                default:
                    resp.sendRedirect("/board/list.do");  // case list로 가라!!
            }
        }

        if (path.equals("user")) {
            switch (action) {
                case "login":
                    String method = req.getMethod();
                    if (!method.equals("POST")) {
                        resp.setContentType("text/html; charset=utf-8");
                        resp.getWriter().println("POST로 요청해야 합니다");
                        break;
                    }
                    String username = req.getParameter("username");
                    String password = req.getParameter("password");
                    String loginRedirect = userController.login(req, username, password);
                    resp.sendRedirect(loginRedirect);
                    break;
                case "join":
                    String method2 = req.getMethod();
                    if (!method2.equals("POST")) {
                        resp.setContentType("text/html; charset=utf-8");
                        resp.getWriter().println("POST로 요청해야 합니다");
                        break;
                    }
                    String username2 = req.getParameter("username");
                    String password2 = req.getParameter("password");
                    String email = req.getParameter("email");
                    String joinRedirect = userController.join(username2, password2, email);
                    resp.sendRedirect(joinRedirect);
                    break;
                case "loginForm":
                    String loginFormView = userController.loginForm();
                    req.getRequestDispatcher(loginFormView).forward(req, resp);
                    break;
                case "joinForm":
                    String joinFormView = userController.joinForm();
                    req.getRequestDispatcher(joinFormView).forward(req, resp);
                    break;
                default:
                    resp.sendRedirect("/user/joinForm.do");
            }
        }
    }

    private String getPath(HttpServletRequest req) {
        // board/list.do
        String path = getUri(req).split("/")[0];  // board
        return path;
    }

    private String getAction(HttpServletRequest req) {
        String action = getUri(req).split("/")[1];
        action = action.replace(".do", "");
        return action;
    }

    // http://localhost:8080/board/list.do
    private String getUri(HttpServletRequest req) {
        String uri = req.getRequestURI();  // /board/list.do <- 호스트를 제외한 모든 것을 준다.
        uri = uri.substring(1);  // board/list.do
        return uri;
    }

}
