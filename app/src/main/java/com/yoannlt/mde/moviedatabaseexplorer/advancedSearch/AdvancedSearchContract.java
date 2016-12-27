package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailContract;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yoannlt on 27/10/2016.
 */

public class AdvancedSearchContract {
    interface View extends BaseView<Presenter> {
        void showResults();
    }

    interface DialogView extends BaseView<Presenter> {
        void showResults(ArrayList<Person> persons);
    }

    interface Presenter extends BasePresenter {
        void discoverMovie();
        void searchActor(String query);
        void setDialogView(DialogView dialogView);
    }
}
