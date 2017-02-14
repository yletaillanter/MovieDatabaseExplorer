package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import io.realm.Realm;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yoannlt on 25/11/2016.
 */

public class AccueilPresenter implements AccueilContract.Presenter  {

    @Inject RequestInterface request;
    private CompositeSubscription compositeSubscription;
    @NonNull private final AccueilContract.View mView;
    AccueilRepository accueilRepository;


    public AccueilPresenter(@NonNull AccueilContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeSubscription = new CompositeSubscription();
        accueilRepository = new AccueilRepository();

    }

    @Override
    public void loadPopular() {
        compositeSubscription.add(
            accueilRepository.loadPopular()
                    .subscribe(new Subscriber<List<Movie>>() {
                        @Override
                        public void onCompleted() {
                        }

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
        compositeSubscription.add(
            request.getNowPlaying()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONResponse>() {
                        @Override
                        public void onCompleted() {

                        }

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
        compositeSubscription.add(
            request.getUpcoming()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONResponse>() {
                        @Override
                        public void onCompleted() {

                        }

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
        compositeSubscription.add(
            request.getTopRated()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONResponse>() {
                        @Override
                        public void onCompleted() {

                        }

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

    @Override
    public void subscribe(@Nullable String source) {
        loadPopular();
        loadNowPlaying();
        loadUpcoming();
        loadTopRated();
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
    }
}
