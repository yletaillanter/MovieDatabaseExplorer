package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;

    private PopularPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        // Do nothing, we're already on that screen
                        break;
                    case R.id.search:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        PopularFragment fragment = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null ) {
            fragment = PopularFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        // Create the presenter
        presenter = new PopularPresenter(fragment);

        fragment.setPresenter(presenter);
    }
}
