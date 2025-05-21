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

import com.cabrera.chat_java.OpcionesLoginActivity;
import com.cabrera.chat_java.R;
import com.cabrera.chat_java.databinding.FragmentPerfilBinding;
import com.google.firebase.auth.FirebaseAuth;

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

        binding.btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(mContext, OpcionesLoginActivity.class));
                getActivity().finishAffinity();
            }
        });
    }
}