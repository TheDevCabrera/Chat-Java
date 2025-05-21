package com.cabrera.chat_java.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cabrera.chat_java.Constantes;
import com.cabrera.chat_java.OpcionesLoginActivity;
import com.cabrera.chat_java.R;
import com.cabrera.chat_java.databinding.FragmentPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentPerfil extends Fragment {

    private FragmentPerfilBinding binding;
    private Context mContext;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    public FragmentPerfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(LayoutInflater.from(mContext), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = firebaseAuth.getInstance();

        cargarInformacion();

        binding.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(mContext, OpcionesLoginActivity.class));
                getActivity().finishAffinity();
            }
        });
    }

    private void cargarInformacion() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String nombres = ""+snapshot.child("nombres").getValue();
                        String email = ""+snapshot.child("email").getValue();
                        String proveedor = ""+snapshot.child("proveedor").getValue();
                        String t_registro = ""+snapshot.child("tiempoR").getValue();
                        String imagen = ""+snapshot.child("imagen").getValue();

                        if (t_registro.equals("null")){
                            t_registro = "0";
                        }

                        String fecha = Constantes.formatoFecha(Long.parseLong(t_registro));

                        /*Setear la información a las vistas*/
                        binding.tvNombres.setText(nombres);
                        binding.tvEmail.setText(email);
                        binding.tvProveedor.setText(proveedor);
                        binding.tvTRegistro.setText(fecha);

                        /*setear la imagen*/
                        Glide.with(mContext)
                                .load(imagen)
                                .placeholder(R.drawable.ic_img_perfil)
                                .into(binding.ivPerfil);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}