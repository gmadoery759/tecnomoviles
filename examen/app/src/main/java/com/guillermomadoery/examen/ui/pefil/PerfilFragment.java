package com.guillermomadoery.examen.ui.pefil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.guillermomadoery.examen.BaseFragment;
import com.guillermomadoery.examen.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class PerfilFragment extends BaseFragment {

    private CircleImageView circleImageView;
    private SharedPreferences preferences;
    private static final int TAKE_PICTURE = 1;
    private EditText etxt_cambiarUsuario,etxt_pass1,etxt_pass2;
    private Button btnGuardar;
    private String passwordNew;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        etxt_cambiarUsuario = root.findViewById(R.id.etxt_cambiar_usuario);
        etxt_pass2 = root.findViewById(R.id.etxt_cambier_contraseña_nueva);
        etxt_pass1 = root.findViewById(R.id.etxt_cambiar_contraseña);
        circleImageView = root.findViewById(R.id.profile_img);
        btnGuardar = root.findViewById(R.id.btn_guardar_perfil);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferences = requireActivity().getSharedPreferences("MYPREFS", MODE_PRIVATE);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = preferences.edit();
                String localUsername = preferences.getString("username",preferences.getString("username","ERROR"));
                String passwordUser = preferences.getString("password","ERROR");
                String passwordOld = etxt_pass1.getText().toString().trim();


                if(passwordUser.equals(passwordOld)){

                    if(etxt_cambiarUsuario.getText().toString().isEmpty()){
                        editor.putString("username",localUsername);
                    }else{
                        editor.putString("username",etxt_cambiarUsuario.getText().toString().trim());
                    }


                    passwordNew = etxt_pass2.getText().toString().trim();
                    editor.putString("password",passwordNew);
                    editor.apply();
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_home);

                }else{
                    Toast.makeText(requireActivity(), "La password anterior no coincide", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Al clicar la imagen abrimos la camara
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openBackCamera();
            }
        });
    }
    private void openBackCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PICTURE);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PICTURE) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            circleImageView.setImageBitmap(imageBitmap);

            //Guardamos el uri de la imagen para luego accederla
            //SharedPreferences.Editor editor = preferences.edit();
            //editor.putString("imageUri",data.getData().getPath());
            //editor.apply();

        }

    }
}