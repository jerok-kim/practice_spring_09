package kim.jerok.mvc_app.controller;

import kim.jerok.mvc_app.config.ViewResolver;
import kim.jerok.mvc_app.model.Board;
import kim.jerok.mvc_app.model.BoardRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// 책임 : 클라이언트의 요청을 잘(유효성검사 = validation check) 받고, 응답(View, Data)
public class BoardController {

    // Controller => Repository 필요로 함
    // Controller는 Repository에 의존적이다
    // 의존적인 객체를 Dispatcher Servlet로 부터 주입 받을 것이다
    private BoardRepository boardRepository;

    // 초기화
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public String list(HttpServletRequest request) {
        // C - M - V
        System.out.println("list : 요청됨");
        
        // session 값을 체크한 뒤 user 값이 없으면 /user/login.do 로 리다이렉션
        if (request.getSession().getAttribute("user") == null) {
            return "/user/login.do";
        }
        List<Board> boardList = boardRepository.findAll();
        request.setAttribute("boardList", boardList);
        return ViewResolver.resolve("/board/list");
    }

    public String saveForm() {
        // C - V
        System.out.println("saveForm : 요청됨");
        return ViewResolver.resolve("/board/saveForm");
    }

    // 스프링은 컨트롤러에 매개변수를 적기만 하면 formUrlEncoded(디폴트) 데이터를 Dispatcher Servlet로 부터 전달받음
    public String save(String title, String content) {
        System.out.println("save : 요청됨");

        // 검증코드 : http method 4가지 중에!!
        // POST, PUT은 resource를 클라이언트로 부터 전달 받으니까!!
        if (title == null || title.equals("")) {
            return "/err/badrequest.jsp";
        }

        if (content == null || content.equals("")) {
            return "/err/badrequest.jsp";
        }

        boardRepository.save(title, content);
        return "/board/list.do";
    }

}
