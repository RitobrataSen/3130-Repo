package com.example.rito.groupapp;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * This is an espresso test to invoke the password reset process. It provides a valid pre-specified
 * email, navigates back to the login page, and uses the new temporary password to demonstrate
 * the updated login credentials. Once logged in, the password is reset to its initial value
 * so that the database entry is not altered for this test.
 *
 * An email is sent from 3130.group10@gmail.com to itself, to provide the temporary password
 * to the user. In a commercial implementation, our team would maintain a similar email server
 * to send the sensitive recovery information to the email address provided. Since there are no active
 * emails in the database currently, for the time being we send the emails to ourselves to
 * demonstrate functionality.
 *
 * @since 07-27-18
 * @author Shane, Gobii
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecoveryEmailActivityTest {

    @Rule
    public ActivityTestRule<RecoveryEmailActivity> reaActivityTestRule = new ActivityTestRule<>(RecoveryEmailActivity.class);

    @Test
    public void recoveryEmailActivityTest() {
        onView(withId(R.id.send_email)).check(matches(isClickable()));
        onView(withId(R.id.return_to_MCL)).check(matches(isClickable()));

        onView(withId(R.id.user_email_recovery)).perform(typeText("Email_Address3"));
        onView(withId(R.id.send_email)).perform(click());
        String tempPassword = RecoveryEmailActivity.tempPassword;

        onView(withId(R.id.return_to_MCL)).perform(click());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.user_email)).perform(typeText("Email_Address3"));
        onView(withId(R.id.user_pw)).perform(typeText(tempPassword));
        onView(withId(R.id.login_submit_button)).perform(click());

        //The only purpose of this code block is to reset the password to its initial value
        final Database dbRefStudent = new Database();
        Query valid_email_query = dbRefStudent.getDbRef().child("STUDENT");
        valid_email_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot student : dataSnapshot.getChildren()) {
                        User currentUser = (User) student.getValue(User.class);
                        if (currentUser.getEmail().equals("Email_Address3")) {
                            User resetUser = currentUser;
                            resetUser.setPassword("pw3");
                            dbRefStudent.updateUser(currentUser, resetUser);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}


