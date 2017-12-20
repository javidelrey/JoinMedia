package es.fempa.javi.es.joinmedia;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Cristian on 18/12/2017.
 */

public class ImagenPropia extends android.support.v7.widget.AppCompatImageView {
    private int posicion;
    public ImagenPropia(Context context) {
        super(context);
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
}
