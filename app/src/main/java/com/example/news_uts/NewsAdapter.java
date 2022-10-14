package com.example.news_uts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> dataItem;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView judul;
        TextView desc;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.txtTitle);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image_list);
        }
    }

    NewsAdapter(ArrayList<News>dataItem) {
        this.dataItem = dataItem;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        final News news = dataItem.get(position);
        TextView judul = holder.judul;
        TextView desc =  holder.desc;
        ImageView image = holder.image;

        judul.setText(dataItem.get(position).getJudul());
        desc.setText(dataItem.get(position).getDescription());
        image.setImageResource(dataItem.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailNews.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", news.getJudul());
                bundle.putString("desc", news.getDescription());
                bundle.putInt("picture", news.getImage());
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

}
