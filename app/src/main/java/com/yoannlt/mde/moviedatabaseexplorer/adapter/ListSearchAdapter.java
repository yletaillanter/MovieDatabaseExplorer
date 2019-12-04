package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 16/06/2016.
 */
public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder> {

    private ArrayList<Movie> mDataset;
    private ClickListener clickListener;

    /* Constructor */
    public ListSearchAdapter(ArrayList<Movie> movies) {
        this.mDataset = movies;
    }

    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        Log.d(getClass().getName(), "YLT: " + position);

        final int MIN_SIZE_OVERVIEW = 0;
        final int MAX_SIZE_OVERVIEW = 150;
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

        // Get the movie
        Movie movie = mDataset.get(position);

        if (movie.getPoster_path() != null) {
            Picasso.get().load(BASE_IMAGE_URL + movie.getPoster_path()).fit().into(holder.poster);
        }

        if (holder.title != null)
            holder.title.setText(movie.getTitle());

        if (holder.note != null)
            holder.note.setText(movie.getVote_average().toString());

        if (mDataset.get(position).getOverview().length() > MAX_SIZE_OVERVIEW) {
            //Get the first 200 Character of the Overview
            // Set the overview to the view
            if (holder.overview != null) {
                holder.overview.setText(movie.getOverview().substring(MIN_SIZE_OVERVIEW, MAX_SIZE_OVERVIEW) + "[...]");
            }
        } else {
            holder.overview.setText(movie.getOverview());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card) CardView card;
        @Nullable @BindView(R.id.title) TextView title;
        @Nullable @BindView(R.id.overview) TextView overview;
        @Nullable @BindView(R.id.note) TextView note;
        @BindView(R.id.poster) ImageView poster;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v, getLayoutPosition(), ListSearchAdapter.class.getSimpleName());
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

    /* Remove an item from the dataList */
    public void remove(int id) {
        int position = mDataset.indexOf(id);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    /* Replace dataList with new list */
    public void replace(ArrayList<Movie> newMovieList) {
        this.mDataset.clear();
        this.mDataset = newMovieList;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getmDataset() {
        return mDataset;
    }
}
