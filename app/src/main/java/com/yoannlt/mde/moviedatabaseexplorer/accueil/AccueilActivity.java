package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchActivity;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoriteActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccueilActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private AccueilPresenter acceuilPresenter;
    @BindView(R.id.drawer_layout_acceuil) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_acceuil) NavigationView navigationView;
    @BindView(R.id.toolbar_acceuil) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.films);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setCheckedItem(R.id.accueil);

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
                    case R.id.advanced:
                        Intent intentAdvancedSearch = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                        startActivity(intentAdvancedSearch);
                        Log.d(LOG_TAG, "menu advancedSearch");
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

        // FRAGMENT 1
        AccueilFragment fragment = (AccueilFragment) getSupportFragmentManager().findFragmentById(R.id.content_fragment_acceuil);
        if (fragment == null ) {
            fragment = AccueilFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_fragment_acceuil);
        }
        // Create the popularPresenter
        acceuilPresenter = new AccueilPresenter(fragment);
        // attached the popularPresenter to the fragment
        fragment.setPresenter(acceuilPresenter);
    }
}
