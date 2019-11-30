package com.yoannlt.mde.moviedatabaseexplorer;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by yoannlt on 15/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ActivitySearchTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testClickRecyclerView() {

        onView(withId(R.id.search_input)).perform(typeText("fight club"),closeSoftKeyboard());
/*
        onView(withId(R.id.card_recycler_view)).perform(RecyclerViewActions.scrollToPosition(3));

        onView(withId(R.id.card_recycler_view)).perform(RecyclerViewActions.scrollToPosition(5));

        onView(withId(R.id.card_recycler_view)).perform(RecyclerViewActions.scrollToPosition(1));
*/
        onView(withId(R.id.card_recycler_view)).perform(actionOnItemAtPosition(2,click()));

        //onView(withId(R.id.original_title)).check(matches(withText("Fight club")));

        //onView(withId(R.id.contentFrameDetail)).check(matches(isDisplayed()));
    }
}
