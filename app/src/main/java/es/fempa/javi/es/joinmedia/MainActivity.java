package es.fempa.javi.es.joinmedia;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MediaPlayerControl {
    TextView tv ;
    GridLayout layout;
    private int posicion;
    private ArrayList<Integer> pos = new ArrayList<Integer>();
    private ArrayList<ImagenPropia> arrayImagen = new ArrayList<ImagenPropia>();
    private ArrayList<ImagenPropia> arrayImagenBorrar = new ArrayList<ImagenPropia>();
    private static final int SELECT_FILE = 1;
    int cont=0;
    private ArrayList<Song> songList;
    private ListView songView;
    private ImageView img;
    private Button procesar;
    public static String NOMBRE_FICHERO = "imagenes.dat";
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences= getSharedPreferences("Preferencias", MODE_PRIVATE);
        String imagen= preferences.getString("Imagen", null);
        img = new ImagenPropia(getApplicationContext());
        songView = (ListView)findViewById(R.id.song_list);
        layout = (GridLayout) findViewById(R.id.gridLayout);
        songList = new ArrayList<Song>();
        procesar = findViewById(R.id.procesar);
        //getSongList();
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        //file = new File(NOMBRE_FICHERO);
        file = new File(getApplicationContext().getFilesDir(), NOMBRE_FICHERO);

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        procesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayImagen.size()==0){
                    Snackbar.make(view, "no has seleccionado ninguna imagen", Snackbar.LENGTH_SHORT).show();
                }else{
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

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
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

                            FileOutputStream foStream;
                            ObjectInputStream ois = null;
                            FileInputStream fis = null;
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            Bitmap ip = null;/*
                            try {
                                foStream = new FileOutputStream(file);
                                ObjectOutputStream oos = new ObjectOutputStream(out);
                                ImagenPropia ipAux = new ImagenPropia(getApplicationContext());
                                ipAux.setPosicion(1);
                                oos.writeObject(ipAux);
                                oos.close();
                                //foStream = openFileOutput("prueba_int.ddr", Context.MODE_APPEND);
                                ipAux = null;
                                foStream.write(out.toByteArray());
                                foStream.close();

                                fis = new FileInputStream(file);
                                ois = new ObjectInputStream(fis);

                                ipAux = (ImagenPropia) ois.readObject();
                                Toast.makeText(getApplicationContext(), ""+ipAux.getPosicion(),Toast.LENGTH_LONG).show();
                            } catch (Exception e) {

                                Toast.makeText(getApplicationContext(), e.getMessage()+"",Toast.LENGTH_LONG).show();
                            } finally {
                            }*/

                            FileOutputStream outputStream;
                            try {
                                outputStream = openFileOutput(NOMBRE_FICHERO, Context.MODE_PRIVATE);
                                //outputStream.write("hola".getBytes());
                                ObjectOutputStream oos = new ObjectOutputStream(out);
                                oos.writeObject(img);
                                oos.close();
                                outputStream.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                            final ImagenPropia mImg = new ImagenPropia(MainActivity.this);

                            final LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            lp2.width = 350;
                            lp2.height = 350;
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

                            //arrayImagen.add(mImg);
                            ImagenPropia aux = new ImagenPropia(getApplicationContext());
                            aux.setImageBitmap(ip);
                            arrayImagen.add(aux);
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

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int i) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
