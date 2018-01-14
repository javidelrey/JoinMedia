package es.fempa.javi.es.joinmedia;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MontajeVideo extends AppCompatActivity {

    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;
    GridLayout gridLayoutMontaje;
    private File file;
    private ArrayList<ImagenPropia> arrayImagen = new ArrayList<ImagenPropia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montaje_video);
        gridLayoutMontaje = findViewById(R.id.gridLayoutMontaje);
/*
        viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);

        ImageView img = new ImageView(getApplicationContext());
        img.setImageDrawable(getDrawable(R.drawable.registro));


        viewFlipper.addView(img);
        gridLayoutMontaje = findViewById(R.id.gridLayoutMontaje);

        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
        //sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();*/
        ObjectInputStream ois = null;
        try
        {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput(MainActivity.NOMBRE_FICHERO)));

            //String texto = fin.readLine();
            //fin.close();
            FileInputStream fis = openFileInput(MainActivity.NOMBRE_FICHERO);
            ois = new ObjectInputStream(fis);
            Bitmap imp = (Bitmap) ois.readObject();
            /*Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            ImagenPropia ip = new ImagenPropia(getApplicationContext());
            ip.setImageBitmap(imp);
            arrayImagen.add(ip);*/
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Log.e("Ficheros", "Error al leer fichero desde memoria interna"+ex.getMessage());
        }
        for(int i = 0; i<arrayImagen.size();i++){
            gridLayoutMontaje.addView(arrayImagen.get(i));
        }
    }


}
