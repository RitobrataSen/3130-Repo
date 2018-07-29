package com.example.rito.groupapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import com.example.rito.groupapp.ViewUser_Information.View_UserInformation;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import static java.util.Arrays.sort;

/**
 * CalendarView Activity displays all courses that a student is registered for sorted by
 * day. Plans exist for expanding this to short all activities based on start time.
 *
 * @author   Dryden and Shane
 * @completed   2018-07-08
 *
 * @since 2018-07-19
 * @author Ritobrata Sen, Qu Yuze
 * @updated: The an added functionality to the menu was added so that the user can now
 * navigate and view their information.
 *
 * @since 2018-07-28
 * @author Dryden and Yuhao
 * @updated: Created a popup displaying a course's specific information and created a filter to filter courses by term.
 */
public class CalendarView extends AppCompatActivity {


    private Context context = this;
    private int courseListSize = 4;
    private String selectedCourse;
    public TextView monday[] = new TextView[courseListSize];
    public TextView tuesday[] = new TextView[courseListSize];
    public TextView wednesday[] = new TextView[courseListSize];
    public TextView thursday[] = new TextView[courseListSize];
    public TextView friday[] = new TextView[courseListSize];
    public CRN_Data courseList[];
    public ArrayList<CRN_Data> calendarCourses = new ArrayList<CRN_Data>();
    public int counter;
    private Term filterTerm = null;
	private int filterState = 0;
    private Toolbar hdrToolBar;
	private ListView lv;
	private HorizontalScrollView cal;
	private ConstraintLayout conLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_course:
                startActivity(new Intent(CalendarView.this, CourseFilterActivity.class));
                return true;

            case R.id.go_to_calender:
                startActivity(new Intent(CalendarView.this, CalendarView.class));
                return true;

            case R.id.go_to_add_crn:
                startActivity(new Intent(CalendarView.this, CourseRegistration.class));
                return true;

            case R.id.go_to_view_remove_registered:
                startActivity(new Intent(CalendarView.this, MyCoursesActivity.class));
                return true;
            case R.id.view_user_information:
                startActivity(new Intent(CalendarView.this, View_UserInformation.class));

            case R.id.log_out:
                startActivity(new Intent(CalendarView.this, Logout_Activity.class));
                return true;
        }

		return super.onOptionsItemSelected(item);

	}

	private BottomNavigationView
			.OnNavigationItemSelectedListener
			mOnNavigationItemSelectedListener =
			new BottomNavigationView
					.OnNavigationItemSelectedListener() {

				@Override
				public boolean onNavigationItemSelected(@NonNull MenuItem item) {
					int o = 0;
					String t, m;
					switch (item.getItemId()) {
						case R.id.navigation_back:
							getPreviousState();
							return true;

						case R.id.navigation_reset:

							t = "Reset Calendar View";
							m = "Are you sure you want to reset your current Calendar View?";
							o = 0;
							popupMsg(t, m, o);
							return true;

					}

					return false;

				}
			};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_view);
		lv = findViewById(R.id.listView);
		cal = findViewById(R.id.hsv);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(hdrToolBar);

		conLayout = findViewById(R.id.constraint_layout);

		populateTerm();
    }

    public void applyNewConstraints(int v){

		ConstraintSet set = new ConstraintSet();
    	//change toolbar constraints
		set.clone(conLayout);
		set.connect(
				R.id.toolbar, ConstraintSet.BOTTOM,
				v, ConstraintSet.TOP);
		//change bottom navigation view constraints
		set.connect(
				R.id.navigation, ConstraintSet.TOP,
				v, ConstraintSet.BOTTOM);

		set.applyTo(conLayout);

	}

	public void popupMsg(String t, String m, int o){

		// custom dialog
		final Dialog dialog = new Dialog(context);

		dialog.setContentView(R.layout.item_popup_msg);

		TextView title = (TextView) dialog.findViewById(R.id.title);
		TextView line1 = (TextView) dialog.findViewById(R.id.line1);

		title.setText(t);
		line1.setText(m);

		Button popUpMsgButtonCancel = (Button) dialog.findViewById(R.id.popUpMsgButtonCancel);
		popUpMsgButtonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		Button popUpMsgButtonOK = (Button) dialog.findViewById(R.id.popUpMsgButtonOK);

		switch(o){
			case 0:
				popUpMsgButtonOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						populateTerm();
						dialog.dismiss();
					}
				});
				break;
			default:
				popUpMsgButtonOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		}

		dialog.show();
	}

	public void getPreviousState(){
		switch (filterState){
			case 0:
				populateTerm();
				break;
			case 1:
				populateTerm();
				break;
			default:
				populateTerm();
				break;
		}
	}

    public void populateTerm() {
		filterState = 0;
		filterTerm = null;
		lv.setAdapter(null);
		lv.setVisibility(View.VISIBLE);
		cal.setVisibility(View.GONE);
		applyNewConstraints(R.id.listView);

        FirebaseListAdapter<Term> firebaseAdapter;
        Database db = new Database("TERM");
        lv = findViewById(R.id.listView);
        firebaseAdapter = new FirebaseListAdapter<Term>(this, Term.class,
                android.R.layout.simple_list_item_1, db.getDbRef()) {
            @Override
            protected void populateView(View v, Term model, int position) {
                TextView termRow = (TextView)v.findViewById(android.R.id.text1);
                termRow.setText(model.toString());
            }
        };

        lv.setAdapter(firebaseAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Term term = (Term) parent.getItemAtPosition(position);
				createCalendar(term);
            }
        });
    }

    public void createCalendar(Term term){
		filterState = 1;
		filterTerm = term;
		lv.setAdapter(null);
		lv.setVisibility(View.GONE);
		cal.setVisibility(View.VISIBLE);
		applyNewConstraints(R.id.hsv);

    	populateTextViewLists();
		calendarCourses.clear();

		//Recall, this wont work unless a user is signed in.
		if(MainActivity.currentUser != null) {
			courseList = new CRN_Data[MainActivity.currentUser.getRegistration().keySet().toArray().length];
			//calendarCourses = new ArrayList<CRN_Data>();
			for(int i=0; i < MainActivity.currentUser.getRegistration().keySet().toArray().length; i++) {

				String crn = MainActivity.currentUser.getRegistration().keySet().toArray()[i].toString();

				counter = i;
				Database db = new Database("CRN_DATA/" + crn);
				db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						if (dataSnapshot.exists()) {
							CRN_Data curr = (CRN_Data) dataSnapshot.getValue(CRN_Data.class);
							Log.d("debug.print",
									"CUR: " + curr.getTerm_Code() +
											" FT: " + filterTerm.getTerm_code());

							if (curr.getTerm_Code().equals(filterTerm.getTerm_code())){
								Log.d("debug.print","equal");
								appendCRN_Data(curr);
								populateTextViewLists();
								populateCalendar();
							}
						}
						/*
						if(calendarCourses.size() == courseList.length){
							populateCalendar();
						}*/
					}

					@Override
					public void onCancelled(DatabaseError databaseError) {
						Log.d("debug.print", "The read failed: " + databaseError.getCode());
					}
				});
			}
		}
		else{
			CRN_Data c = new CRN_Data();
			c.setCrn("Error");
			c.setCourse_Code("User Failed to Login");
			c.setStart_Time("0:00");
			c.setEnd_Time("0:00");
			displayCourse(monday, c);
		}
	}

	//public boolean markSelection(boolean add){
	public void appendCRN_Data(CRN_Data crn){
		boolean sel = false;
		ArrayList<CRN_Data> newSelectedCourses = new ArrayList<>();

		if (!(crn == null)){
			for (CRN_Data x : calendarCourses) {
				if(!(x.equals(crn))){
					newSelectedCourses.add(x);
				} else {
					sel = true;
				}
			}

			calendarCourses = newSelectedCourses;

			if (!(sel)){
				calendarCourses.add(crn);
			}
		}
	}


    public void populateCalendar(){
        Collections.sort(calendarCourses);
        for(int i = 0; i < calendarCourses.size(); i++) {
            if (calendarCourses.get(i).getDays().get("mon")) {
                displayCourse(monday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("tue")) {
                displayCourse(tuesday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("wed")) {
                displayCourse(wednesday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("thu")) {
                displayCourse(thursday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("fri")) {
                displayCourse(friday, calendarCourses.get(i));
            }
        }
    }

    public void displayCourse(TextView[] selected, CRN_Data course){
        for(int i = 0; i < courseListSize; i++) {
            if(selected[i].getText().length() == 0) {
                selected[i].setText(course.getCrn() + "\n" + course.getCourse_Code() + "\nTime:" + course
                        .getStart_Time() + "-" + course.getEnd_Time());
                selected[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView t = (TextView) view;
                        selectedCourse = "";
                        for(int i=0; i<5; i++){
                            selectedCourse += t.getText().charAt(i);
                        }
                        if(selectedCourse != "") {
                            CRN_Data displayed = new CRN_Data();
                            Database db = new Database("CRN_DATA/" + selectedCourse);
                            db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        CRN_Data curr = (CRN_Data) dataSnapshot.getValue(CRN_Data.class);
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.item_crn_selection_full);
                                        //dialog.setTitle("Title...");

                                        // set the custom dialog components - text, image and button
                                        String []  arr = curr.getToStringArray(1);

                                        TextView title = (TextView) dialog.findViewById(R.id.title);

                                        TextView line1 = (TextView) dialog.findViewById(R.id.line1);
                                        TextView line2 = (TextView) dialog.findViewById(R.id.line2);
                                        TextView line3 = (TextView) dialog.findViewById(R.id.line3);
                                        TextView line4 = (TextView) dialog.findViewById(R.id.line4);
                                        TextView line5 = (TextView) dialog.findViewById(R.id.line5);
                                        TextView line6 = (TextView) dialog.findViewById(R.id.line6);
                                        TextView line7 = (TextView) dialog.findViewById(R.id.line7);
                                        TextView line8 = (TextView) dialog.findViewById(R.id.line8);

                                        title.setText("Class Details");

                                        line1.setText(arr[0]);
                                        line2.setText(arr[1]);
                                        line3.setText(arr[2]);
                                        line4.setText(arr[3]);
                                        line5.setText(arr[4]);
                                        line6.setText(arr[5]);
                                        line7.setText(arr[6]);
                                        line8.setText(arr[7]);

                                        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                        // if button is clicked, close the custom dialog
                                        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
                                        dialogButtonCancel.setVisibility(View.INVISIBLE);
                                        dialog.show();

                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.d("debug.print", "The read failed: " + databaseError.getCode());
                                }
                            });
                        }
                        }
                });
                break;
            }
        }
    }


    // Method fills list of TextViews and clears all old courses
    public void populateTextViewLists(){
        monday[0] = findViewById(R.id.m_body);
        monday[1] = findViewById(R.id.m1_body);
        monday[2] = findViewById(R.id.m2_body);
        monday[3] = findViewById(R.id.m3_body);

        monday[0].setText("");
        monday[1].setText("");
        monday[2].setText("");
        monday[3].setText("");

        tuesday[0] = findViewById(R.id.t_body);
        tuesday[1] = findViewById(R.id.t1_body);
        tuesday[2] = findViewById(R.id.t2_body);
        tuesday[3] = findViewById(R.id.t3_body);

        tuesday[0].setText("");
        tuesday[1].setText("");
        tuesday[2].setText("");
        tuesday[3].setText("");

        wednesday[0] = findViewById(R.id.w_body);
        wednesday[1] = findViewById(R.id.w1_body);
        wednesday[2] = findViewById(R.id.w2_body);
        wednesday[3] = findViewById(R.id.w3_body);

        wednesday[0].setText("");
        wednesday[1].setText("");
        wednesday[2].setText("");
        wednesday[3].setText("");

        thursday[0] = findViewById(R.id.r_body);
        thursday[1] = findViewById(R.id.r1_body);
        thursday[2] = findViewById(R.id.r2_body);
        thursday[3] = findViewById(R.id.r3_body);

        thursday[0].setText("");
        thursday[1].setText("");
        thursday[2].setText("");
        thursday[3].setText("");

        friday[0] = findViewById(R.id.f_body);
        friday[1] = findViewById(R.id.f1_body);
        friday[2] = findViewById(R.id.f2_body);
        friday[3] = findViewById(R.id.f3_body);

        friday[0].setText("");
        friday[1].setText("");
        friday[2].setText("");
        friday[3].setText("");
    }

    public int getCourseListSize(){
        return courseListSize;
    }

}
