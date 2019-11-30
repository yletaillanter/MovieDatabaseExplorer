package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

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
