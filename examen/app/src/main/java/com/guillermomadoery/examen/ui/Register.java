package com.guillermomadoery.examen.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.guillermomadoery.examen.R;

public class Register extends AppCompatActivity {

    private String username;
    private String userPassword;
    private String confirmUserPassword;
    private String userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText userName = findViewById(R.id.etNewName);
        final EditText password =  findViewById(R.id.etNewPassword);
        final EditText email =  findViewById(R.id.etNewEmail);
        final EditText confirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnRegister =  findViewById(R.id.btnNewRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Primero obtengo los datos de los campos
                // Uso trim() para evitar espacios en blanco al final o comienzo del string, esto previene que se ingresen campos vacios
                username  = userName.getText().toString().trim();
                userPassword = password.getText().toString().trim();
                userEmail = email.getText().toString().trim();
                confirmUserPassword = confirmPassword.getText().toString().trim();


                // Verifico que todos los campos sean correctos, sino retorno del onClick y no continuo con el guardado hasta que todos los campos
                // pasen la verificación
                if(userNameIsEmpty()){
                    userName.setError("El usuario no puede ser vacio");
                    return;
                }

                if(!matchinPasswords()){
                    password.setError("Las contraseñas no coinciden");
                    return;
                }

                if(!isEmailValid(userEmail)){
                    email.setError("El email no tiene un formato valido");
                    return;
                }

                // Por ultimo si la verificación pasó, guardo los datos
                saveUser(username,userPassword,userEmail);

            }
        });

    }

    private void saveUser(String userName,String userPassword,String useremail){
        SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username",userName);
        editor.putString("password", userPassword);
        editor.putString("email",useremail);
        editor.apply();
        finish();
    }

    private Boolean userNameIsEmpty(){
        return username.isEmpty();
    }

    private Boolean matchinPasswords(){
        return userPassword.equals(confirmUserPassword);
    }

    private Boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}