package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static org.hamcrest.CoreMatchers.anything;

//import android.test.suitebuilder.annotation.LargeTest;

/**
 * This classes ensures the functionality of the MyCoursesActivity by ensuring that the list is
 * populated with the classes that the user is registered for.
 *
 * @author Gobii Vivagananda, Ritobrata Sen, Yuhao Hu
 * @dateComplete 10 July 2018
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MyCoursesActivityTest {
	@Rule
	public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
	public Database db = new Database();


	@Test
	public void testCourseFilter() throws InterruptedException {

		//login to app
		onView(withId(R.id.login_button)).perform(click());
		onView(withId(R.id.user_number)).perform(typeText("ea2"));
		onView(withId(R.id.user_pw)).perform(typeText("pw2"));
		onView(withId(R.id.login_submit_button)).perform(click());


		//add course to deregister
		db.addRemoveCourse("31202", "Student2", true);

		//click view courses from menu
		Thread.sleep(1000);
		openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
		onView(withText("My Courses")).perform(click());
		Thread.sleep(1000);

		//click first term
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click first crn
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click bottom navi - deregister
		onView(withId(R.id.navigation_deregister)).check(matches(isClickable()));
		onView(withId(R.id.navigation_deregister)).perform(click());

		//click cancel from dialog
		Thread.sleep(1000);
		onView(withId(R.id.popUpMsgButtonCancel)).check(matches(isClickable()));
		onView(withId(R.id.popUpMsgButtonCancel)).perform(click());
		Thread.sleep(1000);

		//click bottom navi - back
		onView(withId(R.id.navigation_back)).check(matches(isClickable()));
		onView(withId(R.id.navigation_back)).perform(click());

		//click first term
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click first crn
		Thread.sleep(1000);
		onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
		Thread.sleep(1000);

		//click bottom navi - deregister
		onView(withId(R.id.navigation_deregister)).check(matches(isClickable()));
		onView(withId(R.id.navigation_deregister)).perform(click());

		//click ok from dialog
		Thread.sleep(1000);
		onView(withId(R.id.popUpMsgButtonOK)).check(matches(isClickable()));
		onView(withId(R.id.popUpMsgButtonOK)).perform(click());
		Thread.sleep(1000);

	}
}