package com.example.news_uts;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_berita extends AppCompatActivity {

    private RadioButton utkAnak, semuaUmur;
    private Spinner spinner;
    private EditText edit_judulBerita, edit_isiBerita;
    private Button btn_saveEdit;
    private TextView btn_batalEdit;
    private DatabaseReference mDatabaseReference;
    private String filteredit, kat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_berita);

        edit_isiBerita = findViewById(R.id.edit_isiBerita);
        edit_judulBerita = findViewById(R.id.edit_judulBerita);
        btn_saveEdit = findViewById(R.id.btn_saveEdit);
        btn_batalEdit = findViewById(R.id.btn_batalEdit);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(News.class.getSimpleName());

        //Radio Button
        utkAnak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filteredit = "kids";
            }
        });

        semuaUmur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filteredit = "all";
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

        btn_saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_judulBerita.getText().length() > 0 && edit_isiBerita.getText().length() > 0 && kat != "" && filteredit != ""){
                    saveBerita(edit_judulBerita.getText().toString(), edit_isiBerita.getText().toString(), kat, filteredit);
                }else {
                    Toast.makeText(getApplicationContext(), "silahkah isi semua data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //btn batal
        btn_batalEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveBerita(String judul, String berita, String kategori, String filter){
        News newNews =new News();
        newNews.setJudul(judul);
        newNews.setDesc(berita);
        newNews.setKategori(kategori);
        newNews.setUmur(filter);

        mDatabaseReference.push().setValue(newNews);
        Toast.makeText(this, "Successfilly insert data!", Toast.LENGTH_SHORT).show();
        finish();
    }
}