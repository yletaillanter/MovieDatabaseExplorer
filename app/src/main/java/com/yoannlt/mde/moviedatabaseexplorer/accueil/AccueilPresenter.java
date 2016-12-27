package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 25/11/2016.
 */

public class AccueilPresenter implements AccueilContract.Presenter  {

    @NonNull private final AccueilContract.View mView;
    @Inject RequestInterface request;

    public AccueilPresenter(@NonNull AccueilContract.View mView) {
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
                        Log.d("Accueil Popular error", e.getMessage());
                    }

                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        mView.showPopular(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                    }
                });
    }

    @Override
    public void loadNowPlaying() {
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
                });
    }

    @Override
    public void loadUpcoming() {
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
                });
    }

    @Override
    public void loadTopRated() {
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
    public void subscribe(String source) {

    }

    @Override
    public void unsubscribe() {

    }
}
