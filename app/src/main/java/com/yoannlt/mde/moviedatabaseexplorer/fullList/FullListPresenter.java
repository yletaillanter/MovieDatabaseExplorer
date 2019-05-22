package com.yoannlt.mde.moviedatabaseexplorer.fullList;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by yoannlt on 24/10/2016.
 */

public class FullListPresenter implements FullListContract.Presenter {

    @NonNull private final FullListContract.View mView;
    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;

    public FullListPresenter(@NonNull FullListContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadPopular() {
        compositeDisposable.add(
            request.getPopular()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<JSONResponse>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                        }
                    })
        );
    }

    @Override
    public void loadNowPlaying() {
        compositeDisposable.add(
            request.getNowPlaying()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<JSONResponse>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil nowpla error", e.getMessage());

                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                        }
                    })
        );
    }

    @Override
    public void loadUpcoming() {
        compositeDisposable.add(
            request.getUpcoming()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<JSONResponse>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil upcom error", e.getMessage());

                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                        }
                    })
        );
    }

    @Override
    public void loadTopRated() {
        compositeDisposable.add(
            request.getTopRated()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<JSONResponse>() {

                        @Override public void onComplete() {}

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil toprat error", e.getMessage());
                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                        }
                    })
        );
    }

    @Override
    public void getMovieComplete(@NonNull int id) {
        compositeDisposable.add(
            request.getMovieById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<MovieComplete>() {

                        @Override public void onComplete() {}

                        @Override public void onError(Throwable e) {}

                        @Override
                        public void onNext(MovieComplete movieResponse) {
                            mView.launchDetailMovie(movieResponse);
                        }
                    })
        );

    }

    @Override
    public void initAdvancedSearch() {
        mView.startAdvancedSearch();
    }

    @Override
    public void loadAdvancedSearch(@NonNull HashMap<String, String> queries) {
        compositeDisposable.add(
            request.movieDiscoverTmdb(queries)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<JSONResponse>() {
                        @Override
                        public void onComplete() {
                            Log.d("AdvancedSearchPresenter", "Complete");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("AdSearchPresenterError", e.getMessage());
                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            Log.d("retrofit next", jsonResponse.toString());
                            mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                        }
                    })
        );
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void subscribe(String source) {
        switch(source) {
            case (ActivityUtils.FROM_RECYCLER_NOW_PLAYING):
                loadNowPlaying();
                break;
            case (ActivityUtils.FROM_RECYCLER_POPULAR):
                loadPopular();
                break;
            case (ActivityUtils.FROM_RECYCLER_TOP_RATED):
                loadTopRated();
                break;
            case (ActivityUtils.FROM_RECYCLER_UP_COMING):
                loadUpcoming();
                break;
            case (ActivityUtils.FROM_ADVANCED_SEARCH):
                initAdvancedSearch();
                break;
        }
    }
}
