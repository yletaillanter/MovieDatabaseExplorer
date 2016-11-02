package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yoannlt on 27/10/2016.
 */

public class AdvancedSearchFragment extends Fragment implements AdvancedSearchContract.View {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(AdvancedSearchContract.Presenter presenter) {

    }
}
