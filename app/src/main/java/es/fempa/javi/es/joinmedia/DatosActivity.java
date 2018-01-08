package es.fempa.javi.es.joinmedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

        Button b=(Button)findViewById(R.id.button3);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });



    }
}
