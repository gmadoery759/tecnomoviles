package com.guillermomadoery.examen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.guillermomadoery.examen.ui.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public abstract class BaseFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        final DrawerLayout drawer = requireActivity().findViewById(R.id.drawer_layout);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_home);
                        break;
                    case R.id.nav_perfil:
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_perfil);
                        break;

                    case R.id.nav_logout:
                        SharedPreferences preferences = requireActivity().getSharedPreferences("MYPREFS", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("logged", false);
                        editor.apply();
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        requireActivity().finish();
                        break;

                    case R.id.nav_mapa:
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_mapa);
                        break;

                    case R.id.nav_config:
                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_config);
                        break;
                }

                drawer.closeDrawers();
                return false;
            }
        });
    }
}
