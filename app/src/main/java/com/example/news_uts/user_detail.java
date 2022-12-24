package com.example.news_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class user_detail extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText tgl_lahir;
    private String spinnerText;
    private Button btn_cariberita, btn_logout;
    private Spinner spinner;
    private TextView greet;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //greet
        greet = findViewById(R.id.greet);
        greet.setText("Hello " + firebaseUser.getDisplayName());

        //logout
        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent signout = new Intent(getApplicationContext(), Login.class);
                startActivity(signout);
                finish();
            }
        });

        //Button Cari Berita
        btn_cariberita = findViewById(R.id.btn_cariBerita);
        btn_cariberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(user_detail.this, MainActivity.class);
                String kategori = String.valueOf(spinnerText);
                String tanggal_lahir = String.valueOf(tgl_lahir.getText());
                intent.putExtra("kategori", kategori);
                intent.putExtra("tanggal_lahir", tanggal_lahir);
                startActivity(intent);
            }
        });


        //Tanggal lahir
        tgl_lahir = findViewById(R.id.tgl_lahir);
        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataPicker();
            }
        });

        //Spinner
        spinner = findViewById(R.id.label_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);


        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

    }

    public void processDatePicker(int i2, int i1, int i) {
        String day_string = Integer.toString(i2);
        String month_string = Integer.toString(i1+1);
        String year_string = Integer.toString(i);
        String dateMessage = day_string + "-" + month_string + "-" + year_string;
        Toast.makeText(this, dateMessage, Toast.LENGTH_SHORT).show();
        tgl_lahir.setText(dateMessage);
    }

    public void showDataPicker(){
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), "date-picker");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerText = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, spinnerText, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}