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

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPresenter implements DetailContract.Presenter {

    @NonNull private DetailContract.View mView;
    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;

    private MovieComplete movie;

    public DetailPresenter(@NonNull DetailContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadSimilar(@NonNull int movieId) {
        compositeDisposable.add(
            request.getSimilarMovies(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<SimilarJSONResponse>() {
                        @Override
                        public void onComplete() {

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
        compositeDisposable.add(
            request.getMovieCredits(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CastPersonJSONResponse>() {
                    @Override
                    public void onComplete() {

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
        compositeDisposable.add(
            request.getPerson(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<Person>() {
                        @Override
                        public void onComplete() {

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
        compositeDisposable.add(
            request.getMovieById(movieId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<MovieComplete>() {
                        @Override
                        public void onComplete() {
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
        compositeDisposable.clear();
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
