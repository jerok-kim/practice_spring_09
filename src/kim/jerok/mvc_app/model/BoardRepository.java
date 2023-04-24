package kim.jerok.mvc_app.model;

import kim.jerok.mvc_app.config.DB;

import java.util.List;

// 책임 : 데이터베이스 접근
public class BoardRepository {
    
    public List<Board> findAll() {
        // SELECT * FROM board
        return DB.selectAll();
    }
    
    // 클라이언트(x-www-form-urlencoded)
    // -> Dispatcher Servlet(request body)
    // -> Controller(title, content)
    // -> Repository(title, content)
    public void save(String title, String content) {
        // INSERT INTO board(title, content) VALUES("제목1", "내용1")
        DB.insert(title, content);
    }
    
}
