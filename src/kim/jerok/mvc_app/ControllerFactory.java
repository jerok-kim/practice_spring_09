package kim.jerok.mvc_app;

import kim.jerok.mvc_app.controller.BoardController;
import kim.jerok.mvc_app.controller.UserController;
import kim.jerok.mvc_app.model.BoardRepository;
import kim.jerok.mvc_app.model.UserRepository;

public class ControllerFactory {

    private static BoardRepository boardRepository = new BoardRepository();
    private static UserRepository userRepository = new UserRepository();
    private static BoardController boardController = new BoardController(boardRepository);
    private static UserController userController = new UserController(userRepository);

    public static BoardController getBoardController() {
        return boardController;
    }

    public static UserController getUserController() {
        return userController;
    }

    private ControllerFactory() {
    }


}
