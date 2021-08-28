package cyto.iridium.mselamat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cyto.iridium.mselamat.ui.home.HomeFragment;

public class NewsPageActivity extends AppCompatActivity {

    String title, desc, url, imageurl, content, datetime;
    private TextView titleTV, descTV, contentTV, datetimeTV;
    private ImageView newsIV;
    private Button readnews, backtoallnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        url = getIntent().getStringExtra("url");
        imageurl = getIntent().getStringExtra("imageurl");
        content = getIntent().getStringExtra("content");
        datetime = getIntent().getStringExtra("datetime");

        newsIV = findViewById(R.id.indIVNews);
        titleTV = findViewById(R.id.indNewsTitle);
        descTV = findViewById(R.id.indNewsDesc);
        datetimeTV = findViewById(R.id.indNewsDate);
        contentTV = findViewById(R.id.indNewsContent);

        if (imageurl == null) {
            Picasso.get().load("https://images.unsplash.com/photo-1576091160550-2173dba999ef?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80").into(newsIV);
        } else {
            Picasso.get().load(imageurl).into(newsIV);
        }

        titleTV.setText(title);
        datetimeTV.setText(datetime);
        descTV.setText(desc);
        contentTV.setText(content);

        readnews = findViewById(R.id.btnReadNews);
        readnews.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        backtoallnews = findViewById(R.id.btnBack);
        backtoallnews.setOnClickListener(v -> finish());
    }
}