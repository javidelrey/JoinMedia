package es.fempa.javi.es.joinmedia;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    static MediaPlayer mp;
    static MediaPlayer mp2;
    static MediaPlayer mp3;
    static MediaPlayer mp4;
    static MediaPlayer mp5;

    TextView tv ;
    GridLayout layout;
    private int posicion;
    private ArrayList<Integer> pos = new ArrayList<Integer>();
    public static ArrayList<ImagenPropia> arrayImagen = new ArrayList<ImagenPropia>();
    private ArrayList<ImagenPropia> arrayImagenBorrar = new ArrayList<ImagenPropia>();
    private static final int SELECT_FILE = 1;
    int cont=0;
    private ImageView img;
    private FloatingActionButton procesar;
    public static String NOMBRE_FICHERO = "imagenes.dat";
    private File file;
    private static String cancion ="";
    private String canciones[]=new String[]{"We are the Champions","Crazy Frog","Nyan Cat","Ghost Busters","El pollito pio"};
    private String desc[]= new String[]{"Crazy Frog","Crazy Frog","Daniwell","Ray Parker, Jr.","Pulcino Pio"};
    private Integer[] imgid={
            R.drawable.champions,
            R.drawable.crazyfrog,
            R.drawable.nyancat,
            R.drawable.ghostbusters,
            R.drawable.pollito,
    };

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mp = MediaPlayer.create(this,R.raw.champions);
        mp2 = MediaPlayer.create(this,R.raw.crazyfrog);
        mp3 = MediaPlayer.create(this,R.raw.nyancat);
        mp4 = MediaPlayer.create(this,R.raw.ghostbusters);
        mp5 = MediaPlayer.create(this,R.raw.pollitopio);


        AdapterCategory adapter=new AdapterCategory(this, canciones,desc,imgid);
        lista=(ListView)findViewById(R.id.song_list);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= canciones[+position];
                Toast.makeText(getApplicationContext(), "Se ha seleccionado la cancion, "+Slecteditem, Toast.LENGTH_SHORT).show();
                switch (position+1){
                    case 1: cancion = "cancion1";
                    break;
                    case 2: cancion = "cancion2";
                        break;
                    case 3: cancion = "cancion3";
                        break;
                    case 4: cancion = "cancion4";
                        break;
                    case 5: cancion = "cancion5";
                        break;
                    default: cancion = "error";
                        break;
                }
            }
        });







        SharedPreferences preferences= getSharedPreferences("Preferencias", MODE_PRIVATE);
        String imagen= preferences.getString("Imagen", null);
        img = new ImagenPropia(getApplicationContext());
        layout = (GridLayout) findViewById(R.id.gridLayout);
        procesar = findViewById(R.id.procesar);
        //file = new File(NOMBRE_FICHERO);
        file = new File(getApplicationContext().getFilesDir(), NOMBRE_FICHERO);

        procesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayImagen.size()==0){
                    Snackbar.make(view, "no has seleccionado ninguna imagen", Snackbar.LENGTH_SHORT).show();
                }else{
                    if(!cancion.equals("")) {
                        switch (cancion) {
                            case "cancion1":
                                mp.start();
                                break;
                            case "cancion2":
                                mp2.start();
                                break;
                            case "cancion3":
                                mp3.start();
                                break;
                            case "cancion4":
                                mp4.start();
                                break;
                            case "cancion5":
                                mp5.start();
                                break;
                            default:
                                break;
                        }
                    }
                    Intent intent = new Intent(MainActivity.this, MontajeVideo.class);

                    startActivity(intent);
                }
            }
        });

        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.iconDelete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayImagenBorrar.size()==0) {
                    Toast.makeText(MainActivity.this, "Selecciona una img para borrarla", Toast.LENGTH_SHORT).show();
                }else{
                    layout.removeAllViews();

                    for(ImagenPropia p : arrayImagenBorrar){
                        arrayImagen.remove(p);
                    }

                    for(int i = 0; i<arrayImagen.size();i++){
                        layout.addView(arrayImagen.get(i));
                    }
                    pos = new ArrayList<Integer>();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    public static void pararCancion(){
        switch (cancion){
            case "cancion1": mp.stop();
                break;
            case "cancion2": mp2.stop();
                break;
            case "cancion3": mp3.stop();
                break;
            case "cancion4": mp4.stop();
                break;
            case "cancion5": mp5.stop();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setMessage("¿Está seguro de que quiere cerrar sesión?")
                    .setTitle("Salir");

            builder.setPositiveButton("Cerrar sesión", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(
                    Intent.createChooser(intent, "Seleccione una img"),
                    SELECT_FILE);


        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImageUri = null;
        Uri selectedImage;

        String filePath = null;
        switch (requestCode) {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    String selectedPath = selectedImage.getPath();
                    if (requestCode == SELECT_FILE) {

                        if (selectedPath != null) {
                            InputStream imageStream = null;
                            try {
                                imageStream = getContentResolver().openInputStream(
                                        selectedImage);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transformamos la URI de la img a inputStream y este a un Bitmap
                            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                            img.setImageResource(R.drawable.logo);
                            // Ponemos nuestro bitmap en un ImageView que tengamos en la vista

                            final ImagenPropia mImg = new ImagenPropia(MainActivity.this);

                            final LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            
                            lp2.width = 450;
                            lp2.height = 450;
                            //lp2.gravity = Gravity.CENTER;
                            mImg.setPosicion(arrayImagen.size());
                            Log.e("pos img",mImg.getPosicion()+"");
                            mImg.setPadding(25, 0, 0, 0);
                            mImg.setImageBitmap(bmp);
                            mImg.setLayoutParams(lp2);

                                mImg.setOnLongClickListener(new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        posicion = mImg.getPosicion();
                                        pos.add(posicion);
                                        arrayImagenBorrar.add(mImg);
                                        mImg.setBackground(getDrawable(R.drawable.image_borde));
                                        return true;
                                    }
                                });

                            mImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Integer posclick =mImg.getPosicion();
                                    pos.remove(posclick);
                                    mImg.setBackground(null);
                                    arrayImagenBorrar.remove(mImg);
                                }
                            });

                            arrayImagen.add(mImg);

                            layout.removeAllViews();
                            for(int i = 0; i<arrayImagen.size();i++){
                                layout.addView(arrayImagen.get(i));
                            }
                            //layout.addView(mImg);
                        }
                    }
                }
                break;
        }
    }


}
