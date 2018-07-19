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

//import android.test.suitebuilder.annotation.LargeTest;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ViewRemoveCourseRegistrationActivityTest {
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
		onView(withText("My Courses")).perform(click());
		Thread.sleep(1000);

		onView(withId(R.id.navigation_back)).check(matches(isClickable()));
		onView(withId(R.id.navigation_back)).perform(click());

		onView(withId(R.id.navigation_deregister)).check(matches(isClickable()));
		onView(withId(R.id.navigation_deregister)).perform(click());

	}
}