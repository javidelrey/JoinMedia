package es.fempa.javi.es.joinmedia;

import android.provider.BaseColumns;

/**
 * Created by Miguel on 11/12/2017.
 */

public class PersonasBD {
    private String nombre;
    private String email;
    private String pass;
    private int id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static abstract class PersonasEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASS = "pass";
    }



}
