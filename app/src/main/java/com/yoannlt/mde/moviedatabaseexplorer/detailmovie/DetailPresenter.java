package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

    @NonNull
    private DetailContract.View mView;

    private final String BASE_URL_TMDB = "https://api.themoviedb.org/3/";
    private final String HORIZONTAL_RECYCLER_ADAPTER = "HorizontalRecyclerAdapter";
    private final String CASTING_RECYCLER_ADAPTER = "CastingRecyclerAdapter";
    private final String API_KEY_PARAM = "api_key";
    private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";

    private Retrofit retrofit;
    private RequestInterface request;
    private OkHttpClient okHttpClient2;

    public DetailPresenter(@NonNull DetailContract.View mView) {
        this.mView = mView;

        // Init interceptor retrofit + rest interface
        okHttpClient2 = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        }).build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient2)
                .baseUrl(BASE_URL_TMDB)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        request = retrofit.create(RequestInterface.class);
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
                            //Log.d("Similar Response : ", similarMovies.toString());
                            //adapter.replace(similarMovies);
                            //adapter.notifyDataSetChanged();
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
                        //Log.d("Retrofit Response : ", castPersons.toString());
                        //adapterCasting.replace(castPersons);
                        //adapterCasting.notifyDataSetChanged();
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
}
