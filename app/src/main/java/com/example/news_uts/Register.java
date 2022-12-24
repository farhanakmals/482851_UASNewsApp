package com.example.news_uts;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {

    private TextView back;
    private EditText regName, regEmail, regPass, regConfPass;
    private Button signup;

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back = findViewById(R.id.back);
        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regPass = findViewById(R.id.reg_pass);
        regConfPass = findViewById(R.id.reg_conf_pass);
        signup = findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();

        // Loading
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Silahkan tunggu..");
        progressDialog.setCancelable(false);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regName.getText().length() > 0 && regEmail.getText().length() > 0
                        && regPass.getText().length() > 0 && regConfPass.getText().length() > 0) {

                    if (regPass.getText().toString().equals(regConfPass.getText().toString())){
                        register(regName.getText().toString(), regEmail.getText().toString() + "@mail.com",
                                regPass.getText().toString(), regConfPass.getText().toString());
                    }else {
                        Toast.makeText(getApplicationContext(), "Password yang anda masukan berbeda", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Lengkapi semua data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void register(String nama, String email, String pass, String confPass){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        // set nama user
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(nama).build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                reload();
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(),"Register gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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