package com.example.rito.groupapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CalendarViewTest {

    @Rule
    public ActivityTestRule<CalendarView> mActivityTestRule = new ActivityTestRule<>(CalendarView.class);

    //onView clickable button code from stackoverflow user Ads, button with text code from user Ezequiel García:
    //https://stackoverflow.com/questions/32906881/checking-if-a-button-is-clickable-in-espresso-test-android-studio#32914433
    @Test
    public void calendarViewTest() {
        Courses c = new Courses("CSCI3110", "Computer Science Class", "", "", "", "");
        c.SetStime("3:30");
        c.SetEtime("4:30");
        TextView[] testTextView = mActivityTestRule.getActivity().monday;
        mActivityTestRule.getActivity().displayCourse(testTextView,c);
        onView(withId(R.id.m_body)).check(matches(withText(c.GetCname()+"\n"+c.GetCode()+"\n"+c.GetSt()+"-"+c.GetEt())));
    }
    @Test
    public void calendarListTest() {
        mActivityTestRule.getActivity().populateTextViewLists();
        for(int i=0; i < mActivityTestRule.getActivity().getCourseListSize(); i++){
            // If populateTextViewLists did not succeed then this is crash, as null.getText will not work.
            mActivityTestRule.getActivity().monday[i].getText();
            mActivityTestRule.getActivity().tuesday[i].getText();
            mActivityTestRule.getActivity().wednesday[i].getText();
            mActivityTestRule.getActivity().thursday[i].getText();
            mActivityTestRule.getActivity().friday[i].getText();
        }
    }
}
