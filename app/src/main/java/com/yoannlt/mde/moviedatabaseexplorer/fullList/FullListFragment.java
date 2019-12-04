package com.yoannlt.mde.moviedatabaseexplorer.fullList;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 24/10/2016.
 */

public class FullListFragment extends Fragment implements FullListContract.View, ClickListener {

    private final String LOG_TAG = getClass().getSimpleName();
    @BindView(R.id.popular_recyclerview) RecyclerView recyclerView;
    private FullListContract.Presenter presenter;
    private ListSearchAdapter adapter;
    public String from;
    private FragmentActivity activity;
    @BindBool(R.bool.is_720) boolean is_720;


    public FullListFragment() {
    }

    public static FullListFragment newInstance() {
        return new FullListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null)
            activity = getActivity();

        if (activity.getIntent() != null)
            from = activity.getIntent().getStringExtra(ActivityUtils.FROM);

        adapter = new ListSearchAdapter(new ArrayList<>());
        adapter.setClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(from);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(FullListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_full_list, container, false);
        ButterKnife.bind(this, root);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void showMovieList(ArrayList<Movie> movies) {
        Log.d(LOG_TAG, movies.toString());
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        presenter.getMovieComplete(adapter.getmDataset().get(position).getId());
    }

    @Override
    public void launchDetailMovie(@NonNull MovieComplete movie) {

        // Si petit écran on lauch l'activity normalement
        if(!is_720) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
            Intent i = new Intent(activity.getApplicationContext(), DetailActivity.class);
            i.putExtra("movie", movie);
            startActivity(i,bundle);
        } else { // Sinon on laisse l'activité charger le deuxième fragment
            ((FullListFragment.Callback) activity).onItemSelected(movie);
        }
    }

    @Override
    public void startAdvancedSearch() {
        HashMap<String, String> queries = new HashMap<String, String>();

        Bundle bundle = activity.getIntent().getExtras();

        if (bundle != null) {
            queries = (HashMap<String, String>) bundle.getSerializable("hashmap");

            if (queries != null)
                presenter.loadAdvancedSearch(queries);
        }
    }

    public interface Callback {
        /**
         * PopularFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(MovieComplete movie);
    }
}
