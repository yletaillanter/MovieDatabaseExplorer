package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.accueil.AccueilActivity;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchActivity;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout_favorite) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_favorite) NavigationView navigationView;
    @BindView(R.id.my_toolbar_favorite) Toolbar toolbar;
    private FavoritePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.favorite_title);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        navigationView.setCheckedItem(R.id.favorite);

        TextView name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.appli_name_header);
        Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/cinema/cinema_st.ttf");
        name.setTypeface(myTypeface);

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
            fragment = FavoriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame_favorite);
        }

        presenter = new FavoritePresenter(fragment);
        fragment.setPresenter(presenter);
    }

    @Override
    public void onStart(){
        super.onStart();
        navigationView.setCheckedItem(R.id.favorite);
    }
}
