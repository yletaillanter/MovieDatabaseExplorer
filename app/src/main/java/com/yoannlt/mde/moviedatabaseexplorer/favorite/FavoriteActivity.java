package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.accueil.AccueilActivity;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getName();

    @BindView(R.id.drawer_layout_favorite) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_favorite) NavigationView navigationView;
    @BindView(R.id.my_toolbar_favorite) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "FavoriteActivity onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.favorite_title);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setCheckedItem(R.id.favorite);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent intent = new Intent(getApplicationContext(), AccueilActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        Intent intentSearch = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.advanced:
                        Intent intentAdvancedSearch = new Intent(getApplicationContext(), AdvancedSearchActivity.class);
                        startActivity(intentAdvancedSearch);
                        break;
                    case R.id.favorite:
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        FavoriteFragment fragment = (FavoriteFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_favorite);
        if(fragment == null) {
            Log.d(LOG_TAG, "FavoriteFragment newInstance()");
            fragment = FavoriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame_favorite);
        }

        FavoritePresenter presenter = new FavoritePresenter(fragment);
        fragment.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.favorite);
    }
}
