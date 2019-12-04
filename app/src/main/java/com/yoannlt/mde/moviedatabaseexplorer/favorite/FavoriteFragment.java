package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.app.ActivityOptions;
import android.content.Context;
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

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 05/11/2016.
 */

public class FavoriteFragment extends Fragment implements FavoriteContract.View, ClickListener {

    private final String LOG_TAG = getClass().getName();

    @BindView(R.id.favorite_recyclerview) RecyclerView recyclerView;
    private FavoriteContract.Presenter presenter;
    private ListSearchAdapter adapter;
    private Context context;

    public FavoriteFragment() {}

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        if (getActivity() != null && getActivity().getApplicationContext() != null)
            context = getActivity().getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView");

        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, root);
        initRecyclerView();

        return root;
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

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        Log.d(LOG_TAG, "setPresenter: " + presenter.toString());

        this.presenter = presenter;
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        Log.d(LOG_TAG, "itemClicked: " + position);

        presenter.getMovieComplete(adapter.getmDataset().get(position).getId());
    }

    @Override
    public void showFavorites(@NotNull ArrayList<Movie> movies) {
        Log.d(LOG_TAG, "showFavorites: " + movies.toString());

        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void launchDetailMovie(@NonNull MovieComplete movieComplete) {
        Log.d(LOG_TAG, "launchDetailMovie: " + movieComplete.toString());

        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("movie", movieComplete);
        startActivity(intent, bundle);
    }

    private void initRecyclerView() {
        Log.d(LOG_TAG, "initRecyclerView");

        adapter = new ListSearchAdapter(new ArrayList<>());
        adapter.setClickListener(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
