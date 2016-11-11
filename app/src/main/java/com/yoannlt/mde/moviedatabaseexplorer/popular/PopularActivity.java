package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MenuItem;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailFragment;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoriteActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularActivity extends AppCompatActivity implements PopularFragment.Callback {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;
    @BindView(R.id.MyToolbar) Toolbar toolbar;
    @BindBool(R.bool.is_720) boolean is_720;

    private PopularPresenter popularPresenter;
    private DetailPresenter detailPresenter;

    private MovieComplete movie;

    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpAnimation();
        setContentView(R.layout.activity_popular);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.popular_title);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        break;
                    case R.id.search:
                        Intent intentSearch = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentSearch);
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


        // Test de la taille de l'écran
        if(is_720) {
            // FRAGMENT 1
            PopularFragment fragment = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame1);
            if (fragment == null ) {
                fragment = PopularFragment.newInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame1);
            }
            // Create the popularPresenter
            popularPresenter = new PopularPresenter(fragment);
            // attached the popularPresenter to the fragment
            fragment.setPresenter(popularPresenter);
        } else {
            PopularFragment fragment = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (fragment == null ) {
                fragment = PopularFragment.newInstance();
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
            }
            // Create the popularPresenter
            popularPresenter = new PopularPresenter(fragment);
            // attached the popularPresenter to the fragment
            fragment.setPresenter(popularPresenter);
        }
    }

    public void setUpAnimation(){
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setExitTransition(slide);
    }

    @Override
    public void onItemSelected(MovieComplete movie) {
        this.movie = movie;
        Log.d("test", movie.toString());

        //FRAGMENT 2
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame2);
        if (detailFragment == null) {
            detailFragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), detailFragment, R.id.contentFrame2);
        } else {
            detailFragment = DetailFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), detailFragment, R.id.contentFrame2);
        }
        detailPresenter = new DetailPresenter(detailFragment);
        detailPresenter.setCompleteMovie(movie);
        detailFragment.setPresenter(detailPresenter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (is_720)
            ActivityUtils.removeFragment(getSupportFragmentManager(),detailFragment);
    }
}
