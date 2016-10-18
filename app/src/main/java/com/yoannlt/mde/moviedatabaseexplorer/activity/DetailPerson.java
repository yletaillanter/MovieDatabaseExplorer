package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonImagesJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.Profiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailPerson extends AppCompatActivity implements ClickListener {

    private final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";

    private Retrofit retrofit;
    private RequestInterface request;
    private OkHttpClient okHttpClient2;

    @BindView(R.id.collapse_toolbar_person) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.picture_person) ImageView picture;
    @BindView(R.id.birthdate) TextView birtdate;
    @BindView(R.id.birthplace) TextView birtplace;
    @BindView(R.id.biography) TextView biograhy;
    @BindView(R.id.recycler_other) RecyclerView recyclerViewOther;

    private ArrayList<OtherMoviesFromPerson> otherMovies;
    private OtherMoviesAdapter adapter;

    MovieComplete movie;

    private ArrayList<Profiles> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);

        ButterKnife.bind(this);

        // Init interceptor retrofit + rest interface
        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);

        Person person = getIntent().getParcelableExtra("person");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_detail);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //Chargement des images profile
        loadProfiles(person.getId());

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

    // Charge la liste d'images attachées à la personne
    private void loadProfiles(int id) {

        Call<PersonImagesJSONResponse> call = request.getPersonImages(id);
        call.enqueue(new Callback<PersonImagesJSONResponse>() {
            @Override
            public void onResponse(Call<PersonImagesJSONResponse> call, Response<PersonImagesJSONResponse> response) {
                PersonImagesJSONResponse imagesJSONResponse = response.body();
                if(imagesJSONResponse!= null && imagesJSONResponse.count() >= 1) {
                    profiles = new ArrayList<>(Arrays.asList(imagesJSONResponse.getProfiles()));
                    Log.d("PROFILETEST Response : ", profiles.toString());
                }
            }

            @Override
            public void onFailure(Call<PersonImagesJSONResponse> call, Throwable t) {
                Log.d(this.getClass().getSimpleName(), "LoadProfiles failes");
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
