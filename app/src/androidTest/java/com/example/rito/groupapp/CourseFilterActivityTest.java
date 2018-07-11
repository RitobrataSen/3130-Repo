package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.CursorMatchers.withRowString;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasEntry;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class CourseFilterActivityTest {
	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

	@Test
	public void testCourseFilter() throws InterruptedException {
		onView(withId(R.id.login_button)).perform(click());
		onView(withId(R.id.user_number)).perform(typeText("ea2"));
		onView(withId(R.id.user_pw)).perform(typeText("pw2"));
		onView(withId(R.id.login_submit_button)).perform(click());

		Thread.sleep(1000);
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText("View Courses")).perform(click());

		onView(withId(R.id.navigation_back)).check(matches(isClickable()));
		onView(withId(R.id.navigation_back)).perform(click());

		onView(withId(R.id.navigation_list)).check(matches(isClickable()));
		onView(withId(R.id.navigation_list)).perform(click());

		onView(withId(R.id.navigation_reset)).check(matches(isClickable()));
		onView(withId(R.id.navigation_list)).perform(click());

	}
}