package cyto.iridium.mselamat.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import cyto.iridium.mselamat.NewsPageActivity;
import cyto.iridium.mselamat.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private ArrayList<Article> articleArrayList;
    private HomeFragment context;

    public NewsAdapter(ArrayList<Article> articleArrayList, HomeFragment context) {
        this.articleArrayList = articleArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        Article article = articleArrayList.get(position);
        holder.title.setText(article.getTitle());
        holder.subtitle.setText(article.getDescription());
        holder.datetime.setText(article.getDate());

        if (article.getUrlToImage() == null) {
            Picasso.get().load("https://images.unsplash.com/photo-1576091160550-2173dba999ef?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80").into(holder.newsIV);
        } else {
            Picasso.get().load(article.getUrlToImage()).into(holder.newsIV);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context.getActivity(), NewsPageActivity.class);
            i.putExtra("title", article.getTitle());
            i.putExtra("desc", article.getDescription());
            i.putExtra("url", article.getUrl());
            i.putExtra("imageurl", article.getUrlToImage());
            i.putExtra("content", article.getContent());
            i.putExtra("datetime", article.getDate());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle, datetime;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsIV = itemView.findViewById(R.id.IVNews);
            title = itemView.findViewById(R.id.NewsHeading);
            datetime = itemView.findViewById(R.id.NewsDateTime);
            subtitle = itemView.findViewById(R.id.NewsSubHeading);
        }
    }
}
