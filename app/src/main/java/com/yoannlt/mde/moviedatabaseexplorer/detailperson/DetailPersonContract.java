package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import androidx.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;

import java.util.ArrayList;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPersonContract {
    interface View extends BaseView<DetailPersonContract.Presenter> {
        void setOtherMovies(@NonNull ArrayList<OtherMoviesFromPerson> movies);
        void setMovieComplete(@NonNull MovieComplete movie);
        void showDetailMovie(@NonNull MovieComplete movie);
    }

    interface Presenter extends BasePresenter {
        void loadOtherMovies(@NonNull int id);
        void loadMovieComplete(@NonNull int id);
    }
}
