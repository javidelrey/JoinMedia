package es.fempa.javi.es.joinmedia;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MontajeVideo extends AppCompatActivity {

    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montaje_video);

        viewFlipper = (ViewFlipper) this.findViewById(R.id.bckgrndViewFlipper1);
/*
        Bitmap imagen = getIntent().getExtras().getParcelable("imagenes");
        ImagenPropia img = new ImagenPropia(getApplicationContext());
        img.setImageBitmap(imagen);
*/
        ImageView img = new ImageView(getApplicationContext());
        img.setImageDrawable(getDrawable(R.drawable.registro));


        viewFlipper.addView(img);

/*
       ArrayList arrayList = getIntent().getExtras().get

        for(int i = 0; i<arrayImagen.size();i++){
            viewFlipper.addView((View) arrayImagen.get(i));
        }
*/


        fade_in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);
//sets auto flipping
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();
    }


}
