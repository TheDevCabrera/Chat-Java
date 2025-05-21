package com.cabrera.chat_java;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cabrera.chat_java.databinding.ActivityRegistroEmailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegistroEmailActivity extends AppCompatActivity {

    private ActivityRegistroEmailBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroEmailBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarInformacion();
            }
        });
    }



    private String nombres , email , password , r_password;
    private void validarInformacion() {
        nombres = binding.etNombres.getText().toString().trim();
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();
        r_password = binding.etRPassword.getText().toString().trim();

        if (nombres.isEmpty()){
            binding.etNombres.setError("Ingrese nombres");
            binding.etNombres.requestFocus();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.setError("Correo inválido");
            binding.etEmail.requestFocus();
        }
        else if (email.isEmpty()){
            binding.etEmail.setError("Ingrese correo");
            binding.etEmail.requestFocus();
        }
        else if (password.isEmpty()){
            binding.etPassword.setError("Ingrese contraseña");
            binding.etPassword.requestFocus();
        }
        else if (r_password.isEmpty()){
            binding.etRPassword.setError("Repita la contraseña");
            binding.etRPassword.requestFocus();
        }
        else if (!password.equals(r_password)){
            binding.etRPassword.setError("Las contraseñas no coinciden");
            binding.etRPassword.requestFocus();
        }else {
            registrarUsuario();
        }
    }

    private void registrarUsuario() {
        progressDialog.setMessage("Creando cuenta");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        actualizarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                RegistroEmailActivity.this,
                                "Falló el registro debido a : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void actualizarInformacion() {
        progressDialog.setMessage("Guardando información");

        String uidU = firebaseAuth.getUid();
        String nombresU = nombres;
        String emailU = firebaseAuth.getCurrentUser().getEmail();
        long tiempoR = Constantes.obneterTiempoD();

        HashMap<String, Object> datosUsuario = new HashMap<>();

        datosUsuario.put("uid", uidU);
        datosUsuario.put("nombres", nombresU);
        datosUsuario.put("email", emailU);
        datosUsuario.put("tiempoR", tiempoR);
        datosUsuario.put("proveedor", "Email");
        datosUsuario.put("estado", "Online");
        datosUsuario.put("imagen", "");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuarios");
        reference.child(uidU).setValue(datosUsuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                startActivity(new Intent(RegistroEmailActivity.this, MainActivity.class));
                finishAffinity();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(
                                RegistroEmailActivity.this,
                                "Falló el registro debido a : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}