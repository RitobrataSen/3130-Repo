package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

/**
 * CalendarViewTest ensures that the functions within the Calendar activity work properly.
 * Both displayingCourses and populating a list with textviews are tested.
 *
 * @author  Dryden and Shane
 * @since   2018-07-06
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalendarViewTest {

    @Rule
    public ActivityTestRule<CalendarView> mActivityTestRule = new ActivityTestRule<>(CalendarView.class);

    @Test
    public void calendarViewTest() throws InterruptedException {

        Course_Schedule c = new Course_Schedule("Error", "User Failed to Login",
                "", "", "", "", "",
                "", "", "", "", "", "");
        c.setStartTime("0:00");
        c.setEndTime("0:00");
        onView(withId(R.id.m_body)).check(matches(withText(c.getCourse_name()+"\n"+c.getCourse_code()+
                "\nTime:"+c.getStartTime()+"-"+c.getEndTime())));
    }
    @Test
    public void calendarListTest() {
        for(int i=0; i < mActivityTestRule.getActivity().getCourseListSize(); i++){
            // If populateTextViewLists did not succeed then this is crash, as null.getText will not work.
            if(i+1 < mActivityTestRule.getActivity().getCourseListSize()){
                assertTrue(mActivityTestRule.getActivity().monday[i+1].getText().equals(""));
            }
            assertTrue(mActivityTestRule.getActivity().tuesday[i].getText().equals(""));
            assertTrue(mActivityTestRule.getActivity().wednesday[i].getText().equals(""));
            assertTrue(mActivityTestRule.getActivity().thursday[i].getText().equals(""));
            assertTrue(mActivityTestRule.getActivity().friday[i].getText().equals(""));
        }
    }
}
