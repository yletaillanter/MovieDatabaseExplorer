package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.model.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.SimilarJSONResponse;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailPerson extends AppCompatActivity implements ClickListener {

    final String BASE_URL_EMULATOR = "http://10.0.2.2:5001/";
    private final String BASE_URL_PHYSICAL_DEVICE = "http://192.168.0.16:5001/";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    @BindView(R.id.collapse_toolbar_person) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.picture_person) ImageView picture;
    @BindView(R.id.birthdate) TextView birtdate;
    @BindView(R.id.birthplace) TextView birtplace;
    @BindView(R.id.biography) TextView biograhy;
    @BindView(R.id.recycler_other) RecyclerView recyclerViewOther;

    private ArrayList<OtherMoviesFromPerson> otherMovies;
    private OtherMoviesAdapter adapter;

    MovieComplete movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);

        ButterKnife.bind(this);

        Person person = getIntent().getParcelableExtra("person");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout.setTitle(person.getName());

        Picasso.with(getApplicationContext()).load(BASE_IMAGE_URL + person.getProfile_path()).fit().into(picture);

        birtdate.setText(person.getBirthday());
        birtplace.setText(person.getPlace_of_birth());
        biograhy.setText(person.getBiography());

        //Init du recyclerView other
        otherMovies = new ArrayList<OtherMoviesFromPerson>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(DetailPerson.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOther.setLayoutManager(horizontalLayoutManager);
        adapter = new OtherMoviesAdapter(getApplicationContext(), otherMovies);
        adapter.setClickListener(this);
        recyclerViewOther.setAdapter(adapter);
        loadOtherMoviesFromPerson(person.getId());

        // TODO : Implement color for detail person
        // COULEUR TOOLBAR
/*        try {
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
        }*/
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        loadCompleteMovie(Integer.parseInt(otherMovies.get(position).getId()));
/*        Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
        Movie movieParcelable = otherMovies.get(position);
        intent.putExtra("movie", movieParcelable);
        startActivity(intent);*/
    }

    private void loadOtherMoviesFromPerson(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_PHYSICAL_DEVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);

        Call<PersonCreditsJSONResponse> call = request.getOtherMovies(id);
        call.enqueue(new Callback<PersonCreditsJSONResponse>() {
            @Override
            public void onResponse(Call<PersonCreditsJSONResponse> call, Response<PersonCreditsJSONResponse> response) {
                PersonCreditsJSONResponse jsonResponse = response.body();
                if(jsonResponse.count() > 1) {
                    otherMovies = new ArrayList<>(Arrays.asList(jsonResponse.getCast()));
                    Log.d("Retrofit Response : ", otherMovies.toString());
                    adapter.replace(otherMovies);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<PersonCreditsJSONResponse> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });

    }

    private void loadCompleteMovie(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_PHYSICAL_DEVICE)
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

    private void startMovieActivity(){
        Intent intent = new Intent(DetailPerson.this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

/*    private void loadPerson(int id){
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
                initViewElements();

            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("Retrofit Error", t.getMessage());
            }
        });
    }*/

/*    private void setToolbarTitle() {
        collapsingToolbarLayout.setTitle(person.getName());
    }*/

/*    private void initViewElements(){
        setToolbarTitle();
        Picasso.with(getApplicationContext()).load(BASE_IMAGE_URL + person.getProfile_path()).fit().into(picture);
        birtdate.setText(person.getBirthday());
        birtplace.setText(person.getPlace_of_birth());
        biograhy.setText(person.getBiography());
    }*/
}
