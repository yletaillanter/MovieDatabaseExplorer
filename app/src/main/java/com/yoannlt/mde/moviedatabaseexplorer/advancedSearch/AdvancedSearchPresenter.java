package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SearchPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 27/10/2016.
 */

public class AdvancedSearchPresenter implements AdvancedSearchContract.Presenter {

    private AdvancedSearchContract.View mView;
    private AdvancedSearchContract.DialogView mDialogView;
    @Inject RequestInterface request;

    public AdvancedSearchPresenter(AdvancedSearchContract.View mView) {
        this.mView = mView;
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
    }

    @Override
    public void discoverMovie() {
        mView.showResults();
    }


    //TODO : create request and return object type
    //@Override
    public void searchActor(String query){

        request.searchPerson(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchPersonJSONResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SearchPersonJSONResponse movieResponse) {
                        mDialogView.showResults(new ArrayList<Person>(Arrays.asList(movieResponse.getResults())));
                    }
                });

    }

    @Override
    public void subscribe(String source) {

    }

    @Override
    public void unsubscribe() {

    }

    public void setmDialogView(AdvancedSearchContract.DialogView mDialogView) {
        this.mDialogView = mDialogView;
    }

    @Override
    public void setDialogView(AdvancedSearchContract.DialogView dialogView) {
        this.mDialogView = dialogView;
    }
}
