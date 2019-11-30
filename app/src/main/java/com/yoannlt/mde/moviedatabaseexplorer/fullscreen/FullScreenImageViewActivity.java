package com.yoannlt.mde.moviedatabaseexplorer.fullscreen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullScreenImageViewActivity extends AppCompatActivity {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    @BindView(R.id.img) ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_view);
        ButterKnife.bind(this);

        String imgPath = getIntent().getStringExtra("img");
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Picasso.get().load(BASE_IMAGE_URL + imgPath).into(image);
    }
}
