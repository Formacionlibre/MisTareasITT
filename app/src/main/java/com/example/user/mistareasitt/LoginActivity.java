package com.example.user.mistareasitt;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //quitar la barra
        getSupportActionBar().hide();
    }
    public void crearCuenta(View view){
        Toast toast = Toast.makeText(this,"Funcionalidad no implementada", Toast.LENGTH_LONG);

        toast.show();
    }

   public void login (View view){
       TextInputEditText usuario = (TextInputEditText)findViewById(R.id.cajaUser);
       TextInputEditText pass =  (TextInputEditText)findViewById(R.id.cajaPass);

       if (usuario.getText().toString().equalsIgnoreCase("itt") &&pass.getText().toString().equalsIgnoreCase("123") ){
           Intent intent = new Intent(this, MainActivity.class);
           startActivity(intent);
    }else{
           Toast toast = Toast.makeText(this,"Credenciales invalidos", Toast.LENGTH_LONG);
           toast.show();

}
   }
}
