package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yoannlt on 26/10/2016.
 */
public class DetailPersonPresenter implements DetailPersonContract.Presenter{

    @NonNull
    private DetailPersonContract.View mView;

    @Inject RequestInterface request;

    private CompositeDisposable compositeDisposable;

    public DetailPersonPresenter(@NonNull DetailPersonContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);

        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadOtherMovies(@NonNull int id) {

        compositeDisposable.add(
            request.getOtherMovies(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<PersonCreditsJSONResponse>() {
                        @Override public void onComplete() {}

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

        compositeDisposable.add(
            request.getMovieById(id)
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
        compositeDisposable.clear();
    }
}
