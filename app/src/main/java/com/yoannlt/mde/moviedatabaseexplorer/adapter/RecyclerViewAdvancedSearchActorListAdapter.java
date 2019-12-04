package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 13/12/2016.
 */

public class RecyclerViewAdvancedSearchActorListAdapter extends RecyclerView.Adapter<RecyclerViewAdvancedSearchActorListAdapter.ViewHolder> {

    private ArrayList<Person> mDataset = new ArrayList<Person>();

    private ClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_search_actor) CardView card;
        @Nullable @BindView(R.id.search_actor_name) TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.itemClicked(v,getPosition(), RecyclerViewAdvancedSearchActorListAdapter.class.getSimpleName());
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /* Add an item to the dataList */
    public void add(int position, Person person) {
        mDataset.add(position, person);
        notifyItemInserted(position);
    }

    /* Remove an item to the dataList */
    public void remove(int id) {
        int position = mDataset.indexOf(id);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public RecyclerViewAdvancedSearchActorListAdapter(ArrayList<Person> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public RecyclerViewAdvancedSearchActorListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = mDataset.get(position);
        if (holder.name != null)
            holder.name.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        if(mDataset != null)
            return mDataset.size();
        else
            return 0;
    }

    // Replace the data list
    public void replace(ArrayList<Person> personList) {
        this.mDataset = personList;
    }

    public ArrayList<Person> getmDataset() {
        return mDataset;
    }
}
