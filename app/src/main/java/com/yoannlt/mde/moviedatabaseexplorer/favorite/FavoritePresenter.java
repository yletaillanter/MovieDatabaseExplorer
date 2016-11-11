package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

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

    @Inject RequestInterface request;


    public FavoritePresenter(@NonNull FavoriteContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
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
