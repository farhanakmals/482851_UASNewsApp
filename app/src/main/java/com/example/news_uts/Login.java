package com.example.news_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText email, password;
    private TextView register;
    private Button btn_login;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        btn_login = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        // Loading
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Silahkan tunggu..");
        progressDialog.setCancelable(false);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String username = email.getText().toString() + "@mail.com";
                if (email.getText().length() > 0 && password.getText().length() > 0){
                    login(username, password.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "Isi email dan password anda", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regis = new Intent(view.getContext(),Register.class);
                startActivity(regis);
            }
        });
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // cek apakah berhasil? apakah berisi result?
                if (task.isSuccessful() && task.getResult() != null){
                    if (task.getResult().getUser() != null){
                        reload();
                    }else {
                        Toast.makeText(getApplicationContext(),"Login gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Login gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reload(){
        startActivity(new Intent(getApplicationContext(), user_detail.class));
    }

    // jika sudah login
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }
}