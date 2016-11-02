package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailContract {

    interface View extends BaseView<Presenter> {
        void setSimilarMovies(@NonNull ArrayList<Movie> movies);
        void setCasting(@NonNull ArrayList<CastPerson> cast);
        void setPerson(@NonNull Person person);
        void setCompleteMovie(@NonNull MovieComplete movie);
        void showDetailPerson(@NonNull Person person);
        void showDetailMovie(@NonNull MovieComplete movie);

    }

    interface Presenter extends BasePresenter {
        void loadSimilar(@NonNull int movieId);
        void loadCredits(@NonNull int movieId);
        void loadPerson(@NonNull int id);
        void loadCompleteMovie(@NonNull int movieId);
    }
}
