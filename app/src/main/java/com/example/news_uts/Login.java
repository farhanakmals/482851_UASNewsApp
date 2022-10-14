package com.example.news_uts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText username, password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usName = username.getText().toString();
                String pwd = password.getText().toString();

                if(usName.equals("pakjoko") && pwd.equals("yangpentingcuan")){
                    Intent inten = new Intent(view.getContext(),user_detail.class);
                    startActivity(inten);
                } else {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Login.this);
                    alertBuilder.setTitle("Username atau password salah!");
                    alertBuilder.setMessage("Silahkan masukan username dan password yang benar!");

                    alertBuilder.setNeutralButton("Ok", (dialog, which) ->{
                        Toast.makeText(getApplicationContext(), "Masukan username dan password anda kembali", Toast.LENGTH_LONG).show();

                    });

                    alertBuilder.show();

                    username.setText("");
                    password.setText("");
                }
            }
        });
    }
}