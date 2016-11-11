package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

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
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull private DetailContract.View mView;
    @Inject RequestInterface request;

    private MovieComplete movie;

    public DetailPresenter(@NonNull DetailContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
    }

    @Override
    public void loadSimilar(@NonNull int movieId) {
        request.getSimilarMovies(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimilarJSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SimilarJSONResponse similarJSONResponse) {
                        if(similarJSONResponse != null && similarJSONResponse.count() > 1) {
                            mView.setSimilarMovies(new ArrayList<>(Arrays.asList(similarJSONResponse.getResults())));
                        }
                    }
                });
    }

    @Override
    public void loadCredits(@NonNull int movieId) {
        request.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CastPersonJSONResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CastPersonJSONResponse castPersonJSONResponse) {
                        mView.setCasting(new ArrayList<>(Arrays.asList(castPersonJSONResponse.getCast())));
                    }
                });
    }

    @Override
    public void loadPerson(@NonNull int id) {
        request.getPerson(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Person>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Person personResult) {
                        mView.setPerson(personResult);
                        mView.showDetailPerson(personResult);
                    }
                });
    }

    @Override
    public void loadCompleteMovie(@NonNull int movieId) {
        request.getMovieById(movieId)
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
                    public void onNext(MovieComplete movieComplete) {
                        mView.setCompleteMovie(movieComplete);
                        mView.showDetailMovie(movieComplete);
                    }
                });
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void setCompleteMovie(MovieComplete movie){
        this.movie = movie;
    }

    @Override
    public MovieComplete getMovieFromActivityCallback(){
        return movie;
    }
}
