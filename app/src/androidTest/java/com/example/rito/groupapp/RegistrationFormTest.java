/*
Test class for Registration activity.
Authour: Yuze Divannno
Last Modified: July 6th, 2018
 */
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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegistrationFormTest {

    @Rule
    public ActivityTestRule<Registration_Form> mActivityTestRule = new ActivityTestRule<>(Registration_Form.class);


    @Test
    public void RegistrationTest() {
        onView(withId(R.id.submit_button)).check(matches(isClickable()));
        onView(withId(R.id.textView)).check(matches(withText(String.valueOf("Registration"))));
        onView(withId(R.id.submit_button)).check(matches(withText(String.valueOf("SUBMIT"))));
    }
}

