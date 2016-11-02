package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.popular.PopularActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;

    private DetailPresenter presenter;
    private Map<Integer, String> genres = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.accueil:
                        Intent intent = new Intent(getApplicationContext(), PopularActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.search:
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent2);
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameDetail);
        if (fragment == null) {
            fragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameDetail);
        }

        presenter = new DetailPresenter(fragment);
        fragment.setPresenter(presenter);


        //initGenreMap();

        //final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SLIDE OVERVIEW
        /*
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(R.id.overview_layout);
        slide.setInterpolator(AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in));
        slide.setDuration(300);
        getWindow().setEnterTransition(slide);
        */


        // TODO : fix genres list
        // Init Genres view
/*        if (movie.getGenre_ids() != null) {
            if(movie.getGenre_ids().size() == 1) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
            } else if (movie.getGenre_ids().size() == 2) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
                genre_two.setText(genres.get(movie.getGenre_ids().get(1)));
            } else if (movie.getGenre_ids().size() > 2) {
                genre_one.setText(genres.get(movie.getGenre_ids().get(0)));
                genre_two.setText(genres.get(movie.getGenre_ids().get(1)));
                genre_three.setText(genres.get(movie.getGenre_ids().get(2)));
            }
        }*/

    }
/*


    private void initGenreMap() {
        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(10769, "Foreign");
        genres.put(36, "History");
        genres.put(27, "Horro");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV Movie");
        genres.put(53, "Thriller");
        genres.put(10742, "War");
        genres.put(37, "Western");
    }
*/
/*
    private void startPersonActivity(){
        Intent intent = new Intent(getApplicationContext(), DetailPerson.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    private void startMovieActivity(){
        Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    private void startFullScreenActivity(){
        if(currentMovie != null) {
            Intent i = new Intent(getApplicationContext(), FullScreenImageViewActivity.class);
            i.putExtra("img", currentMovie.getPoster_path());
            startActivity(i);
        }
    }
    */
}