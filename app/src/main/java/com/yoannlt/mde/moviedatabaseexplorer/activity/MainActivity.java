package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.popular.PopularActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private final String LOG_TAG = getClass().getSimpleName();
    private final String TITLE = "Movie Explorer";
    private final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";

    //@BindView(R.id.MyToolbar2) Toolbar toolbar;
    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_input) EditText searchInput;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;

    private OkHttpClient okHttpClient2;
    private Retrofit retrofit;
    private RequestInterface request;

    ListSearchAdapter adapter;
    ArrayList<Movie> movies;
    MovieComplete movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //toolbar.setTitle(TITLE);
        //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //setSupportActionBar(toolbar);

        init();
        initSearch();
    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        movies = new ArrayList<Movie>();
        adapter = new ListSearchAdapter(getApplicationContext(), movies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent intent = new Intent(getApplicationContext(), PopularActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void initSearch() {

        // Get the search input
        searchInput.getText().toString();

        // add a listener when the search request is modified
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Start the searching when the search get over two characters
                if (searchInput.getText().toString().length()>1) {
                    searchMovie(searchInput.getText().toString());
                } else {
                    // Else empty the result list (when deleting the search input)
                    adapter.replace(new ArrayList<Movie>());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        // API rest retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        request = retrofit.create(RequestInterface.class);

    }

    private void searchMovie(String searchValue) {


        request.movieSearchTmdb(searchValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        if (jsonResponse != null && jsonResponse.count() >= 1) {
                            movies = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                            adapter.replace(movies);
                            adapter.notifyDataSetChanged();
                        }
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
                        startActivity();
                    }
                });
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {

        loadCompleteMovie(adapter.getmDataset().get(position).getId());

        // TODO : Fix shared element from recycler view
        //Shared element + Transition
        //Pair<View, String> p1 = Pair.create((View)titleForTransition,"title_transition");
        //Pair<View, String> p2 = Pair.create((View)posterForTransition,"poster_transition");
        //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2);
        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this,cardTransition,cardTransition.getTransitionName()).toBundle();
    }

    private void startActivity(){
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("movie", movie);
        startActivity(i, bundle);
    }
}
