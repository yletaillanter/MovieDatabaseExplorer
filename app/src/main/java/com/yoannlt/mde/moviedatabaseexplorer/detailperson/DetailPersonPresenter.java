package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPersonPresenter implements DetailPersonContract.Presenter{

    @NonNull
    private DetailPersonContract.View mView;

    @Inject RequestInterface request;

    private CompositeSubscription compositeSubscription;

    public DetailPersonPresenter(@NonNull DetailPersonContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);

        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void loadOtherMovies(@NonNull int id) {

        compositeSubscription.add(
            request.getOtherMovies(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PersonCreditsJSONResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(PersonCreditsJSONResponse personCreditsJSONResponse) {
                            mView.setOtherMovies(new ArrayList<>(Arrays.asList(personCreditsJSONResponse.getCast())));
                        }
                    })
        );

    }

    @Override
    public void loadMovieComplete(@NonNull int id) {

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
                        public void onNext(MovieComplete movieComplete) {
                            mView.setMovieComplete(movieComplete);
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
}
