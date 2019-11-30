package com.yoannlt.mde.moviedatabaseexplorer.activity;

import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.accueil.AccueilActivity;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchActivity;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoriteActivity;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity implements ClickListener {

    private final String LOG_TAG = getClass().getSimpleName();
    private final String TITLE = "Movie Explorer";

    //@BindView(R.id.MyToolbar2) Toolbar toolbar;
    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_input) EditText searchInput;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;
    @BindView(R.id.my_toolbar_as) Toolbar toolbar;

    @Inject RequestInterface request;

    ListSearchAdapter adapter;
    ArrayList<Movie> movies;
    MovieComplete movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        MovieExplorer.application().getMovieExplorerComponent().inject(this);

        init();
        initSearch();
    }

    @Override
    public void onStart(){
        super.onStart();
        navigationView.setCheckedItem(R.id.search);
    }

    private void init() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.search_by_name);

        movies = new ArrayList<Movie>();
        adapter = new ListSearchAdapter(getApplicationContext(), movies);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.appli_name_header);
        //Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/cinema/cinema_st.ttf");
        //name.setTypeface(myTypeface);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        break;
                    case R.id.advanced:
                        Intent intentAdvanced = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                        startActivity(intentAdvanced);
                        break;
                    case R.id.favorite:
                        Intent intentFavorite = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intentFavorite);
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

    }

    private void searchMovie(String searchValue) {

        request.movieSearchTmdb(searchValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<JSONResponse>() {
                    @Override
                    public void onComplete() {

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

        Flowable<MovieComplete> obs = request.getMovieById(id);

            obs.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<MovieComplete>() {
                    @Override
                    public void onComplete() {
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
    }

    private void startActivity(){
        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("movie", movie);
        startActivity(i);
    }
}
