package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This espresso test checks to see if a course is actually being added to an user.
 * It ensures that the functionality that enables this works properly.
 *
 * @author Gobii Viviagananda, Shane Mitravitz
 * @DateComplete 28 July 2018
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CourseAddingTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void courseAddingTest(){

        //login to app
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.user_email)).perform(typeText("ea2"));
        onView(withId(R.id.user_pw)).perform(typeText("pw2"));
        onView(withId(R.id.login_submit_button)).perform(click());

        //navigate with menu
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Add Courses")).perform(click());

        //button
        onView(withId(R.id.drop)).check(matches(isClickable()));
        onView(withId(R.id.drop)).check(matches(withText("DROP")));
        onView(withId(R.id.add)).check(matches(isClickable()));
        onView(withId(R.id.add)).check(matches(withText("ADD")));

        //text view
        onView(withId(R.id.crn)).perform(typeText("11623"));

    }
}
