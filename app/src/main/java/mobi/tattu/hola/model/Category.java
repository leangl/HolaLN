package mobi.tattu.hola.model;

import android.content.Context;

import java.io.Serializable;

import mobi.tattu.hola.R;

/**
 * Created by cristian on 25/09/15.
 */
public enum Category implements Serializable {
    POLITICA(R.string.category_politica),
    ECONOMIA(R.string.category_economia),
    EL_MUNDO(R.string.category_el_mundo),
    OPINION(R.string.category_opinion),
    SOCIEDAD(R.string.category_sociedad),
    BUENOS_AIRES(R.string.category_buenos_aires),
    ESPECTACULOS(R.string.category_espectaculos),
    TECNOLOGIA(R.string.category_tecnologia),
    DEPORTES(R.string.category_deportes),
    SEGURIDAD(R.string.category_seguridad);

    private int mName;
    public Side layoutSide;
    public boolean checked;
    Category(int name){
        this.mName = name;

    }
    public String getNameCategory(Context context){
        return  context.getString(this.mName);
    }

    public enum Side{
        LEFT,
        RIGHT
    }

}
