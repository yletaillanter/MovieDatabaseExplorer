package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

public class DetailPersonActivity extends AppCompatActivity {

    private DetailPersonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);

        DetailPersonFragment fragment = (DetailPersonFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameDetailPerson);
        if (fragment == null) {
            fragment = DetailPersonFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameDetailPerson);
        }

        presenter = new DetailPersonPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
