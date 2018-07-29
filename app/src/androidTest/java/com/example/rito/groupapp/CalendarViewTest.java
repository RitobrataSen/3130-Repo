package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;

import com.example.rito.groupapp.old.Course_Schedule;

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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.anything;

/**
 * CalendarViewTest ensures that the functions within the Calendar activity work properly.
 * Both displayingCourses and populating a list with textviews are tested.
 *`
 * @author  Dryden and Shane
 * @since   2018-07-06
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalendarViewTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void calendarViewTest() throws InterruptedException {
        //login to app
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.user_number)).perform(typeText("ea2"));
        onView(withId(R.id.user_pw)).perform(typeText("pw2"));
        onView(withId(R.id.login_submit_button)).check(matches(isClickable()));
        onView(withId(R.id.login_submit_button)).perform(click());

        //click view courses from menu
        Thread.sleep(1000);
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("My Calendar")).perform(click());

        //click last term
        Thread.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(3).perform(click());
        Thread.sleep(1000);

    }

}
