package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.popular.PopularActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailPersonActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView navigationView;

    private DetailPersonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);
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

        DetailPersonFragment fragment = (DetailPersonFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameDetailPerson);
        if (fragment == null) {
            fragment = DetailPersonFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameDetailPerson);
        }

        presenter = new DetailPersonPresenter(fragment);
        fragment.setPresenter(presenter);

        //Chargement des images profile
        //loadProfiles(person.getId());

        // TODO : Implement color for detail person
        // COULEUR TOOLBAR
/*        try {
            if(movie.getBackdrop_path() != "") {
                InputStream imageStream = new URL(BASE_IMAGE_URL + movie.getBackdrop_path()).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        if (palette.getVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getVibrantSwatch().getRgb());
                        } else if (palette.getDarkVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getDarkVibrantSwatch().getRgb());
                        } else if (palette.getLightVibrantSwatch() != null) {
                            collapsingToolbarLayout.setContentScrimColor(palette.getLightVibrantSwatch().getRgb());
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Error on Palette", ""+ex);
        }*/
    }
}
