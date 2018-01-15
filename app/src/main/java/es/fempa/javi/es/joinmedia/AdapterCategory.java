package es.fempa.javi.es.joinmedia;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Cristian on 15/01/2018.
 */

public class AdapterCategory extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final String[] desc;
    private final Integer[] integers;

    public AdapterCategory(Activity context, String[] itemname, String[] desc, Integer[] integers) {
        super(context, R.layout.item_category, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.desc = desc;
        this.integers=integers;
    }

    public View getView(int posicion,View view, ViewGroup parent){

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.item_category,null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.category);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView4);
        TextView etxDescripcion = (TextView) rowView.findViewById(R.id.texto);

        txtTitle.setText(itemname[posicion]);
        imageView.setImageResource(integers[posicion]);
        etxDescripcion.setText(desc[posicion]);



        return rowView;
    }
}
