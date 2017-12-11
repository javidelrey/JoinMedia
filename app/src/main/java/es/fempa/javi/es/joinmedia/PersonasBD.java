package es.fempa.javi.es.joinmedia;

import android.provider.BaseColumns;

/**
 * Created by Miguel on 11/12/2017.
 */

public class PersonasBD {

    public static abstract class PersonasEntry implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String EMAIL = "email";
        public static final String PASS = "pass";
    }

}
