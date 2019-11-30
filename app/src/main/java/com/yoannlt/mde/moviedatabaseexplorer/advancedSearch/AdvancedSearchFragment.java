package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ActorSearchListAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.customUI.GenreTextView;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yoannlt on 27/10/2016.
 */

public class AdvancedSearchFragment extends Fragment implements AdvancedSearchContract.View {

    private final String LOG_TAG = getClass().getSimpleName();
    @BindView(R.id.spinner) MaterialSpinner spinner_notation;
    @BindView(R.id.add_actor_button) ImageView addActorButton;

    @BindView(R.id.recycler_actor) RecyclerView recyclerViewActors;
    private ActorSearchListAdapter adapter;

    //genre
    @BindView(R.id.genre_action) TextView action;
    @BindView(R.id.genre_adventure) TextView adventure;
    @BindView(R.id.genre_animation) TextView animation;
    @BindView(R.id.genre_comedy) TextView comedy;
    @BindView(R.id.genre_crime) TextView crime;
    @BindView(R.id.genre_documentary) TextView documentary;
    @BindView(R.id.genre_drama) TextView drama;
    @BindView(R.id.genre_family) TextView family;
    @BindView(R.id.genre_fantasy) TextView fantasy;
    @BindView(R.id.genre_history) TextView history;
    @BindView(R.id.genre_horror) TextView horror;
    @BindView(R.id.genre_music) TextView music;
    @BindView(R.id.genre_mystery) TextView mystery;
    @BindView(R.id.genre_romance) TextView romance;
    @BindView(R.id.genre_sf) TextView sf;
    @BindView(R.id.genre_tv) TextView tv;
    @BindView(R.id.genre_thriller) TextView thriller;
    @BindView(R.id.genre_war) TextView war;
    @BindView(R.id.genre_western) GenreTextView western;

    // Date
    @BindView(R.id.edit_year_from) EditText yearFrom;
    @BindView(R.id.edit_year_to) EditText yearTo;

    //Notation
    @BindView(R.id.input_notation) EditText inputNotation;
    private String valueSpinner;

    // Button rechercher
    @BindView(R.id.search_button_advanced) Button searchButton;



    private Animation alphaGenreAnimation;
    private HashMap<String, String> queries;
    private List<String> genres;


    private AdvancedSearchContract.Presenter presenter;

    public AdvancedSearchFragment() {
    }

    public static AdvancedSearchFragment newInstance(){
        return new AdvancedSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queries = new HashMap<String, String>();
        genres = new ArrayList<String>();

        alphaGenreAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation);

        adapter = new ActorSearchListAdapter(getActivity().getApplicationContext(), new ArrayList<Person>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_advanced_search, container, false);
        ButterKnife.bind(this, root);

        recyclerViewActors.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewActors.setLayoutManager(layoutManager);
        recyclerViewActors.setAdapter(adapter);

        // Création du spinner
        spinner_notation.setItems(ActivityUtils.EQUAL, ActivityUtils.GTE_OR_EQUAL, ActivityUtils.LTE_OR_EQUAL);
        spinner_notation.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                valueSpinner = item;
            }
        });
        spinner_notation.setSelectedIndex(0);

        return root;
    }

    @Override
    public void setPresenter(AdvancedSearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick (R.id.add_actor_button)
    public void addActor(){
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        MyActorsSearchDialogFragment myActorsSearchDialogFragment = MyActorsSearchDialogFragment.newInstance();
        myActorsSearchDialogFragment.setPresenter(presenter);
        myActorsSearchDialogFragment.setTargetFragment(this, 1);
        myActorsSearchDialogFragment.show(ft, "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Person person = (Person) data.getParcelableExtra("person");

        adapter.getPersons().add(person);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.genre_action)
    public void clickAction(){
        action.startAnimation(alphaGenreAnimation);

        switch (action.getTypeface().getStyle()){
            case Typeface.NORMAL:
                action.setTypeface(Typeface.create(action.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                action.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("28");
                break;
            case Typeface.BOLD:
                action.setTypeface(Typeface.create(action.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                action.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("28");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_adventure)
    public void clickAdventure(){
        adventure.startAnimation(alphaGenreAnimation);

        switch (adventure.getTypeface().getStyle()){
            case Typeface.NORMAL:
                adventure.setTypeface(Typeface.create(adventure.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                adventure.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("12");
                break;
            case Typeface.BOLD:
                adventure.setTypeface(Typeface.create(adventure.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                adventure.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("12");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_animation)
    public void clickAnimation(){
        animation.startAnimation(alphaGenreAnimation);

        switch (animation.getTypeface().getStyle()){
            case Typeface.NORMAL:
                animation.setTypeface(Typeface.create(animation.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                animation.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("16");
                break;
            case Typeface.BOLD:
                animation.setTypeface(Typeface.create(animation.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                animation.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("16");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_comedy)
    public void clickComedy(){
        comedy.startAnimation(alphaGenreAnimation);

        switch (comedy.getTypeface().getStyle()){
            case Typeface.NORMAL:
                comedy.setTypeface(Typeface.create(comedy.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                comedy.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("35");
                break;
            case Typeface.BOLD:
                comedy.setTypeface(Typeface.create(comedy.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                comedy.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("35");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_crime)
    public void clickCrime(){
        crime.startAnimation(alphaGenreAnimation);

        switch (crime.getTypeface().getStyle()){
            case Typeface.NORMAL:
                crime.setTypeface(Typeface.create(crime.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                crime.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("80");
                break;
            case Typeface.BOLD:
                crime.setTypeface(Typeface.create(crime.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                crime.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("80");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_documentary)
    public void clickDocumentary(){
        documentary.startAnimation(alphaGenreAnimation);

        switch (documentary.getTypeface().getStyle()){
            case Typeface.NORMAL:
                documentary.setTypeface(Typeface.create(documentary.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                documentary.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("99");
                break;
            case Typeface.BOLD:
                documentary.setTypeface(Typeface.create(documentary.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                documentary.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("99");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_drama)
    public void clickDrama(){
        drama.startAnimation(alphaGenreAnimation);

        switch (drama.getTypeface().getStyle()){
            case Typeface.NORMAL:
                drama.setTypeface(Typeface.create(drama.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                drama.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("18");
                break;
            case Typeface.BOLD:
                drama.setTypeface(Typeface.create(drama.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                drama.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("18");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_family)
    public void clickFamily(){
        family.startAnimation(alphaGenreAnimation);

        switch (family.getTypeface().getStyle()){
            case Typeface.NORMAL:
                family.setTypeface(Typeface.create(family.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                family.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("10751");
                break;
            case Typeface.BOLD:
                family.setTypeface(Typeface.create(family.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                family.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("10751");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_fantasy)
    public void clickfantasy(){
        fantasy.startAnimation(alphaGenreAnimation);

        switch (fantasy.getTypeface().getStyle()){
            case Typeface.NORMAL:
                fantasy.setTypeface(Typeface.create(fantasy.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                fantasy.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("14");
                break;
            case Typeface.BOLD:
                fantasy.setTypeface(Typeface.create(fantasy.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                fantasy.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("14");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_history)
    public void clickHistory(){
        history.startAnimation(alphaGenreAnimation);

        switch (history.getTypeface().getStyle()){
            case Typeface.NORMAL:
                history.setTypeface(Typeface.create(history.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                history.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("36");
                break;
            case Typeface.BOLD:
                history.setTypeface(Typeface.create(history.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                history.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("36");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_horror)
    public void clickHorror(){
        horror.startAnimation(alphaGenreAnimation);

        switch (horror.getTypeface().getStyle()){
            case Typeface.NORMAL:
                horror.setTypeface(Typeface.create(horror.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                horror.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("27");
                break;
            case Typeface.BOLD:
                horror.setTypeface(Typeface.create(horror.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                horror.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("27");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_music)
    public void clickMusic(){
        music.startAnimation(alphaGenreAnimation);

        switch (music.getTypeface().getStyle()){
            case Typeface.NORMAL:
                music.setTypeface(Typeface.create(music.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                music.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("10402");
                break;
            case Typeface.BOLD:
                music.setTypeface(Typeface.create(music.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                music.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("10402");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_mystery)
    public void clickMystery(){
        mystery.startAnimation(alphaGenreAnimation);

        switch (mystery.getTypeface().getStyle()){
            case Typeface.NORMAL:
                mystery.setTypeface(Typeface.create(mystery.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                mystery.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("9648");
                break;
            case Typeface.BOLD:
                mystery.setTypeface(Typeface.create(mystery.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                mystery.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("9648");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_romance)
    public void clickRomance(){
        romance.startAnimation(alphaGenreAnimation);

        switch (romance.getTypeface().getStyle()){
            case Typeface.NORMAL:
                romance.setTypeface(Typeface.create(romance.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                romance.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("10749");
                break;
            case Typeface.BOLD:
                romance.setTypeface(Typeface.create(romance.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                romance.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("10749");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_sf)
    public void clickSf(){
        sf.startAnimation(alphaGenreAnimation);

        switch (sf.getTypeface().getStyle()){
            case Typeface.NORMAL:
                sf.setTypeface(Typeface.create(sf.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                sf.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("878");
                break;
            case Typeface.BOLD:
                sf.setTypeface(Typeface.create(sf.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                sf.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("878");
                break;
            default:
                break;
        }
    }


    @OnClick(R.id.genre_tv)
    public void clickTv(){
        tv.startAnimation(alphaGenreAnimation);

        switch (tv.getTypeface().getStyle()){
            case Typeface.NORMAL:
                tv.setTypeface(Typeface.create(tv.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                tv.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("10770");
                break;
            case Typeface.BOLD:
                tv.setTypeface(Typeface.create(mystery.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                tv.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("10770");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_thriller)
    public void clickThriller(){
        thriller.startAnimation(alphaGenreAnimation);

        switch (thriller.getTypeface().getStyle()){
            case Typeface.NORMAL:
                thriller.setTypeface(Typeface.create(thriller.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                thriller.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("53");
                break;
            case Typeface.BOLD:
                thriller.setTypeface(Typeface.create(thriller.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                thriller.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("53");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.genre_war)
    public void clickWar(){
        war.startAnimation(alphaGenreAnimation);

        switch (war.getTypeface().getStyle()){
            case Typeface.NORMAL:
                war.setTypeface(Typeface.create(war.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                war.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("10752");
                break;
            case Typeface.BOLD:
                war.setTypeface(Typeface.create(war.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                war.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("10752");
                break;
            default:
                break;
        }
    }


    @OnClick(R.id.genre_western)
    public void clickWestern(){
        western.startAnimation(alphaGenreAnimation);

        switch (western.getTypeface().getStyle()){
            case Typeface.NORMAL:
                western.setTypeface(Typeface.create(western.getTypeface(), Typeface.BOLD), Typeface.BOLD);
                western.setBackground(getResources().getDrawable(R.drawable.genre_box_select));
                genres.add("37");
                break;
            case Typeface.BOLD:
                western.setTypeface(Typeface.create(western.getTypeface(), Typeface.NORMAL), Typeface.NORMAL);
                western.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
                genres.remove("37");
                break;
            default:
                break;
        }
    }

    @OnClick (R.id.search_button_advanced)
    public void clickSearch(){

        if (presenter != null)
            presenter.discoverMovie();

    }

    @Override
    public void showResults() {

        //Actor ex: Brad pitt 287 ex: Ed Norton 819
        //queries.put("with_cast","287,819");
        if(adapter.getPersons().size()>0){
            StringBuilder builderCast = new StringBuilder();
            for (Person actor : adapter.getPersons()) {
                builderCast.append(actor.getId());
                builderCast.append(",");
            }
            //Removing the last commas
            builderCast.replace(builderCast.length()-1, builderCast.length(), "");

            queries.put("with_cast", builderCast.toString());
        }


        // Genre
        if (genres.size() > 0) {
            StringBuilder builderGenre = new StringBuilder();
            for (String genre : genres) {
                builderGenre.append(genre);
                builderGenre.append(",");
            }

            //Removing the last commas
            builderGenre.replace(builderGenre.length()-1, builderGenre.length(), "");

            queries.put("with_genres", builderGenre.toString());
        }

        //Date
        if(yearTo.getText().length() < 1) {
            queries.put("primary_release_year", yearFrom.getText().toString());
            //Alors date unique
        } else {
            queries.put("primary_release_date.gte", yearFrom.getText().toString());
            queries.put("primary_release_date.lte", yearTo.getText().toString());
            //queries.put("primary_release_year", yearTo.getText().toString());
        }

        //Notation
        if(inputNotation.getText().length() > 0) {
            valueSpinner = "vote_average.gte";
            if (valueSpinner.equals(ActivityUtils.LTE_OR_EQUAL)) {
                valueSpinner = "vote_average.lte";
            }
            queries.put(valueSpinner,inputNotation.getText().toString());
        }

        Bundle extras = new Bundle();
        extras.putString(ActivityUtils.FROM, ActivityUtils.FROM_ADVANCED_SEARCH);
        extras.putSerializable("hashmap",queries);

        Intent intent = new Intent(getActivity(), FullListActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        queries.clear();
    }

    /**
     * "genres": [
     {
     "id": 28,
     "name": "Action"
     },
     {
     "id": 12,
     "name": "Adventure"
     },
     {
     "id": 16,
     "name": "Animation"
     },
     {
     "id": 35,
     "name": "Comedy"
     },
     {
     "id": 80,
     "name": "Crime"
     },
     {
     "id": 99,
     "name": "Documentary"
     },
     {
     "id": 18,
     "name": "Drama"
     },
     {
     "id": 10751,
     "name": "Family"
     },
     {
     "id": 14,
     "name": "Fantasy"
     },
     {
     "id": 36,
     "name": "History"
     },
     {
     "id": 27,
     "name": "Horror"
     },
     {
     "id": 10402,
     "name": "Music"
     },
     {
     "id": 9648,
     "name": "Mystery"
     },
     {
     "id": 10749,
     "name": "Romance"
     },
     {
     "id": 878,
     "name": "Science Fiction"
     },
     {
     "id": 10770,
     "name": "TV Movie"
     },
     {
     "id": 53,
     "name": "Thriller"
     },
     {
     "id": 10752,
     "name": "War"
     },
     {
     "id": 37,
     "name": "Western"
     }
     ]
     */
}
