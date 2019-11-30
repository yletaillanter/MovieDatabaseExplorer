package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import androidx.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;

/**
 * Created by yoannlt on 25/11/2016.
 */

public interface AccueilContract {

    interface View extends BaseView<AccueilContract.Presenter> {
        void showNowPlaying(@NonNull ArrayList<Movie> movies);
        void showUpcoming(@NonNull ArrayList<Movie> movies);
        void showPopular(@NonNull ArrayList<Movie> movies);
        void showTopRated(@NonNull ArrayList<Movie> movies);
        void launchDetailMovie(@NonNull MovieComplete movie);

    }

    interface Presenter extends BasePresenter {
        void loadNowPlaying();
        void loadUpcoming();
        void loadPopular();
        void loadTopRated();
        void getMovieComplete(@NonNull int id);
    }
}
