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
        int img_photo = bundle.getInt("picture");

        TextView title = findViewById(R.id.title);
        TextView desc = findViewById(R.id.deskripsi);
        ImageView img = findViewById(R.id.image);

       title.setText(txtTitle);
        desc.setText(deskripsi);
        img.setImageResource(img_photo);

    }
}