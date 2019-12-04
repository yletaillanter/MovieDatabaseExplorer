package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by yoannlt on 05/11/2016.
 */
public class FavoritePresenter implements FavoriteContract.Presenter {

    private final String LOG_TAG = getClass().getName();

    private FavoriteContract.View mView;
    private Realm realm;

    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;

    protected FavoritePresenter(@NonNull FavoriteContract.View mView) {
        Log.d(LOG_TAG, "FavoritePresenter Constructor");

        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void getMovieComplete(int id) {
        Log.d(LOG_TAG, "getMovieComplete: " + id);

        compositeDisposable.add(
            request.getMovieById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<MovieComplete>() {
                        @Override
                        public void onNext(MovieComplete movie) {
                            mView.launchDetailMovie(movie);
                        }
                        @Override
                        public void onError(Throwable e) { e.printStackTrace(); }
                        @Override
                        public void onComplete() {}
                    })
        );
    }

    private void loadFavoriteMovies() {
        Log.d(LOG_TAG, "LoadFavoriteMovies");

        RealmResults<Movie> moviesRealm = realm.where(Movie.class).equalTo("listSource", "favorite").findAllAsync();

        compositeDisposable.add(
            moviesRealm.asFlowable()
                    .subscribeWith(new DisposableSubscriber<RealmResults<Movie>>() {
                        @Override
                        public void onComplete() {
                        }

                        @Override
                        public void onError(Throwable e) {}

                        @Override
                        public void onNext(RealmResults<Movie> movies) {
                            mView.showFavorites(new ArrayList<>(movies));
                        }
                    })
        );
    }

    @Override
    public void subscribe(@Nullable String source) {
        Log.d(LOG_TAG, "subscribe");

        loadFavoriteMovies();
    }

    @Override
    public void unsubscribe() {
        Log.d(LOG_TAG, "unsubscribe");

        compositeDisposable.clear();
    }
}
