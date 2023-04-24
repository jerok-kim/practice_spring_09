package kim.jerok.mvc_app.config;

import kim.jerok.mvc_app.model.Board;
import kim.jerok.mvc_app.model.User;

import java.util.ArrayList;
import java.util.List;

public class DB {

    private static List<Board> boardList = new ArrayList<>();
    private static List<User> userList = new ArrayList<>();

    static {
        boardList.add(new Board(1, "제목", "내용"));
        boardList.add(new Board(2, "제목", "내용"));
        userList.add(new User(1, "홍길동", "GD.Hong", "gd.hong@email.com"));
        userList.add(new User(2, "임꺽정", "GJ.Lim", "gj.lim@email.com"));
    }

    public static List<Board> selectAll() {
        return boardList;
    }

    public static void insert(String title, String content) {
        int id = boardList.size() + 1;
        boardList.add(new Board(id, title, content));
    }

    public static void join(String username, String password, String email) {
        int id = userList.size() + 1;
        userList.add(new User(id, username, password, email));
    }

    public static User login(String username, String password) {
        try {
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        return user;
                    }

                    throw new RuntimeException("비밀번호가 일치하지 않습니다");
                }
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("존재하지 않는 user입니다");
        }
        return null;
    }

}
