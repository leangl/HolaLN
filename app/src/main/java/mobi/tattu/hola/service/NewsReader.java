package mobi.tattu.hola.service;

/**
 * Created by cristian on 26/09/15.
 */
public class NewsReader {
    private static NewsReader ourInstance = new NewsReader();

    public static NewsReader getInstance() {
        return ourInstance;
    }

    private NewsReader() {
    }
}
