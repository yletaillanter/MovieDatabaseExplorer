package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.CastingRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailperson.DetailPersonActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.popular.PopularFragment;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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

    @Nullable @BindView(R.id.MyToolbar) Toolbar toolbar;
    @Nullable @BindView(R.id.collapse_toolbar) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.recycler_similar) RecyclerView recyclerView;
    @BindView(R.id.recycler_casting) RecyclerView recyclerViewCasting;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.language) TextView language;
    @Nullable @BindView(R.id.bgheader) ImageView back;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.releasedate) TextView release_date;
    @BindView(R.id.original_title) TextView original_title;
    @BindView(R.id.original_language) TextView original_language;
    @BindView(R.id.status) TextView status;
    @BindView(R.id.budget) TextView budget;

    @BindView(R.id.revenue_label) TextView revenueLabel;
    @BindView(R.id.revenue) TextView revenue;
    @BindView(R.id.production_companies_label) TextView productionCompaniesLabel;
    @BindView(R.id.production_companies) TextView production_companies;

    //Buttons
    @BindView(R.id.gallery) ImageView galleryButton;
    @BindView(R.id.video) ImageView videoButton;
    @BindView(R.id.http) ImageView httpButton;
    @BindView(R.id.favorite) FloatingActionButton favorite;

    private Window window;
    private DetailContract.Presenter presenter;
    private Realm realm;

    @BindBool(R.bool.is_720) boolean is_720;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
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

        if(!is_720) {
            Log.d("ici", "je passe ici");
            currentMovie = getActivity().getIntent().getParcelableExtra("movie");
        } else {
            currentMovie = presenter.getMovieFromActivityCallback();
        }

        if(!is_720) {
            initToolbar();
            initPalettePersonalization();
        }
        initLayoutComponents();
        initRecyclerView();

        return root;
    }

    private void initToolbar() {
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setTitle(currentMovie.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
    }

    private void initLayoutComponents() {
        overview.setText(currentMovie.getOverview());
        rate.setText("" + currentMovie.getVote_average());
        language.setText(currentMovie.getOriginal_language());
        release_date.setText(currentMovie.getRelease_date());
        original_title.setText(currentMovie.getOriginal_title());
        original_language.setText(currentMovie.getOriginal_language());
        status.setText(currentMovie.getStatus());
        budget.setText("" + currentMovie.getBudget());
        if (currentMovie.getRevenue() == 0) {
            revenueLabel.setVisibility(View.GONE);
        } else {
            revenue.setText("" + currentMovie.getRevenue());
        }
        if (currentMovie.getProduction_companies() != null && currentMovie.getProduction_companies().length > 0) {
            production_companies.setText(currentMovie.getProduction_companies()[0].getName());
        } else {
            productionCompaniesLabel.setVisibility(View.GONE);
        }

        if(!is_720)
            Picasso.with(getActivity().getApplicationContext()).load(BASE_IMAGE_URL + currentMovie.getBackdrop_path()).into(back);
        Picasso.with(getActivity().getApplicationContext()).load(BASE_IMAGE_URL + currentMovie.getPoster_path()).into(poster);

        if (checkIfExists(currentMovie)) {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_yellow));
        } else {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star));
        }
    }

    @OnClick(R.id.poster)
    public void fullScreenPoster() {
        Intent i = new Intent(getActivity(), FullScreenImageViewActivity.class);
        i.putExtra("img", currentMovie.getPoster_path());
        startActivity(i);
    }

    @OnClick(R.id.gallery)
    public void openGallery() {
        Intent i = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
        i.putExtra("from", "movie");
        i.putExtra("movie", currentMovie);
        startActivity(i);
    }

    @OnClick(R.id.http)
    public void launchImdb() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://www.imdb.com/title/" + currentMovie.getImdb_id()));
        startActivity(i);
    }

    @OnClick(R.id.favorite)
    public void setFavorite() {
        realm.beginTransaction();
        if (checkIfExists(currentMovie)) {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star));
            RealmResults<Movie> rows = realm.where(Movie.class).equalTo("id", currentMovie.getId()).findAll();
            rows.deleteFromRealm(0);
        } else {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_yellow));
            realm.insert(ActivityUtils.movieCompleteToMovie(currentMovie));
        }
        realm.commitTransaction();
    }

    private void initPalettePersonalization(){
        try {
            window = getActivity().getWindow();
            if (currentMovie.getBackdrop_path() != "") {
                InputStream imageStream = new URL(BASE_IMAGE_URL + currentMovie.getBackdrop_path()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {

                        if (palette.getVibrantSwatch() != null && palette.getVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color - (color / 200));
                        } else if (palette.getDarkVibrantSwatch() != null && palette.getDarkVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color - (color / 200));
                        } else if (palette.getLightVibrantSwatch() != null && palette.getLightVibrantSwatch().getRgb() == 0) {
                            int color = palette.getVibrantSwatch().getRgb();
                            collapsingToolbarLayout.setContentScrimColor(color);
                            window.setStatusBarColor(color - (color / 200));
                        } else {
                            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }
                });
            }
        } catch (Exception ex) {
            collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
            Log.e("Error on Palette", "" + ex);
        }
    }

    private void initRecyclerView(){
        //Init du recyclerView similar
        similarMovies = new ArrayList<Movie>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new HorizontalRecyclerAdapter(getActivity().getApplicationContext(), similarMovies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Init Similar Movie
        presenter.loadSimilar(currentMovie.getId());

        // Init recyclar casting
        castPersons = new ArrayList<CastPerson>();
        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCasting.setLayoutManager(layoutManagerCast);
        adapterCasting = new CastingRecyclerAdapter(getActivity().getApplicationContext(), castPersons);
        adapterCasting.setClickListener(this);
        recyclerViewCasting.setAdapter(adapterCasting);

        // Init credits call
        presenter.loadCredits(currentMovie.getId());
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        Log.d("DetailFragment", "CLICKLISTENER");
        if (CASTING_RECYCLER_ADAPTER.equals(recycler)) {
            presenter.loadPerson(castPersons.get(position).getId());
        } else if(HORIZONTAL_RECYCLER_ADAPTER.equals(recycler)) {
            presenter.loadCompleteMovie(similarMovies.get(position).getId());
        }
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        this.presenter = presenter;

    }

    @Override
    public void setSimilarMovies(@NonNull ArrayList<Movie> movies) {
        this.similarMovies = movies;
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setCasting(@NonNull ArrayList<CastPerson> cast) {
        this.castPersons = cast;
        adapterCasting.replace(cast);
        adapterCasting.notifyDataSetChanged();
    }

    @Override
    public void setPerson(@NonNull Person person) {
        this.person = person;
    }

    @Override
    public void showDetailPerson(@NonNull Person person) {
        Intent i = new Intent(getActivity(), DetailPersonActivity.class);
        i.putExtra("person", person);
        startActivity(i);
    }

    @Override
    public void showDetailMovie(@NonNull MovieComplete movie) {
        if (!is_720) {
            Intent i = new Intent(getActivity(), DetailActivity.class);
            i.putExtra("movie", movie);
            startActivity(i);
        } else {
            ((PopularFragment.Callback) getActivity()).onItemSelected(movie);
        }
    }

    @Override
    public void setCompleteMovie(@NonNull MovieComplete movie) {
        this.currentMovie = movie;
    }

    public boolean checkIfExists(MovieComplete movie){

        RealmQuery<Movie> query = realm.where(Movie.class)
                .equalTo("id", movie.getId());

        return query.count() != 0;
    }
}
