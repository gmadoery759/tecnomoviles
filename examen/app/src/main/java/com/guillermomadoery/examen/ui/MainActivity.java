package com.guillermomadoery.examen.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.guillermomadoery.examen.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Busacamos los recursos de la layout y declaramos los elementos (BIND)
        final EditText etName = findViewById(R.id.etName);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnRegister = (Button) findViewById(R.id.btnRegister);

        // Encontramos las preferencias guardadas bajo el nombre MYPREFS
        preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);

        // False es el valor por defecto, aca si no consigue el valor que esta almacenado que es true devuelve false
        if(preferences.getBoolean("logged",false)){
            Intent displayScreen = new Intent(MainActivity.this, DisplayScreen.class);
            startActivity(displayScreen);
            finish();
        }

        // Obtenemos el usuario y la contraseña y chequeamos que sean iguales a los que estan en sharedpreferences, si lo son vamos a la ventana principal
        // y ponemos logged en true para que no nos vuelva a llegar al login una vez logeados
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etName.getText().toString();
                String password = etPassword.getText().toString();


                String username = preferences.getString("username","DEFAULT");
                String userpassword = preferences.getString("password","DEFAULT");

                if(username.equals(user) && password.equals(userpassword)){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("logged",true);
                    editor.apply();

                    Intent displayScreen = new Intent(MainActivity.this, DisplayScreen.class);
                    startActivity(displayScreen);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this,"El usuario o contraseña es incorrecto",Toast.LENGTH_LONG).show();
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerScreen = new Intent(MainActivity.this, Register.class);
                startActivity(registerScreen);
            }
        });
    }



}

