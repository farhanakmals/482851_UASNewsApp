package com.example.news_uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailNews extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        Bundle bundle = getIntent().getExtras();
        String txtTitle = bundle.getString("title");
        String deskripsi = bundle.getString("desc");
        String author = bundle.getString("author");


        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.deskripsi);
        TextView penulis = findViewById(R.id.by_author);

        TextView bookmark = findViewById(R.id.bookmark);

        title.setText(txtTitle);
        desc.setText(deskripsi);
        penulis.setText("by " + author);

    }
}