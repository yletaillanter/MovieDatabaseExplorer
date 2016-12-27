package com.yoannlt.mde.moviedatabaseexplorer.fullList;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 24/10/2016.
 */

public class FullListPresenter implements FullListContract.Presenter {

    @NonNull private final FullListContract.View mView;
    @Inject RequestInterface request;

    public FullListPresenter(@NonNull FullListContract.View mView) {
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
                        mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
                        mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
                        mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
                        mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
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
    public void initAdvancedSearch() {
        mView.startAdvancedSearch();
    }

    @Override
    public void loadAdvancedSearch(@NonNull HashMap<String, String> queries) {
        request.movieDiscoverTmdb(queries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("AdvancedSearchPresenter", "Complete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AdSearchPresenterError", e.getMessage());
                    }

                    @Override
                    public void onNext(JSONResponse jsonResponse) {
                        Log.d("retrofit next", jsonResponse.toString());
                        mView.showMovieList(new ArrayList<>(Arrays.asList(jsonResponse.getResults())));
                    }
                });
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void subscribe(String source) {
        switch(source) {
            case (ActivityUtils.FROM_RECYCLER_NOW_PLAYING):
                loadNowPlaying();
                break;
            case (ActivityUtils.FROM_RECYCLER_POPULAR):
                loadPopular();
                break;
            case (ActivityUtils.FROM_RECYCLER_TOP_RATED):
                loadTopRated();
                break;
            case (ActivityUtils.FROM_RECYCLER_UP_COMING):
                loadUpcoming();
                break;
            case (ActivityUtils.FROM_ADVANCED_SEARCH):
                initAdvancedSearch();
                break;
        }
    }
}
