package com.example.news_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_berita extends AppCompatActivity {

    private RadioButton utkAnak, semuaUmur;
    private Spinner spinner;
    private EditText add_judul, add_berita;
    private Button btn_save;
    TextView btn_batal;
    private String filter = "";
    private String kat = "";

    private ProgressDialog progressDialog;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_berita);

        add_berita = findViewById(R.id.add_berita);
        add_judul = findViewById(R.id.add_judul);
        btn_save = findViewById(R.id.btn_save);
        btn_batal = findViewById(R.id.btn_batal);
        utkAnak = findViewById(R.id.tdk_anak);
        semuaUmur =findViewById(R.id.semua_umur);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(News.class.getSimpleName());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Loading
        progressDialog = new ProgressDialog(add_berita.this);
        progressDialog.setMessage("Silahkan tunggu..");
        progressDialog.setCancelable(false);

        //Radio Button
        utkAnak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filter = "child";
            }
        });

        semuaUmur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filter = "all";
            }
        });

        //Spinner
        spinner = findViewById(R.id.list_kategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kat = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (add_judul.getText().length() > 0 && add_berita.getText().length() > 0 && kat != "" && filter != ""){
                    saveBerita(add_judul.getText().toString(), add_berita.getText().toString(), kat, filter, firebaseUser.getDisplayName());
                }else {
                    Toast.makeText(getApplicationContext(), "silahkah isi semua data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void saveBerita(String judul, String berita, String kategori, String filter, String namaUser){
        News newNews =new News();
        newNews.setJudul(judul);
        newNews.setDesc(berita);
        newNews.setKategori(kategori);
        newNews.setUmur(filter);
        newNews.setAuthor(namaUser);

        mDatabaseReference.push().setValue(newNews);
        Toast.makeText(this, "Successfilly insert data!", Toast.LENGTH_SHORT).show();
    }
}