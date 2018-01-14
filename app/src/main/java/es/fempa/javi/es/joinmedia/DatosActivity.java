package es.fempa.javi.es.joinmedia;

import android.app.NotificationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class DatosActivity extends AppCompatActivity {

    private String nombreText;
    private String emailText;
    private String passText;
    private String confirmPassText;
    private Button guardar, cancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        final EditText nombre=(EditText)findViewById(R.id.editTextNombre);

        final EditText email=(EditText)findViewById(R.id.editTextEmail);

        final EditText pass=(EditText)findViewById(R.id.editTextPass);

        final EditText confimPass=(EditText)findViewById(R.id.editTextConfPass);

        cancelar=(Button)findViewById(R.id.cancelar);
        guardar=(Button)findViewById(R.id.guardar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreText=nombre.getText().toString();
                emailText=email.getText().toString();
                passText=pass.getText().toString();
                confirmPassText=confimPass.getText().toString();
                if(passText.equals(confirmPassText)) {
                    boolean resultado = BaseDatos.insertar(nombreText + "," + emailText + "," + passText, BaseDatos.db);
                    NotificationCompat.Builder mBuilder;
                    NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    int mNotificationId = 001;

                    if (resultado) {
                        mBuilder =
                                new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.drawable.logo)
                                        .setContentTitle("Registro completado!")
                                        .setContentText("Haz login y disfruta de JoinMedia!");
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        Intent intent = new Intent(DatosActivity.this, LoginActivity.class);
                        intent.putExtra("user", emailText);
                        intent.putExtra("pass", passText);
                        startActivity(intent);
                    } else {
                        mBuilder =
                                new NotificationCompat.Builder(getApplicationContext())
                                        .setSmallIcon(R.drawable.logo)
                                        .setContentTitle("Ops, ha fallado el registro")
                                        .setContentText("Intentalo de nuevo");
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    }
                }else{
                    pass.setText("");
                    confimPass.setText("");
                    Snackbar.make(view, "Las contraseñas no son iguales", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }
}
