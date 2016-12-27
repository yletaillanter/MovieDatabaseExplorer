package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest;

import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailContract;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 18/11/2016.
 */

public class TmdbRepositoryDetail {

    @Inject
    RequestInterface request;
    DetailContract.Presenter presenter;

    public TmdbRepositoryDetail(DetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getSimilarMovies() {
        /*
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
                            presenter.setSimilarMovies(new ArrayList<>(Arrays.asList(similarJSONResponse.getResults())));
                        }
                    }
                });
                */
    }

    public interface RequestInterfaceDetail {

        @GET("3/movie/{id}/similar")
        Observable<SimilarJSONResponse> getSimilarMovies(@Path("id") int id);

        @GET("3/movie/{id}/credits")
        Observable<CastPersonJSONResponse> getMovieCredits(@Path("id") int id);

        @GET("3/person/{id}")
        Observable<Person> getPerson(@Path("id") int id);

        @GET("3/movie/{id}")
        Observable<MovieComplete> getMovieById(@Path("id") int id);
    }
}
