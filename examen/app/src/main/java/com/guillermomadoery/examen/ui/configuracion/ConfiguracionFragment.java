package com.guillermomadoery.examen.ui.configuracion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.guillermomadoery.examen.BaseFragment;
import com.guillermomadoery.examen.ui.MainActivity;
import com.guillermomadoery.examen.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfiguracionFragment extends BaseFragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}