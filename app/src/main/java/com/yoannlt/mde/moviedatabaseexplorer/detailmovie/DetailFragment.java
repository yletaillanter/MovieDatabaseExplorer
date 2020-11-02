package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.CastingRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailperson.DetailPersonActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListFragment;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.CrewPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.palette.PaletteTransformation;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

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

    private static final String EXECUTIVE_PRODUCER = "Producer";
    private static final String DIRECTOR = "Director";
    private static final String WRITER = "Writer";
    private static final String TAG = "DetailFragment";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";
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

    // Crew
    private ArrayList<CrewPerson> crewPersons = new ArrayList<>();

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
    @BindView(R.id.director) TextView director;
    @BindView(R.id.executive_producer) TextView producer;
    @BindView(R.id.writer) TextView writer;

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
        presenter.subscribe(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, root);

        if(!is_720) {
            currentMovie = getActivity().getIntent().getParcelableExtra("movie");
        } else {
            currentMovie = presenter.getMovieFromActivityCallback();
        }

        initLayoutComponents();
        initRecyclerView();

        if(!is_720) {
            initToolbar();
            initPalettePersonalization();
        }

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

        //if (currentMovie.getRevenue() == 0) {
        //    revenueLabel.setVisibility(View.GONE);
        //} else {
            revenue.setText("" + currentMovie.getRevenue());
        //}
        if (currentMovie.getProduction_companies() != null && currentMovie.getProduction_companies().length > 0) {
            production_companies.setText(currentMovie.getProduction_companies()[0].getName());
        }

        if(!is_720)
            Picasso.get().load(BASE_IMAGE_URL + currentMovie.getBackdrop_path()).into(back);
            Picasso.get().load(BASE_IMAGE_URL + currentMovie.getPoster_path()).into(poster);

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

        // Adding the current intent to the back stack
        //getActivity().getIntent().setFlags(getActivity().getIntent().getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

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

    @OnClick(R.id.video)
    public void launchVideo() {
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/")));
        Toast.makeText(getActivity().getApplicationContext(), "Not available yet", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.favorite)
    public void setFavorite() {
        realm.beginTransaction();
        if (checkIfExists(currentMovie)) {
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star));
            RealmResults<Movie> rows = realm.where(Movie.class).equalTo("id", currentMovie.getId()).equalTo("listSource", "favorite").findAll();
            rows.deleteFromRealm(0);
        } else {
            currentMovie.setListSource("favorite");
            Movie movietest = ActivityUtils.movieCompleteToMovie(currentMovie);
            favorite.setImageDrawable(getResources().getDrawable(R.drawable.star_yellow));
            Log.d("insert", movietest.toString());
            realm.insert(movietest);
        }
        realm.commitTransaction();
    }

    private void initPalettePersonalization() {

        window = getActivity().getWindow();

        Picasso
                .get()
                .load(BASE_IMAGE_URL + currentMovie.getBackdrop_path())
                .fit().centerCrop()
                .transform(PaletteTransformation.instance())
                .into(back, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Bitmap bitmap = ((BitmapDrawable) back.getDrawable()).getBitmap();
                        Palette palette = PaletteTransformation.getPalette(bitmap);

                        if (palette.getVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getVibrantSwatch().getRgb());

                            if (palette.getDarkVibrantSwatch() != null)
                                window.setStatusBarColor(palette.getDarkVibrantSwatch().getRgb());
                            else if (palette.getLightVibrantSwatch() != null)
                                window.setStatusBarColor(palette.getLightVibrantSwatch().getRgb());
                            else if (palette.getMutedSwatch() != null)
                                window.setStatusBarColor(palette.getMutedSwatch().getRgb());

                        } else if (palette.getDarkVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantSwatch().getRgb());

                            if (palette.getLightVibrantSwatch() != null)
                                window.setStatusBarColor(palette.getLightVibrantSwatch().getRgb());
                            else if (palette.getMutedSwatch() != null)
                                window.setStatusBarColor(palette.getMutedSwatch().getRgb());

                        } else if (palette.getLightVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getLightVibrantSwatch().getRgb());

                            if (palette.getMutedSwatch() != null)
                                window.setStatusBarColor(palette.getMutedSwatch().getRgb());

                        } else if (palette.getMutedSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getMutedSwatch().getRgb());
                            window.setStatusBarColor(palette.getMutedSwatch().getRgb());
                        }


                    }
                });
    }

    private void initRecyclerView() {
        //Init du recyclerView similar
        similarMovies = new ArrayList<Movie>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new HorizontalRecyclerAdapter(similarMovies, null);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Init Similar Movie
        presenter.loadSimilar(currentMovie.getId());

        // Init recyclar casting
        castPersons = new ArrayList<CastPerson>();
        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCasting.setLayoutManager(layoutManagerCast);
        adapterCasting = new CastingRecyclerAdapter(castPersons);
        adapterCasting.setClickListener(this);
        recyclerViewCasting.setAdapter(adapterCasting);

        // Init credits
        presenter.loadCredits(currentMovie.getId());
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        if (CASTING_RECYCLER_ADAPTER.equals(recycler)) {
            presenter.loadPerson(castPersons.get(position).getId());
        } else {
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
    public void setCrew(@NonNull ArrayList<CrewPerson> crew) {
        Log.i(TAG, "setCrew, size: " + crew.size());
        this.crewPersons = crew;

        for(CrewPerson crewPerson : crewPersons) {
            Log.i("DetailFragment", "forEach crew: " + crewPerson.getName());
            if (DIRECTOR.equals(crewPerson.getJob()))
                director.setText(crewPerson.getName());
            else if (EXECUTIVE_PRODUCER.equals(crewPerson.getJob()))
                producer.setText(crewPerson.getName());
            else if (WRITER.equals(crewPerson.getJob()))
                writer.setText(crewPerson.getName());
        }
    }

    @Override
    public void setPerson(@NonNull Person person) {
        this.person = person;
    }

    @Override
    public void showDetailPerson(@NonNull Person person) {
        Intent i = new Intent(getActivity(), DetailPersonActivity.class);
        i.putExtra("person", person);
        //i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    @Override
    public void showDetailMovie(@NonNull MovieComplete movie) {
        if (!is_720) {
            Intent i = new Intent(getActivity(), DetailActivity.class);
            i.putExtra("movie", movie);
            //i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
        } else {
            ((FullListFragment.Callback) getActivity()).onItemSelected(movie);
        }
    }

    @Override
    public void setCompleteMovie(@NonNull MovieComplete movie) {
        this.currentMovie = movie;
    }

    public boolean checkIfExists(MovieComplete movie) {

        RealmQuery<Movie> query = realm.where(Movie.class)
                .equalTo("id", movie.getId())
                .equalTo("listSource", "favorite");

        return query.count() != 0;
    }


}
