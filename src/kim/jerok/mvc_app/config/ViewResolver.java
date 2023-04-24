package kim.jerok.mvc_app.config;

public class ViewResolver {

    private static String prefix = "/WEB-INF/views";
    private static String suffix = ".jsp";

    public static String resolve(String viewName) {
        return prefix + viewName + suffix;
    }

}
