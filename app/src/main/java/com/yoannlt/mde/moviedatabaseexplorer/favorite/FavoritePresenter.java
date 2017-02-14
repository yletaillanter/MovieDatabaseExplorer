package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yoannlt on 05/11/2016.
 */

public class FavoritePresenter implements FavoriteContract.Presenter{

    private FavoriteContract.View mView;
    private Realm realm;
    private RealmResults<Movie> moviesRealm;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    @Inject RequestInterface request;
    private CompositeSubscription compositeSubscription;


    public FavoritePresenter(@NonNull FavoriteContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeSubscription = new CompositeSubscription();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void getMovieComplete(@NonNull int id) {
        compositeSubscription.add(
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
                    })
        );
    }

    private void loadFavoriteMovies(){
        moviesRealm = realm.where(Movie.class).equalTo("listSource", "favorite").findAllAsync();

        compositeSubscription.add(
            moviesRealm.asObservable()
                    .subscribe(new Observer<RealmResults<Movie>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RealmResults<Movie> movies) {
                            mView.showFavorites(new ArrayList<Movie>(movies));
                        }
                    })
        );
    }

    @Override
    public void subscribe(@Nullable String source) {
        loadFavoriteMovies();
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }
}
