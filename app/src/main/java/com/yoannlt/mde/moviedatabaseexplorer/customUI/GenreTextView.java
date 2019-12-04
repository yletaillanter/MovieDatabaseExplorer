package com.yoannlt.mde.moviedatabaseexplorer.customUI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;

/**
 * Created by yoannlt on 11/12/2016.
 */

public class GenreTextView extends AppCompatTextView {

    public GenreTextView(Context context) {
        super(context);
    }

    public GenreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setBackground(getResources().getDrawable(R.drawable.genre_box_deselect));
    }

    @Override
    public Typeface getTypeface() {
        return super.getTypeface();
    }
}
