package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //onView clickable button code from stackoverflow user Ads, button with text code from user Ezequiel García:
    //https://stackoverflow.com/questions/32906881/checking-if-a-button-is-clickable-in-espresso-test-android-studio#32914433
    @Test
    public void mainActivityTest() {
        onView(withId(R.id.login_button)).check(matches(isClickable()));
        onView(withId(R.id.login_button)).check(matches(withText(R.string.Button_Login)));
        onView(withId(R.id.register_button)).check(matches(isClickable()));
        onView(withId(R.id.register_button)).check(matches(withText(R.string.Button_register)));
    }
}
