package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Image;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 19/10/2016.
 */

public class RecyclerViewGalleryAdapter extends RecyclerView.Adapter<RecyclerViewGalleryAdapter.ViewHolder> {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    private ArrayList<Image> images = new ArrayList<Image>();
    private Context context;
    private ClickListener clickListener;
    private Image image;

    /* Constructor */
    public RecyclerViewGalleryAdapter(Context context, ArrayList<Image> images) {
        this.context = context;
        this.images = images;
    }

    // CREATING THE VIEW
    @Override
    public RecyclerViewGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_inside, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        image = images.get(position);


        // Set l'affichage
        Picasso.with(context).load(BASE_IMAGE_URL + image.getFile_path()).into(holder.poster);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return images.size();
    }


    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.poster_gallery_person) ImageView poster;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
            //v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                //clickListener.itemClicked(v,getPosition());
                Toast.makeText(context, "clique on : "+image.getFile_path(), Toast.LENGTH_SHORT).show();
            }
        }

        /*
        @Override
        public boolean onLongClick(View v) {
            clickListener.itemClickedLong(v,getPosition());
            return true;
        }*/
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // Replace the data list
    public void replace(ArrayList<Image> images) {
        this.images = images;
    }

}
