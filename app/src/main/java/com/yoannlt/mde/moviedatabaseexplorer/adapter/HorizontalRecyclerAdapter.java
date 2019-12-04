package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 21/06/2016.
 */
public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.ViewHolder> {

    /* List of data */
    private ArrayList<Movie> mDataset;
    private ClickListener clickListener;
    // quand multiple recyclerviews
    private String originRecycler;

    /* Constructor */
    public HorizontalRecyclerAdapter( ArrayList<Movie> movies, String originRecycler) {
        this.mDataset = movies;

        if (originRecycler != null) {
            this.originRecycler = originRecycler;
        }
    }

    @Override
    public HorizontalRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horizontal_similar, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        // Get the movie
        Movie movie = mDataset.get(position);

        if (movie.getPoster_path() != null) {
            Picasso.get().load("http://image.tmdb.org/t/p/w300" + movie.getPoster_path()).fit().into(holder.poster);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.similar_poster) ImageView poster;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v, getLayoutPosition(), originRecycler);
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /* Add an item to the dataList */
    public void add(int position, Movie movie) {
        mDataset.add(position, movie);
        notifyItemInserted(position);
    }

    /* Remove an item to the dataList */
    public void remove(int id) {
        int position = mDataset.indexOf(id);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Replace the data list
    public void replace(ArrayList<Movie> newMovieList) {
        this.mDataset = newMovieList;
    }

    public ArrayList<Movie> getmDataset() {
        return mDataset;
    }

    public String getOriginRecycler() {
        return originRecycler;
    }

    public void setOriginRecycler(String originRecycler) {
        this.originRecycler = originRecycler;
    }
}
