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
                        Toast.makeText(getApplicationContext(), "accueil", Toast.LENGTH_SHORT).show();
                        // Do nothing, we're already on that screen
                        break;
                    case R.id.search:
                        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
                        //Intent intent = new Intent(TasksActivity.this, StatisticsActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK                                       | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        //startActivity(intent);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
/*         mDrawerLayout.addView(navigationView);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }*/

        PopularFragment fragment = (PopularFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null ) {
            fragment = PopularFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
        }

        // Create the presenter
        presenter = new PopularPresenter(fragment);

        fragment.setPresenter(presenter);
    }

    /*
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.accueil:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.search:
                                //Intent intent = new Intent(TasksActivity.this, StatisticsActivity.class);
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK                                       | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    */
}
