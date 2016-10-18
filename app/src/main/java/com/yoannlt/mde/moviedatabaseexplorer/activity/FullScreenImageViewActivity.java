package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

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

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        Picasso.with(getApplicationContext()).load(BASE_IMAGE_URL + imgPath).into(image);
    }
}
