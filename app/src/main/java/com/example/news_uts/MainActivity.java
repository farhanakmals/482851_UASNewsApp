package com.example.news_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ArrayList<News> data;
    String kat, tanggal_lahir, key, filterUmur;
    FloatingActionButton tambah;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get data from user detail
        Intent intent = getIntent();
        kat = intent.getStringExtra("kategori");
        tanggal_lahir = intent.getStringExtra("tanggal_lahir");

        //recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        //Button add berita
        tambah = findViewById(R.id.add_berita);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNews = new Intent(view.getContext(), add_berita.class);
                startActivity(addNews);
            }
        });

        //menghitung umur
        String TanggalLahirUser[] = tanggal_lahir.split("-");
        int umurUser = 2022 - Integer.parseInt(TanggalLahirUser[2]);
        if (umurUser >= 18 ) {
            filterUmur = "all";
        }else {
            filterUmur = "kids";
        }

        recyclerViewLayoutManager = new LinearLayoutManager(this);
        //newsAdapter = new NewsAdapter(data);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(newsAdapter);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        tampildata();

    }


    private void tampildata() {
        mDatabaseReference.child("News").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                data = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()){
                    News dataBerita = item.getValue(News.class);

                    // filter kategori
                    if (dataBerita.getKategori().equals(kat)) {
                        if (filterUmur.equals("all")){
                            // filter umur
                            dataBerita.setKey(item.getKey());
                            data.add(dataBerita);
                        }else {
                            if (dataBerita.getUmur().equals("kids")){
                                dataBerita.setKey(item.getKey());
                                data.add(dataBerita);
                            }
                        }
                    }

                }

                newsAdapter = new NewsAdapter(data, MainActivity.this, filterUmur, kat);
                recyclerView.setAdapter(newsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}