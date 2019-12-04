package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.HorizontalRecyclerAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yoannlt on 25/11/2016.
 */

public class AccueilFragment extends Fragment implements AccueilContract.View, ClickListener {

    private final String LOG_TAG = getClass().getSimpleName();

    @BindView(R.id.now_playing_recyclerview) RecyclerView recyclerViewNowPlaying;
    @BindView(R.id.upcoming_recyclerview) RecyclerView recyclerViewUpComing;
    @BindView(R.id.popular_recyclerview) RecyclerView recyclerViewPopular;
    @BindView(R.id.toprated_recyclerview) RecyclerView recyclerViewTopRated;

    private HorizontalRecyclerAdapter adapterNowPlaying;
    private HorizontalRecyclerAdapter adapterUpComing;
    private HorizontalRecyclerAdapter adapterPopular;
    private HorizontalRecyclerAdapter adapterTopRated;

    private AccueilContract.Presenter presenter;

    private List<Movie> listNowPlaying;
    private List<Movie> listUpComing;
    private List<Movie> listPopular;
    private List<Movie> listTopRated;

    public AccueilFragment() {}

    public static AccueilFragment newInstance() {
        return new AccueilFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);

        adapterNowPlaying = new HorizontalRecyclerAdapter(new ArrayList<Movie>(), ActivityUtils.FROM_RECYCLER_NOW_PLAYING);
        adapterUpComing = new HorizontalRecyclerAdapter(new ArrayList<Movie>(), ActivityUtils.FROM_RECYCLER_UP_COMING);
        adapterPopular = new HorizontalRecyclerAdapter(new ArrayList<Movie>(), ActivityUtils.FROM_RECYCLER_POPULAR);
        adapterTopRated = new HorizontalRecyclerAdapter(new ArrayList<Movie>(), ActivityUtils.FROM_RECYCLER_TOP_RATED);

        adapterNowPlaying.setClickListener(this);
        adapterUpComing.setClickListener(this);
        adapterPopular.setClickListener(this);
        adapterTopRated.setClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        View root = inflater.inflate(R.layout.fragment_accueil, container, false);
        ButterKnife.bind(this, root);

        recyclerViewNowPlaying.setHasFixedSize(true);
        recyclerViewUpComing.setHasFixedSize(true);
        recyclerViewPopular.setHasFixedSize(true);
        recyclerViewTopRated.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManagerNowPlaying = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerUpComing = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerPopular = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerTopRated = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewNowPlaying.setLayoutManager(layoutManagerNowPlaying);
        recyclerViewUpComing.setLayoutManager(layoutManagerUpComing);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        recyclerViewTopRated.setLayoutManager(layoutManagerTopRated);

        recyclerViewNowPlaying.setAdapter(adapterNowPlaying);
        recyclerViewUpComing.setAdapter(adapterUpComing);
        recyclerViewPopular.setAdapter(adapterPopular);
        recyclerViewTopRated.setAdapter(adapterTopRated);

        return root;
    }

    @Override
    public void showNowPlaying(@NonNull ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "showNowPlaying");

        this.listNowPlaying = movies;
        adapterNowPlaying.replace(movies);
        adapterNowPlaying.notifyDataSetChanged();
    }

    @Override
    public void showUpcoming(@NonNull ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "showUpcoming");

        this.listUpComing = movies;
        adapterUpComing.replace(movies);
        adapterUpComing.notifyDataSetChanged();
    }

    @Override
    public void showPopular(@NonNull ArrayList<Movie> movies) {
        this.listPopular = movies;
        adapterPopular.replace(movies);
        adapterPopular.notifyDataSetChanged();
    }

    @Override
    public void showTopRated(@NonNull ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "showTopRated");

        this.listTopRated = movies;
        adapterTopRated.replace(movies);
        adapterTopRated.notifyDataSetChanged();
    }

    @Override
    public void launchDetailMovie(@NonNull MovieComplete movie) {
        Log.d(LOG_TAG, "launchDetailMovie");

        Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void setPresenter(AccueilContract.Presenter presenter) {
        Log.d(LOG_TAG, "setPresenter");

        this.presenter = presenter;
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        Log.d(LOG_TAG, "itemClicked: " + position);

        if (recycler != null) {
            switch (recycler) {
                case ActivityUtils.FROM_RECYCLER_NOW_PLAYING:
                    presenter.getMovieComplete(listNowPlaying.get(position).getId());
                    break;
                case ActivityUtils.FROM_RECYCLER_UP_COMING:
                    presenter.getMovieComplete(listUpComing.get(position).getId());
                    break;
                case ActivityUtils.FROM_RECYCLER_POPULAR:
                    presenter.getMovieComplete(listPopular.get(position).getId());
                    break;
                case ActivityUtils.FROM_RECYCLER_TOP_RATED:
                    presenter.getMovieComplete(listTopRated.get(position).getId());
                    break;
                default:
                    Toast.makeText(getActivity().getApplicationContext(), "Impossible de voir le detail du film", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.label_nowplaying)
    public void showFullListNowPlaying() {
        Log.d(LOG_TAG, "showFullListNowPlaying");
        Intent intent = new Intent(getActivity().getApplicationContext(), FullListActivity.class);
        intent.putExtra(ActivityUtils.FROM, ActivityUtils.FROM_RECYCLER_NOW_PLAYING);
        startActivity(intent);
    }

    @OnClick(R.id.label_upcoming)
    public void showFullListUpComing() {
        Log.d(LOG_TAG, "showFullListUpComing");

        Intent intent = new Intent(getActivity().getApplicationContext(), FullListActivity.class);
        intent.putExtra(ActivityUtils.FROM, ActivityUtils.FROM_RECYCLER_UP_COMING);
        startActivity(intent);
    }

    @OnClick(R.id.label_popular)
    public void showFullListPopular() {
        Log.d(LOG_TAG, "showFullListPopular");

        Intent intent = new Intent(getActivity().getApplicationContext(), FullListActivity.class);
        intent.putExtra(ActivityUtils.FROM, ActivityUtils.FROM_RECYCLER_POPULAR);
        startActivity(intent);
    }

    @OnClick(R.id.label_toprated)
    public void showFullListTopRated() {
        Log.d(LOG_TAG, "showFullListTopRated");

        Intent intent = new Intent(getActivity().getApplicationContext(), FullListActivity.class);
        intent.putExtra(ActivityUtils.FROM, ActivityUtils.FROM_RECYCLER_TOP_RATED);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");

        super.onResume();
        presenter.subscribe(null);
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "onPause");

        super.onPause();
        presenter.unsubscribe();
    }
}
