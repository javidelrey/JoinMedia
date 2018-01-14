package es.fempa.javi.es.joinmedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import es.fempa.javi.es.joinmedia.PersonasBD.PersonasEntry;

/**
 * Created by Miguel on 16/11/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "joinMedia.db";
    public static SQLiteDatabase db;
    public BaseDatos(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PersonasEntry.TABLE_NAME + " ("
                + PersonasEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PersonasEntry.NAME + " TEXT NOT NULL,"
                + PersonasEntry.EMAIL + " TEXT NOT NULL,"
                + PersonasEntry.PASS + " TEXT NOT NULL,"
                + "UNIQUE (" + PersonasEntry.ID + "))");


        ContentValues values = new ContentValues();
        values.put(PersonasEntry.NAME, "Miguel Verdu");
        values.put(PersonasEntry.EMAIL, "miguelverdu1812@gmail.com");
        values.put(PersonasEntry.PASS, "123456");
        sqLiteDatabase.insert(PersonasEntry.TABLE_NAME, null, values);

        values.put(PersonasEntry.NAME, "Javier del Rey");
        values.put(PersonasEntry.EMAIL, "javierdelrey3@gmail.com");
        values.put(PersonasEntry.PASS, "123456");
        sqLiteDatabase.insert(PersonasEntry.TABLE_NAME, null, values);

        values.put(PersonasEntry.NAME, "Cristian Pérez");
        values.put(PersonasEntry.EMAIL, "icristian7@gmail.com");
        values.put(PersonasEntry.PASS, "123456");
        sqLiteDatabase.insert(PersonasEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  ArrayList<PersonasBD> consultar(String query){
        db = getReadableDatabase();
        Log.e("query", query);
        Cursor resultado = db.rawQuery(query, new String[]{});
        ArrayList<PersonasBD> usuarios = new ArrayList<PersonasBD>();

        while(resultado.moveToNext()){
            PersonasBD p = new PersonasBD();

            p.setNombre(resultado.getString(resultado.getColumnIndex(PersonasEntry.NAME)));
            p.setPass(resultado.getString(resultado.getColumnIndex(PersonasEntry.PASS)));
            p.setEmail(resultado.getString(resultado.getColumnIndex(PersonasEntry.EMAIL)));
            usuarios.add(p);
        }

        return usuarios;
    }

    public static boolean insertar(String query, SQLiteDatabase bd){
        String params[] = query.split(",");
        try{

            ContentValues values = new ContentValues();
            values.put(PersonasEntry.NAME, params[0]);
            values.put(PersonasEntry.EMAIL, params[1]);
            values.put(PersonasEntry.PASS, params[2]);
            bd.insert(PersonasEntry.TABLE_NAME, null, values);

            return true;
        }catch(Exception e){
            return false;
        }
    }
}
