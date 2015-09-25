package mobi.tattu.hola.model;

import java.io.Serializable;

/**
 * Created by cristian on 25/09/15.
 */
public class Category implements Serializable {
    public String name;
    public boolean checked;
    public Side layoutSide;

    public enum Side{
        LEFT,
        RIGHT;
    }

}
