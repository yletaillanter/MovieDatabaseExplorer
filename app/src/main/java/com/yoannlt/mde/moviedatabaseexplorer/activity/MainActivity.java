package com.yoannlt.mde.moviedatabaseexplorer.activity;

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

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private final String LOG_TAG = getClass().getSimpleName();
    private final String TITLE = "Movie Explorer";

    //@BindView(R.id.MyToolbar2) Toolbar toolbar;
    @BindView(R.id.card_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.search_input) EditText searchInput;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;

    @Inject RequestInterface request;

    ListSearchAdapter adapter;
    ArrayList<Movie> movies;
    MovieComplete movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
    }

    private void startActivity(){
        //Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("movie", movie);
        startActivity(i);
    }
}
