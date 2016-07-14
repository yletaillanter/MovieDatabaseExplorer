package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.CastingRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.model.SimilarJSONResponse;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity implements ClickListener {

    /*private final String BASE_URL = "http://10.0.2.2:5001/";*/
    private final String BASE_URL = "http://192.168.0.16:5001/";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    private final String HORIZONTAL_RECYCLER_ADAPTER = "HorizontalRecyclerAdapter";
    private final String CASTING_RECYCLER_ADAPTER = "CastingRecyclerAdapter";

    @BindView(R.id.recycler_similar) RecyclerView recyclerView;
    @BindView(R.id.recycler_casting) RecyclerView recyclerViewCasting;
    @BindView(R.id.overview) TextView overview;
    @BindView(R.id.rate) TextView rate;
    @BindView(R.id.language) TextView language;
    @BindView(R.id.bgheader) ImageView back;
    @BindView(R.id.poster) ImageView poster;
    @BindView(R.id.genre_one) TextView genre_one;
    @BindView(R.id.genre_two) TextView genre_two;
    @BindView(R.id.genre_three) TextView genre_three;
    @BindView(R.id.releasedate) TextView release_date;
    @BindView(R.id.original_title) TextView original_title;

    public CollapsingToolbarLayout collapsingToolbarLayout;
    private Map<Integer, String> genres = new HashMap<Integer, String>();

    // LIST FILMS SIMILAIRES
    private ArrayList<Movie> similarMovies;
    private HorizontalRecyclerAdapter adapter;

    // LIST CASTING
    private ArrayList<CastPerson> castPersons;
    private CastingRecyclerAdapter adapterCasting;

    // PERSON OBJECT PARCELABLE
    private Person person = new Person();

    // MOVIE COMPLETE PARCELABLE
    MovieComplete movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //initGenreMap();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SLIDE OVERVIEW
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.overview_layout);
        slide.setInterpolator(
                AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.setDuration(200);
        getWindow().setEnterTransition(slide);

        MovieComplete movie = getIntent().getParcelableExtra("movie");

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());

        overview.setText(movie.getOverview());
        rate.setText("" + movie.getVote_average());
        language.setText(movie.getOriginal_language());
        release_date.setText(movie.getRelease_date());
        original_title.setText(movie.getOriginal_title());

        Picasso.with(getApplicationContext()).load(BASE_IMAGE_URL + movie.getBackdrop_path()).into(back);
        Picasso.with(getApplicationContext()).load(BASE_IMAGE_URL + movie.getPoster_path()).into(poster);

        try {
            if(movie.getBackdrop_path() != "") {
                InputStream imageStream = new URL(BASE_IMAGE_URL + movie.getBackdrop_path()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette.getVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getVibrantSwatch().getRgb());
                        } else if (palette.getDarkVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantSwatch().getRgb());
                        } else if (palette.getLightVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getLightVibrantSwatch().getRgb());
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Error on Palette", ""+ex);
        }

        // TODO : fix genres list
        // Init Genres view
/*        if (movie.getGenre_ids() != null) {
            if(movie.getGenre_ids().size() == 1) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
            } else if (movie.getGenre_ids().size() == 2) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
                genre_two.setText(genres.get(movie.getGenre_ids().get(1)));
            } else if (movie.getGenre_ids().size() > 2) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
                genre_two.setText(genres.get(movie.getGenre_ids().get(1)));
                genre_three.setText(genres.get(movie.getGenre_ids().get(2)));
            }
        }*/


        //Init du recyclerView similar
        similarMovies = new ArrayList<Movie>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new HorizontalRecyclerAdapter(getApplicationContext(), similarMovies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // Init Similar Movie
        loadSimilar(movie.getId());

        // Init recyclar casting
        castPersons = new ArrayList<CastPerson>();
        LinearLayoutManager layoutManagerCast = new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCasting.setLayoutManager(layoutManagerCast);
        adapterCasting = new CastingRecyclerAdapter(getApplicationContext(), castPersons);
        adapterCasting.setClickListener(this);
        recyclerViewCasting.setAdapter(adapterCasting);

        // Init credits call
        loadCredits(movie.getId());
    }

    // Get Colors from palette
    HashMap<String,Integer> processPalette (Palette p) {
        HashMap<String, Integer> map = new HashMap<>();

        if (p.getVibrantSwatch() != null)
            map.put("Vibrant", p.getVibrantSwatch().getRgb());
        if (p.getDarkVibrantSwatch() != null)
            map.put("DarkVibrant", p.getDarkVibrantSwatch().getRgb());
        if (p.getLightVibrantSwatch() != null)
            map.put("LightVibrant", p.getLightVibrantSwatch().getRgb());

        if (p.getMutedSwatch() != null)
            map.put("Muted", p.getMutedSwatch().getRgb());
        if (p.getDarkMutedSwatch() != null)
            map.put("DarkMuted", p.getDarkMutedSwatch().getRgb());
        if (p.getLightMutedSwatch() != null)
            map.put("LightMuted", p.getLightMutedSwatch().getRgb());

        return map;
    }

    private void initGenreMap() {
        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(10769, "Foreign");
        genres.put(36, "History");
        genres.put(27, "Horro");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV Movie");
        genres.put(53, "Thriller");
        genres.put(10742, "War");
        genres.put(37, "Western");
    }

    private void loadSimilar(int movieId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<SimilarJSONResponse> call = request.getSimilarMovies(movieId);
        call.enqueue(new Callback<SimilarJSONResponse>() {
            @Override
            public void onResponse(Call<SimilarJSONResponse> call, Response<SimilarJSONResponse> response) {
                SimilarJSONResponse jsonResponse = response.body();
                if(jsonResponse.count() > 1) {
                    similarMovies = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                    Log.d("Retrofit Response : ", similarMovies.toString());
                    adapter.replace(similarMovies);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<SimilarJSONResponse> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });
    }

    private void loadCredits(int movieId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<CastPersonJSONResponse> call = request.getMovieCredits(movieId);
        call.enqueue(new Callback<CastPersonJSONResponse>() {
            @Override
            public void onResponse(Call<CastPersonJSONResponse> call, Response<CastPersonJSONResponse> response) {
                CastPersonJSONResponse jsonResponse = response.body();
                if(jsonResponse.count() > 1) {
                    castPersons = new ArrayList<>(Arrays.asList(jsonResponse.getCast()));
                    Log.d("Retrofit Response : ", castPersons.toString());
                    adapterCasting.replace(castPersons);
                    adapterCasting.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CastPersonJSONResponse> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });
    }

    private void loadPerson(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<Person> call = request.getPerson(id);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                person = response.body();
                startPersonActivity();
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });
    }

    private void loadCompleteMovie(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<MovieComplete> call = request.getMovieById(id);
        call.enqueue(new Callback<MovieComplete>() {
            @Override
            public void onResponse(Call<MovieComplete> call, Response<MovieComplete> response) {
                movie = response.body();
                startMovieActivity();
            }

            @Override
            public void onFailure(Call<MovieComplete> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        if (CASTING_RECYCLER_ADAPTER.equals(recycler)) {
            //Toast.makeText(getApplicationContext(), castPersons.get(position).getId()+"", Toast.LENGTH_LONG).show();
            loadPerson(castPersons.get(position).getId());
        } else if(HORIZONTAL_RECYCLER_ADAPTER.equals(recycler)) {
            loadCompleteMovie(similarMovies.get(position).getId());
        }
    }


    private void startPersonActivity(){
        Intent intent = new Intent(getApplicationContext(), DetailPerson.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    private void startMovieActivity(){
        Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}