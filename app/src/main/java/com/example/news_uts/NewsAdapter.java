package com.example.news_uts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mlist;
    private Activity activity;
    String filter, kat;

    public NewsAdapter (List<News> mlist, Activity activity, String filter, String kat){
        this.mlist = mlist;
        this.kat = kat;
        this.activity = activity;
        this.filter = filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judul;
        TextView desc;
        TextView author;
        CardView card_hasil;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.txtTitle);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            card_hasil = itemView.findViewById(R.id.card_hasil);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final News news = mlist.get(position);
        TextView judul = holder.judul;
        TextView desc =  holder.desc;
        TextView author = holder.author;

        judul.setText(mlist.get(position).getJudul());
        desc.setText(mlist.get(position).getDesc());
        author.setText("by " + mlist.get(position).getAuthor());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailNews.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", news.getJudul());
                bundle.putString("desc", news.getDesc());
                bundle.putString("author", news.getAuthor());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

}
