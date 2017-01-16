package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull private DetailContract.View mView;
    @Inject RequestInterface request;
    private CompositeSubscription compositeSubscription;

    private MovieComplete movie;

    public DetailPresenter(@NonNull DetailContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadSimilar(@NonNull int movieId) {
        compositeSubscription.add(
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
                    })
        );
    }

    public void setSimilar(){

    }

    @Override
    public void loadCredits(@NonNull int movieId) {
        compositeSubscription.add(
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
                })
        );
    }

    @Override
    public void loadPerson(@NonNull int id) {
        compositeSubscription.add(
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
                    })
        );
    }

    @Override
    public void loadCompleteMovie(@NonNull int movieId) {
        compositeSubscription.add(
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
                    })
        );
    }

    @Override
    public void subscribe(@Nullable String source) {
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.clear();
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
