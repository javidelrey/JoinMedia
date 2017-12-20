package es.fempa.javi.es.joinmedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

public class DatosActivity extends AppCompatActivity {

    private String nombreText;
    private String emailText;
    private String passText;
    private String confirmPassText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        EditText nombre=(EditText)findViewById(R.id.editTextNombre);
        nombreText=nombre.getText().toString();

        EditText email=(EditText)findViewById(R.id.editTextEmail);
        emailText=email.getText().toString();

        EditText pass=(EditText)findViewById(R.id.editTextPass);
        passText=pass.getText().toString();

        EditText confimPass=(EditText)findViewById(R.id.editTextConfPass);
        confirmPassText=confimPass.getText().toString();

        Log.e("datos",nombreText);
        Log.e("datos",emailText);
        Log.e("datos",passText);
        Log.e("datos",confirmPassText);

    }
}
