package com.yoannlt.mde.moviedatabaseexplorer.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.RecyclerViewGalleryAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONImagesResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonImagesJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Image;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

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
import rx.subscriptions.CompositeSubscription;

//TODO : MVPisation
public class GalleryActivity extends AppCompatActivity implements ClickListener {

    @BindView(R.id.gallery_recyclerview) RecyclerView recyclerView;
    @Inject RequestInterface request;
    private CompositeSubscription compositeSubscription;

    private RecyclerViewGalleryAdapter adapter;
    private Person person;
    private MovieComplete movie;
    private ArrayList<Image> images;

    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        MovieExplorer.application().getMovieExplorerComponent().inject(this);

        compositeSubscription = new CompositeSubscription();

        getParcelableExtraFromIntent();
        initRecyclerView();
    }

    private void loadImagesPerson(int id) {
        compositeSubscription.add(
            request.getPersonImage(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PersonImagesJSONResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(PersonImagesJSONResponse personImagesJSONResponseCall) {
                            PersonImagesJSONResponse jsonResponse = personImagesJSONResponseCall;
                            images = new ArrayList<>(Arrays.asList(jsonResponse.getProfiles()));
                            adapter.replace(images);
                            adapter.notifyDataSetChanged();
                        }
                    })
        );
    }

    private void loadImagesMovie(int id) {
        compositeSubscription.add(
            request.getMovieImage(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONImagesResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(JSONImagesResponse jsonImagesResponse) {
                            JSONImagesResponse jsonResponse = jsonImagesResponse;
                            images = concatArrayAsArrayList(jsonResponse.getPosters(), jsonResponse.getBackdrops());
                            adapter.replace(images);
                            adapter.notifyDataSetChanged();
                        }
                    })
        );
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        Intent i = new Intent(getApplicationContext(), FullScreenImageViewActivity.class);
        i.putExtra("img", images.get(position).getFile_path());
        startActivity(i);
    }

    private ArrayList<Image> concatArrayAsArrayList(Image[] poster, Image[] backdrop) {
        ArrayList<Image> imgReturn = new ArrayList<Image>();
        for (Image item : poster) {
            imgReturn.add(item);
        }

        for (Image item : backdrop) {
            imgReturn.add(item);
        }

        return imgReturn;
    }

    private void initRecyclerView(){
        images = new ArrayList<Image>();
        GridLayoutManager layoutManagerGallery = new GridLayoutManager(GalleryActivity.this, 3);
        recyclerView.setLayoutManager(layoutManagerGallery);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewGalleryAdapter(getApplicationContext(), images);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        if (from != null) {
            if (from.equals("movie")) {
                loadImagesMovie(movie.getId());
            } else {
                loadImagesPerson(person.getId());
            }
        }
    }

    private void getParcelableExtraFromIntent(){
        from = getIntent().getStringExtra("from");
        if (from != null) {
            if (from.equals("movie")) {
                movie = getIntent().getParcelableExtra("movie");
            } else {
                person = getIntent().getParcelableExtra("person");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeSubscription.clear();
    }
}
