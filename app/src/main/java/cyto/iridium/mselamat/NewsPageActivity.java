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

        Picasso.get().load(imageurl).into(newsIV);
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