package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPersonFragment  extends Fragment implements DetailPersonContract.View, ClickListener {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    private Window window;

    @BindView(R.id.collapse_toolbar_person) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.picture_person) ImageView picture;
    @BindView(R.id.birthdate) TextView birtdate;
    @BindView(R.id.birthplace) TextView birtplace;
    @BindView(R.id.biography) TextView biograhy;
    @BindView(R.id.recycler_other) RecyclerView recyclerViewOther;
    @BindView(R.id.gallery) ImageView gallery;
    @BindView(R.id.bgheader_person) ImageView bgheaderPerson;

    private ArrayList<OtherMoviesFromPerson> otherMovies;
    private OtherMoviesAdapter adapter;

    private MovieComplete movie;
    private Person person;

    private DetailPersonContract.Presenter presenter;

    public DetailPersonFragment() {
    }

    public static DetailPersonFragment newInstance() {
        return new DetailPersonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        person = getActivity().getIntent().getParcelableExtra("person");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_person, container, false);
        ButterKnife.bind(this, root);

        initToolbar();
        initLayoutComponents();
        initPalettePersonalization();
        initRecyclerView();

        return root;
    }

    private void initToolbar() {
        collapsingToolbarLayout.setTitle(person.getName());
        collapsingToolbarLayout.setExpandedTitleColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
    }

    private void initLayoutComponents() {
        Picasso.get().load(BASE_IMAGE_URL + person.getProfile_path())
                .transform(new BlurTransformation(getActivity().getApplicationContext()))
                .into(bgheaderPerson);
        Picasso.get().load(BASE_IMAGE_URL + person.getProfile_path()).fit().into(picture);
        birtdate.setText(person.getBirthday());
        birtplace.setText(person.getPlace_of_birth());
        biograhy.setText(person.getBiography());
    }

    private void initPalettePersonalization() {
        window = getActivity().getWindow();

        try {
            if(person.getProfile_path() != "") {
                InputStream imageStream = new URL(BASE_IMAGE_URL + person.getProfile_path()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {

                        if (palette.getVibrantSwatch() != null && palette.getVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color-(color/200));
                        } else if (palette.getDarkVibrantSwatch() != null && palette.getDarkVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color-(color/200));
                        } else if (palette.getLightVibrantSwatch() != null && palette.getLightVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color-(color/200));
                        } else {
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }
                });
            }
        } catch (Exception ex) {
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
            Log.e("Error on Palette", ""+ex);
        }
    }

    private void initRecyclerView(){
        //Init du recyclerView other
        otherMovies = new ArrayList<OtherMoviesFromPerson>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOther.setLayoutManager(horizontalLayoutManager);
        adapter = new OtherMoviesAdapter(getActivity().getApplicationContext(), otherMovies);
        adapter.setClickListener(this);
        recyclerViewOther.setAdapter(adapter);
        presenter.loadOtherMovies(person.getId());
    }

    @OnClick(R.id.picture_person)
    public void fullScreenPoster() {
        Intent i = new Intent(getActivity(), FullScreenImageViewActivity.class);
        i.putExtra("img", person.getProfile_path());
        startActivity(i);
    }

    @OnClick(R.id.gallery)
    public void openGallery() {
        Intent i = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
        i.putExtra("from", "person");
        i.putExtra("person", person);
        startActivity(i);
    }

    @Override
    public void setPresenter(DetailPersonContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setOtherMovies(@NonNull ArrayList<OtherMoviesFromPerson> movies) {
        this.otherMovies = movies;
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setMovieComplete(@NonNull MovieComplete movie) {
        this.movie = movie;
    }

    @Override
    public void showDetailMovie(@NonNull MovieComplete movie) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("movie", movie);
        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        presenter.loadMovieComplete(Integer.parseInt(otherMovies.get(position).getId()));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }
}
