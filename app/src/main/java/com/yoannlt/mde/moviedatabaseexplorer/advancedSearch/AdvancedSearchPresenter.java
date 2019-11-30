package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import androidx.annotation.Nullable;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SearchPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yoannlt on 27/10/2016.
 */

public class AdvancedSearchPresenter implements AdvancedSearchContract.Presenter {

    private AdvancedSearchContract.View mView;
    private AdvancedSearchContract.DialogView mDialogView;
    @Inject RequestInterface request;
    private CompositeDisposable compositeDisposable;


    public AdvancedSearchPresenter(AdvancedSearchContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void discoverMovie() {
        mView.showResults();
    }

    //@Override
    public void searchActor(String query){
        compositeDisposable.add(
            request.searchPerson(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSubscriber<SearchPersonJSONResponse>() {
                        @Override
                        public void onComplete() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(SearchPersonJSONResponse movieResponse) {
                            mDialogView.showResults(new ArrayList<Person>(Arrays.asList(movieResponse.getResults())));
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

    public void setmDialogView(AdvancedSearchContract.DialogView mDialogView) {
        this.mDialogView = mDialogView;
    }

    @Override
    public void setDialogView(AdvancedSearchContract.DialogView dialogView) {
        this.mDialogView = dialogView;
    }
}
