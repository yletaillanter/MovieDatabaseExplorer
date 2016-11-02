package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPersonPresenter implements DetailPersonContract.Presenter{

    @NonNull
    private DetailPersonContract.View mView;

    private final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";
    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";

    private Retrofit retrofit;
    private RequestInterface request;
    private OkHttpClient okHttpClient2;

    public DetailPersonPresenter(@NonNull DetailPersonContract.View mView) {
        this.mView = mView;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();

                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).addInterceptor(logging).build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
    }

    @Override
    public void loadOtherMovies(@NonNull int id) {
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
                });
    }

    @Override
    public void loadMovieComplete(@NonNull int id) {
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
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
