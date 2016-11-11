package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailPresenter;
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

        FavoriteFragment fragment = (FavoriteFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame_favorite);
        if(fragment == null) {
            fragment = FavoriteFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame_favorite);
        }

        presenter = new FavoritePresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
