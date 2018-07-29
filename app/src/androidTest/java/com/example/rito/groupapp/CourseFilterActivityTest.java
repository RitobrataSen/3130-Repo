package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.CursorMatchers.withRowString;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.hasEntry;

/**
 * This class ensures that the functionality for viewing the courses by terms
 * Ensures that the list is properly populated
 *
 * @author Ritobrata Sen, Gobii Vivagananda, Yuhao Hu
 * @DateComplete 10 July 2018
 */


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CourseFilterActivityTest {
	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void testCourseFilter() throws InterruptedException {
		//login to app
		onView(withId(R.id.login_button)).perform(click());
		onView(withId(R.id.user_email)).perform(typeText("ea2"));
		onView(withId(R.id.user_pw)).perform(typeText("pw2"));
		onView(withId(R.id.login_submit_button)).check(matches(isClickable()));
		onView(withId(R.id.login_submit_button)).perform(click());

		//click view courses from menu
		Thread.sleep(1000);
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText("View Courses")).perform(click());

		//click first term
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click first subject
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click first course
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click core
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click first crn
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click cancel from dialog
		Thread.sleep(1000);
		onView(withId(R.id.dialogButtonCancel)).check(matches(isClickable()));
		onView(withId(R.id.dialogButtonCancel)).perform(click());
		Thread.sleep(1000);

		//click first crn again
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click ok from dialog
		Thread.sleep(1000);
		onView(withId(R.id.dialogButtonOK)).check(matches(isClickable()));
		onView(withId(R.id.dialogButtonOK)).perform(click());
		Thread.sleep(1000);

		//click bottom navi - back
		onView(withId(R.id.navigation_back)).check(matches(isClickable()));
		onView(withId(R.id.navigation_back)).perform(click());

		//click bottom navi - selection
		onView(withId(R.id.navigation_list)).check(matches(isClickable()));
		onView(withId(R.id.navigation_list)).perform(click());

		//click first selection
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click bottom navi - done
		onView(withId(R.id.navigation_done)).check(matches(isClickable()));
		onView(withId(R.id.navigation_done)).perform(click());

		//click cancel from dialog
		Thread.sleep(1000);
		onView(withId(R.id.popUpMsgButtonCancel)).check(matches(isClickable()));
		onView(withId(R.id.popUpMsgButtonCancel)).perform(click());
		Thread.sleep(1000);

		//click bottom navi - done
		onView(withId(R.id.navigation_done)).check(matches(isClickable()));
		onView(withId(R.id.navigation_done)).perform(click());

		//click ok from dialog
		Thread.sleep(1000);
		onView(withId(R.id.popUpMsgButtonOK)).check(matches(isClickable()));
		onView(withId(R.id.popUpMsgButtonOK)).perform(click());
		Thread.sleep(1000);

		//click bottom navi - reset
		onView(withId(R.id.navigation_reset)).check(matches(isClickable()));
		onView(withId(R.id.navigation_list)).perform(click());
	}
}