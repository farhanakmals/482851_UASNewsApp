package com.example.news_uts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ArrayList<News> data;
    String kat, tanggal_lahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        kat = intent.getStringExtra("kategori");
        tanggal_lahir = intent.getStringExtra("tanggal_lahir");

        //menghitung umur
        String TanggalLahirUser[] = tanggal_lahir.split("-");
        int umurUser = 2022 - Integer.parseInt(TanggalLahirUser[2]);

        //kategori berita
        data = new ArrayList<>();
        for(int i = 0; i < NewsData.judul.length; i++){
            Integer targetUmurBerita = Integer.valueOf(NewsData.umur[i]);
            if(targetUmurBerita <= umurUser) {
                if(NewsData.kategori[i].equals(kat)){
                    data.add(new News(
                            NewsData.judul[i],
                            NewsData.description[i],
                            NewsData.imagelist[i],
                            NewsData.umur[i],
                            NewsData.kategori[i]));
                }
            }
        }

        newsAdapter = new NewsAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

}