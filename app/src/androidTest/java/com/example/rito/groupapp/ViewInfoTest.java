package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rito.groupapp.ViewUser_Information.View_UserInformation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

/**
 * ViewInfoTest is the espresso test for the View_UserInformation
 *
 * @Author: Ritobrata Sen, Qu Yuze
 * @DateStarted: 16th July 2018
 * @DateEnded: 24th July 2018
 *
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ViewInfoTest {

    @Rule
    public ActivityTestRule<View_UserInformation> mActivityTestRule = new ActivityTestRule<>(View_UserInformation.class);


    @Test
    public void InfoTest() {

        onView(withId(R.id.button2)).check(matches(isClickable()));

        onView(withId(R.id.button2)).check(matches(withText(String.valueOf("Submit"))));

        onView(withId(R.id.textView2)).check(matches(withText(String.valueOf("Email"))));

        onView(withId(R.id.textView2)).check(matches(isDisplayed()));

        onView(withId(R.id.textView3)).check(matches(withText(String.valueOf("Username"))));

        onView(withId(R.id.textView3)).check(matches(isDisplayed()));

        onView(withId(R.id.textView4)).check(matches(withText(String.valueOf("Password"))));

        onView(withId(R.id.textView4)).check(matches(isDisplayed()));

        onView(withId(R.id.textView5)).check(matches(withText(String.valueOf("New Password"))));

        onView(withId(R.id.textView5)).check(matches(isDisplayed()));

        onView(withId(R.id.textView6)).check(matches(withText(String.valueOf("Confirm Password"))));

        onView(withId(R.id.textView6)).check(matches(isDisplayed()));

    }
}