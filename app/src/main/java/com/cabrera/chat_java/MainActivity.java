package com.cabrera.chat_java;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.cabrera.chat_java.Fragmentos.FragmentChats;
import com.cabrera.chat_java.Fragmentos.FragmentPerfil;
import com.cabrera.chat_java.Fragmentos.FragmentUsuarios;
import com.cabrera.chat_java.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        verFragmentoPerfil();

        binding.bottomNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item_perfil){
                    //Visualizar el fragmento perfil
                    verFragmentoPerfil();
                    return true;

                } else if (itemId == R.id.item_usuarios) {
                    //visualizar el fragmento usuarios
                    verFragmentoUsuarios();
                    return true;
                    
                } else if (itemId == R.id.item_chats) {
                    //visualizar el fragmento chats
                    verFragmentosChats();
                    return true;
                }
                else {
                    return false;
                }

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void verFragmentoPerfil() {
        binding.tvTitulo.setText("Perfil");

        FragmentPerfil fragment = new FragmentPerfil();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentosFL.getId(), fragment, "FragmentPerfil");
        fragmentTransaction.commit();
    }
    private void verFragmentoUsuarios(){
            binding.tvTitulo.setText("Usuarios");

            FragmentUsuarios fragment = new FragmentUsuarios();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(binding.fragmentosFL.getId(), fragment, "FragmentUsuarios");
            fragmentTransaction.commit();
    }

    private void verFragmentosChats(){
        binding.tvTitulo.setText("Chats");

        FragmentChats fragment = new FragmentChats();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentosFL.getId(), fragment, "FragmentChats");
        fragmentTransaction.commit();
    }
}