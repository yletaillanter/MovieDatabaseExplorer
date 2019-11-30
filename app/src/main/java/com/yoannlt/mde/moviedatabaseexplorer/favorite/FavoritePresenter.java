package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public class FavoritePresenter implements FavoriteContract.Presenter{

    private FavoriteContract.View mView;
    private Realm realm;
    private RealmResults<Movie> moviesRealm;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;


    public FavoritePresenter(@NonNull FavoriteContract.View mView) {
        this.mView = mView;
        //MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void getMovieComplete(@NonNull int id) {
        compositeDisposable.add(
            request.getMovieById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<MovieComplete>() {
                        @Override
                        public void onNext(MovieComplete movie){
                            mView.launchDetailMovie(movie);
                        }

                        @Override
                        public void onError(Throwable e)
                        {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {}

                    })
        );
    }
                 //           mView.launchDetailMovie(movieResponse);

    private void loadFavoriteMovies(){
        moviesRealm = realm.where(Movie.class).equalTo("listSource", "favorite").findAllAsync();

        compositeDisposable.add(
            moviesRealm.asFlowable()
                    .subscribeWith(new DisposableSubscriber<RealmResults<Movie>>() {
                        @Override
                        public void onComplete() {}

                        @Override
                        public void onError(Throwable e) {}

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
        compositeDisposable.clear();
    }
}
