package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Affirming that all of the UI elements can be interacted with, and display as expected.
 *
 * @author  Shane, Divanno
 * @since   06-21-18
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.login_button)).check(matches(isClickable()));
        onView(withId(R.id.login_button)).check(matches(withText(R.string.Button_Login)));
        onView(withId(R.id.register_button)).check(matches(isClickable()));
        onView(withId(R.id.register_button)).check(matches(withText(R.string.Button_register)));
    }
}
