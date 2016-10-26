package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.gallery) ImageView gallery;


    private ArrayList<OtherMoviesFromPerson> otherMovies;
    private OtherMoviesAdapter adapter;

    private MovieComplete movie;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);

        ButterKnife.bind(this);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Init interceptor retrofit + rest interface
        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();

                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);

        person = getIntent().getParcelableExtra("person");

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
        //loadProfiles(person.getId());

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
                i.putExtra("from", "person");
                i.putExtra("person", person);
                startActivity(i);
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullScreenActivity();
            }
        });

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
    }

    private void loadOtherMoviesFromPerson(int id) {

        request.getOtherMovies(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PersonCreditsJSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PersonCreditsJSONResponse personCreditsJSONResponse) {
                        otherMovies = new ArrayList<>(Arrays.asList(personCreditsJSONResponse.getCast()));
                        adapter.replace(otherMovies);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void loadCompleteMovie(int id) {

        request.getMovieById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieComplete>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieComplete movieComplete) {
                        movie = movieComplete;
                        startMovieActivity();
                    }
                });
    }

    private void startMovieActivity(){
        Intent intent = new Intent(DetailPerson.this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private void startFullScreenActivity(){
        if(person != null) {
            Intent i = new Intent(getApplicationContext(), FullScreenImageViewActivity.class);
            i.putExtra("img", person.getProfile_path());
            startActivity(i);
        }
    }
}
