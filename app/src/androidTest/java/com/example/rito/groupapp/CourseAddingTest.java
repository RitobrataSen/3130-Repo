package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CourseAddingTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void courseAddingTest(){
        //navigate through login into add course by crn
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.user_email)).perform(typeText("ea2"));
        onView(withId(R.id.user_pw)).perform(typeText("pw2"));

        //submit forms
        onView(withId(R.id.login_submit_button)).perform(click());

        //navigate with menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add CRN")).perform(click());

        //button
        onView(withId(R.id.drop)).check(matches(isClickable()));
        onView(withId(R.id.drop)).check(matches(withText("DROP")));
        onView(withId(R.id.add)).check(matches(isClickable()));
        onView(withId(R.id.add)).check(matches(withText("ADD")));

        //spinner
        onView(withId(R.id.term)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.term)).check(matches(withSpinnerText(containsString("2018 Summer"))));

        //text view
        onView(withId(R.id.crn)).perform(typeText("11623"));

    }
}
