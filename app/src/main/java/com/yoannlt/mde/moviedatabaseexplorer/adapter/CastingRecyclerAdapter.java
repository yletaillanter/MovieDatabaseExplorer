package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 22/06/2016.
 */
public class CastingRecyclerAdapter extends RecyclerView.Adapter<CastingRecyclerAdapter.ViewHolder> {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    /* List of data */
    private ArrayList<CastPerson> mDataset = new ArrayList<CastPerson>();
    private Context context;
    private ClickListener clickListener;
    private CastPerson person;

    /* Constructor */
    public CastingRecyclerAdapter(Context context, ArrayList<CastPerson> persons) {
        this.context = context;
        this.mDataset = persons;
    }

    @Override
    public CastingRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horizontal_casting, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the movie
        person = mDataset.get(position);

        if (person.getProfile_path() != null) {
            Picasso.with(context).load(BASE_IMAGE_URL + person.getProfile_path()).into(holder.picture);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.person_picture) ImageView picture;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v,getPosition(),CastingRecyclerAdapter.class.getSimpleName());
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /* Add an item to the dataList */
    public void add(int position, CastPerson person) {
        mDataset.add(position, person);
        notifyItemInserted(position);
    }

    /* Remove an item to the dataList */
    public void remove(int id) {
        int position = mDataset.indexOf(id);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Replace the data list
    public void replace(ArrayList<CastPerson> newMovieList) {
        this.mDataset = newMovieList;
    }

    public ArrayList<CastPerson> getmDataset() {
        return mDataset;
    }
}
