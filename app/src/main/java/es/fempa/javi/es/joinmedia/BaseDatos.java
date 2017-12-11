package es.fempa.javi.es.joinmedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.fempa.javi.es.joinmedia.PersonasBD.PersonasEntry;

/**
 * Created by Miguel on 16/11/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "joinMedia.db";

    public BaseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PersonasEntry.TABLE_NAME + " ("
                + PersonasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
}
