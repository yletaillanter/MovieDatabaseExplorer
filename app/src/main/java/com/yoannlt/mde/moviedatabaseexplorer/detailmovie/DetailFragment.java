package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.CastingRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailFragment extends Fragment implements DetailContract.View, ClickListener {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    private final String HORIZONTAL_RECYCLER_ADAPTER = "HorizontalRecyclerAdapter";
    private final String CASTING_RECYCLER_ADAPTER = "CastingRecyclerAdapter";

    private MovieComplete currentMovie;
    private Person person = new Person();
    MovieComplete movie;

    // LIST FILMS SIMILAIRES
    private ArrayList<Movie> similarMovies;
    private HorizontalRecyclerAdapter adapter;

    // LIST CASTING
    private ArrayList<CastPerson> castPersons;
    private CastingRecyclerAdapter adapterCasting;

    //@BindView(R.id.collapse_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_similar)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_casting) RecyclerView recyclerViewCasting;
    @BindView(R.id.overview)
    TextView overview;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.language) TextView language;
    //@BindView(R.id.bgheader)
    ImageView back;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.genre_one) TextView genre_one;
    @BindView(R.id.genre_two) TextView genre_two;
    @BindView(R.id.genre_three) TextView genre_three;
    @BindView(R.id.releasedate) TextView release_date;
    @BindView(R.id.original_title) TextView original_title;
    @BindView(R.id.original_language) TextView original_language;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.budget) TextView budget;
    @BindView(R.id.revenue) TextView revenue;
    @BindView(R.id.production_companies) TextView production_companies;
    @BindView(R.id.gallery) ImageView galleryButton;

    private DetailContract.Presenter presenter;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the movie
        currentMovie = getActivity().getIntent().getParcelableExtra("movie");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);

        //collapsingToolbarLayout.setTitle(currentMovie.getTitle());

        overview.setText(currentMovie.getOverview());
        rate.setText("" + currentMovie.getVote_average());
        language.setText(currentMovie.getOriginal_language());
        release_date.setText(currentMovie.getRelease_date());
        original_title.setText(currentMovie.getOriginal_title());
        original_language.setText(currentMovie.getOriginal_language());
        status.setText(currentMovie.getStatus());
        budget.setText(""+currentMovie.getBudget());
        revenue.setText(""+currentMovie.getRevenue());
        if(currentMovie.getProduction_companies() != null && currentMovie.getProduction_companies().length > 0) {
            production_companies.setText(currentMovie.getProduction_companies()[0].getName());
        }

        //Picasso.with(getActivity().getApplicationContext()).load(BASE_IMAGE_URL + currentMovie.getBackdrop_path()).into(back);
        Picasso.with(getActivity().getApplicationContext()).load(BASE_IMAGE_URL + currentMovie.getPoster_path()).into(poster);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startFullScreenActivity();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "gallery", Toast.LENGTH_LONG).show();
                //Intent i = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
                //i.putExtra("from", "movie");
                //i.putExtra("movie", currentMovie);
                //startActivity(i);
            }
        });

        try {
            if(currentMovie.getBackdrop_path() != "") {
                InputStream imageStream = new URL(BASE_IMAGE_URL + currentMovie.getBackdrop_path()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette.getVibrantSwatch() != null) {
                            //collapsingToolbarLayout.setContentScrimColor(palette.getVibrantSwatch().getRgb());
                        } else if (palette.getDarkVibrantSwatch() != null) {
                            //collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantSwatch().getRgb());
                        } else if (palette.getLightVibrantSwatch() != null) {
                            //collapsingToolbarLayout.setContentScrimColor(palette.getLightVibrantSwatch().getRgb());
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Error on Palette", ""+ex);
        }

        //Init du recyclerView similar
        similarMovies = new ArrayList<Movie>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new HorizontalRecyclerAdapter(getActivity().getApplicationContext(), similarMovies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Init Similar Movie
        //loadSimilar(currentMovie.getId());
        presenter.loadSimilar(currentMovie.getId());

        // Init recyclar casting
        castPersons = new ArrayList<CastPerson>();
        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCasting.setLayoutManager(layoutManagerCast);
        adapterCasting = new CastingRecyclerAdapter(getActivity().getApplicationContext(), castPersons);
        adapterCasting.setClickListener(this);
        recyclerViewCasting.setAdapter(adapterCasting);

        // Init credits call
        //loadCredits(currentMovie.getId());
        presenter.loadCredits(currentMovie.getId());

        return root;
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        if (CASTING_RECYCLER_ADAPTER.equals(recycler)) {
            //loadPerson(castPersons.get(position).getId());
        } else if(HORIZONTAL_RECYCLER_ADAPTER.equals(recycler)) {
            //loadCompleteMovie(similarMovies.get(position).getId());
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void setSimilarMovies(@NonNull ArrayList<Movie> movies) {
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setCasting(@NonNull ArrayList<CastPerson> cast) {
        adapterCasting.replace(cast);
        adapterCasting.notifyDataSetChanged();
    }

    @Override
    public void setPerson(@NonNull Person person) {
        this.person = person;
    }

    @Override
    public void setCompleteMovie(@NonNull MovieComplete movie) {
        this.currentMovie = movie;
    }
}
