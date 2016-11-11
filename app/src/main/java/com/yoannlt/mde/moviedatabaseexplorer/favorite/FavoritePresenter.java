package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 05/11/2016.
 */

public class FavoritePresenter implements FavoriteContract.Presenter{

    private FavoriteContract.View mView;
    private Realm realm;
    private RealmResults<Movie> moviesRealm;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";

    private static Retrofit retrofit;
    private static RequestInterface request;
    private static OkHttpClient okHttpClient2;

    public FavoritePresenter(@NonNull FavoriteContract.View mView) {
        this.mView = mView;

        // Init interceptor retrofit + rest interface
        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
    }

    @Override
    public void getMovieComplete(@NonNull int id) {
        request.getMovieById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieComplete>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieComplete movieResponse) {
                        mView.launchDetailMovie(movieResponse);
                    }
                });
    }

    @Override
    public void subscribe() {
        realm = Realm.getDefaultInstance();
        moviesRealm = realm.where(Movie.class).findAll();
        for (Movie f : moviesRealm) {
            movies.add(f);
        }
        Log.d("FavPresenterRealm: ", moviesRealm.toString());
        Log.d("FavPresenter: ", movies.toString());
        mView.showFavorites(movies);
    }

    @Override
    public void unsubscribe() {
    }
}
