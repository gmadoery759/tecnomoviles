package com.guillermomadoery.examen.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.guillermomadoery.examen.BaseFragment;
import com.guillermomadoery.examen.ui.MainActivity;
import com.guillermomadoery.examen.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends BaseFragment {

    private TextView userName;
    private SharedPreferences preferences;
    private FloatingActionButton fab;
    private Button btnConexion;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        userName = root.findViewById(R.id.txt_username);
        fab = requireActivity().findViewById(R.id.fab);
        btnConexion = root.findViewById(R.id.btn_check_internet);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Mensaje de bienvenida
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String username = preferences.getString("username","DEFAULT");
        userName.setText(username);

        // Enviar email a soporte
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { "admin@tecnologiasmobiles.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Consulta");
                intent.putExtra(Intent.EXTRA_TEXT, "Escribe aquí el email.");
                startActivity(Intent.createChooser(intent, "Enviar E-mail"));
            }
        });

        btnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkConnected()){
                    Toast.makeText(requireActivity(), "Hay conexion a internet", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireActivity(), "No hay conexion a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    // Usar si se quiere hace un ping a googel para chequear internet.
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            try {
                URL url = new URL("http://www.google.com/");
                HttpURLConnection urlc = (HttpURLConnection)url.openConnection();
                urlc.setRequestProperty("User-Agent", "test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1000); // mTimeout is in seconds
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                Log.i("warning", "Error checking internet connection", e);
                return false;
            }
        }

        return false;

    }
}