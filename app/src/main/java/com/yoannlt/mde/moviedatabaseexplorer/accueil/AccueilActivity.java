package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchActivity;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoriteActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccueilActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();

    private AccueilPresenter accueilPresenter;
    @BindView(R.id.drawer_layout_accueil) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_accueil) NavigationView navigationView;
    @BindView(R.id.toolbar_accueil) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.films);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        Log.d(LOG_TAG, "select accueil");
                        break;
                    case R.id.search:
                        Log.d(LOG_TAG, "select search");
                        Intent intentSearch = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.advanced:
                        Log.d(LOG_TAG, "select advanced");
                        Intent intentAdvancedSearch = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                        startActivity(intentAdvancedSearch);
                        break;
                    case R.id.favorite:
                        Log.d(LOG_TAG, "select favorite");
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
        AccueilFragment fragment = (AccueilFragment) getSupportFragmentManager().findFragmentById(R.id.content_fragment_accueil);
        if (fragment == null ) {
            Log.d(LOG_TAG, "AccueilFragment newInstance()");
            fragment = AccueilFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_fragment_accueil);
        }
        // Create the popularPresenter
        accueilPresenter = new AccueilPresenter(fragment);
        // attached the popularPresenter to the fragment
        fragment.setPresenter(accueilPresenter);
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        navigationView.setCheckedItem(R.id.accueil);
    }
}
