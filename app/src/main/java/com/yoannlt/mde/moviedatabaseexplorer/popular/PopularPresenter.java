package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 24/10/2016.
 */

public class PopularPresenter implements PopularContract.Presenter {

    @NonNull private final PopularContract.View mView;
    @Inject RequestInterface request;

    MovieComplete movieComplete;

    public PopularPresenter(@NonNull PopularContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
    }

    @Override
    public void loadPopular() {

        request.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        mView.showPopularMovies(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                    }
                });
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
    public void unsubscribe() {

    }

    @Override
    public void subscribe() {
        loadPopular();
    }
}
