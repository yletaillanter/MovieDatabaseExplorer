package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yoannlt on 25/11/2016.
 */

public class AccueilPresenter implements AccueilContract.Presenter  {

    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;
    @NonNull private final AccueilContract.View mView;
    AccueilRepository accueilRepository;


    public AccueilPresenter(@NonNull AccueilContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
        accueilRepository = new AccueilRepository();

    }

    @Override
    public void loadPopular() {
        compositeDisposable.add(
            accueilRepository.loadPopular()
                    .subscribeWith(new DisposableSubscriber<List<Movie>>() {
                        @Override
                        public void onComplete() {}

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil Popular error", e.getMessage());
                        }

                        @Override
                        public void onNext(List<Movie> movies) {
                            Log.d("resultPopular", movies.toString());
                            mView.showPopular(new ArrayList<>(movies));
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
                        @Override public void onComplete() {}

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil nowpla error", e.getMessage());
                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showNowPlaying(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
                        @Override public void onComplete() {}

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Accueil upcom error", e.getMessage());
                        }

                        @Override
                        public void onNext(JSONResponse jsonResponse) {
                            mView.showUpcoming(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
                            mView.showTopRated(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
    public void subscribe(@Nullable String source) {
        loadPopular();
        loadNowPlaying();
        loadUpcoming();
        loadTopRated();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
