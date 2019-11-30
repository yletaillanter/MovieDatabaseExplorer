package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.content.Intent;

import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.accueil.AccueilActivity;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoriteActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvancedSearchActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout_as) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_as) NavigationView navigationView;
    @BindView(R.id.my_toolbar_as) Toolbar toolbar;
    private AdvancedSearchPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.advanced_search_title);

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
                        Intent intentSearch = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentSearch);
                        break;
                    case R.id.advanced:
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

        AdvancedSearchFragment fragment = (AdvancedSearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameAdvancedSearch);
        if(fragment == null) {
            fragment = AdvancedSearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameAdvancedSearch);
        }

        presenter = new AdvancedSearchPresenter(fragment);
        fragment.setPresenter(presenter);

    }

    @Override
    public void onStart(){
        super.onStart();
        navigationView.setCheckedItem(R.id.advanced);
    }
}
