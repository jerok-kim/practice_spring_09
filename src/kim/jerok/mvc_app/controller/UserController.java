package kim.jerok.mvc_app.controller;

import kim.jerok.mvc_app.config.ViewResolver;
import kim.jerok.mvc_app.model.User;
import kim.jerok.mvc_app.model.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(HttpServletRequest request, String username, String password) {
        System.out.println("login : 요청됨");

        if (username == null || username.equals("")) {
            throw new RuntimeException("username이 없습니다");
        }

        if (password == null || password.equals("")) {
            throw new RuntimeException(("password가 없습니다"));
        }

        User user = userRepository.login(username, password);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "/board/list.do";
    }

    public String join(String username, String password, String email) {
        System.out.println("join : 요청됨");

        if (username == null || username.equals("")) {
            throw new RuntimeException("username이 없습니다");
        }

        if (password == null || password.equals("")) {
            throw new RuntimeException("password가 없습니다");
        }

        if (email == null || email.equals("")) {
            throw new RuntimeException("email이 없습니다");
        }

        userRepository.join(username, password, email);
        return "/user/loginForm.do";
    }

    public String loginForm() {
        System.out.println("loginForm : 요청됨");
        return ViewResolver.resolve("/user/loginForm");
    }

    public String joinForm() {
        System.out.println("joinForm : 요청됨");
        return ViewResolver.resolve("/user/joinForm");
    }

}
