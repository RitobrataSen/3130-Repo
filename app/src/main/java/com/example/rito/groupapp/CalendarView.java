package com.example.rito.groupapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    public ArrayList<TextView> monday = new ArrayList<TextView>();
    public ArrayList<TextView> tuesday = new ArrayList<TextView>();
    public ArrayList<TextView> wednesday = new ArrayList<TextView>();
    public ArrayList<TextView> thursday = new ArrayList<TextView>();
    public ArrayList<TextView> friday = new ArrayList<TextView>();
    public ArrayList<TableRow> newRows = new ArrayList<TableRow>();
    public ArrayList<CRN_Data> calendarCourses = new ArrayList<CRN_Data>();
    public int counter;
    private Term filterTerm = null;
	private int filterState = 0;
    private Toolbar hdrToolBar;
	private ListView lv;
	private ScrollView cal;
	private ConstraintLayout conLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	//main navigation menu
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
                return true;

            case R.id.log_out:
                startActivity(new Intent(CalendarView.this, Logout_Activity.class));
                return true;
        }

		return super.onOptionsItemSelected(item);

	}

	//in activity navigation
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
		cal = findViewById(R.id.scrollView);

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

	//activity specific popup message
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

	//used for in activity navigation
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

	//used for in activity navigation
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

    //initializes the create calendar process
    public void createCalendar(Term term){
		filterState = 1;
		filterTerm = term;
		lv.setAdapter(null);
		lv.setVisibility(View.GONE);
		cal.setVisibility(View.VISIBLE);
		applyNewConstraints(R.id.scrollView);

        courseListSize = 4;
    	populateTextViewLists();
		calendarCourses.clear();

		Object [] crn_list = MainActivity.currentUser.getRegistration().keySet().toArray();
		//Recall, this wont work unless a user is signed in.
		if(MainActivity.currentUser != null) {
			for(int i=0; i < MainActivity.currentUser.getRegistration().keySet().toArray().length; i++) {

				Log.d("debug.print", crn_list[i].toString());

				String crn = crn_list[i].toString();

				counter = i;
				Database db = new Database("CRN_DATA/" + crn);
				db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
						if (dataSnapshot.exists()) {
							CRN_Data curr = (CRN_Data) dataSnapshot.getValue(CRN_Data.class);

							if (curr.getTerm_Code().equals(filterTerm.getTerm_code())){
								appendCRN_Data(curr);
								populateTextViewLists();
								populateCalendar();
							}
						}
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
			displayCourse(0,monday, c);
		}
	}

	//add a specific crn tot he calendar list
	public void appendCRN_Data(CRN_Data crn){
		boolean sel = false;
		ArrayList<CRN_Data> newSelectedCourses = new ArrayList<>();

		Log.d("debug.print", crn.toString_CFA_Full());

		if (!(crn == null)){
			for (CRN_Data x : calendarCourses) {
				if(!(x.equals(crn))){
					newSelectedCourses.add(x);
				} else {
					sel = true;
				}
			}

			calendarCourses = newSelectedCourses;
			Log.d("debug.print", String.format("%s", sel));
			if (!(sel)){
				calendarCourses.add(crn);
			}

			Log.d("debug.print", calendarCourses.toString());
		}
	}

	//used to populate the calendar
    public void populateCalendar(){
        Collections.sort(calendarCourses);
        for(int i = 0; i < calendarCourses.size(); i++) {
            if (calendarCourses.get(i).getDays().get("mon")) {
                displayCourse(0,monday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("tue")) {
                displayCourse(1,tuesday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("wed")) {
                displayCourse(2,wednesday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("thu")) {
                displayCourse(3,thursday, calendarCourses.get(i));
            }
            if (calendarCourses.get(i).getDays().get("fri")) {
                displayCourse(4,friday, calendarCourses.get(i));
            }
        }
    }


    //used to display course information
    public void displayCourse(int day, ArrayList<TextView> selected, CRN_Data course){
        for(int i = 0; i < selected.size(); i++) {
            if(i == selected.size()-1){
                selected = increaseCalendarSize(day);
            }
            if(selected.get(i).getText().length() == 0) {
                selected.get(i).setText(course.getCrn() + "\n" + course.getCourse_Code() + "\nTime:" + course
                        .getStart_Time() + "-" + course.getEnd_Time());
                selected.get(i).setBackgroundResource(R.drawable.cell_shape_event);
                selected.get(i).setClickable(true);
                selected.get(i).setOnClickListener(new View.OnClickListener() {
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
										title.setText("Class Details");

										//bold titles
										SpannableString ss;
										String t, v;
										HashMap<String, TextView> hmap = new HashMap<>();
										hmap.put("line1", (TextView) dialog.findViewById(R.id.line1));
										hmap.put("line2", (TextView) dialog.findViewById(R.id.line2));
										hmap.put("line3", (TextView) dialog.findViewById(R.id.line3));
										hmap.put("line4", (TextView) dialog.findViewById(R.id.line4));
										hmap.put("line5", (TextView) dialog.findViewById(R.id.line5));
										hmap.put("line6", (TextView) dialog.findViewById(R.id.line6));
										hmap.put("line7", (TextView) dialog.findViewById(R.id.line7));
										hmap.put("line8", (TextView) dialog.findViewById(R.id.line8));
										String [] s;
										TextView tv;
										for (int i = 0; i < arr.length; i++){
											s = arr[i].split(":");
											t = s[0] + ":";
											v = s[1];
											ss =  new SpannableString(t);
											ss.setSpan(new StyleSpan(Typeface.BOLD), 0, ss.length(), 0);
											tv = hmap.get("line" + (i + 1));
											tv.append(ss);
											tv.append(v);
										}

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

    /**@since 2018-07-29
     * @Author Dryden and Yuhao
     * Fixes a bug that occurs when many courses are added to calendar.
     */
    public ArrayList<TextView> increaseCalendarSize(int day){
        TableLayout tl = (TableLayout)findViewById(R.id.table_layout);
        TableRow newTableRow = new TableRow(this);
        newTableRow.setLayoutParams(new TableLayout.LayoutParams( TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT));
        TableLayout.LayoutParams lparams = new TableLayout.LayoutParams(110, 115);

        int width = monday.get(0).getWidth();
        int height = monday.get(0).getHeight();

        TextView textview = new TextView(this);
        textview.setText("");
        textview.setWidth(width);
        textview.setHeight(height);
        textview.setPadding(10,10,10,10);
        textview.setTextAppearance(this,R.style.TextAppearance_AppCompat_Medium);
        textview.setBackgroundResource(R.drawable.cell_shape_no_event);
        textview.setVisibility(View.VISIBLE);
        newTableRow.addView(textview);
        monday.add(textview);

        TextView textview1 = new TextView(this);
        textview1.setText("");
        textview1.setWidth(width);
        textview1.setHeight(height);
        textview1.setPadding(10,10,10,10);
        textview1.setTextAppearance(this,R.style.TextAppearance_AppCompat_Medium);
        textview1.setBackgroundResource(R.drawable.cell_shape_no_event);
        textview1.setVisibility(View.VISIBLE);
        newTableRow.addView(textview1);
        tuesday.add(textview1);

        TextView textview2 = new TextView(this);
        textview2.setText("");
        textview2.setWidth(width);
        textview2.setHeight(height);
        textview2.setPadding(10,10,10,10);
        textview2.setTextAppearance(this,R.style.TextAppearance_AppCompat_Medium);
        textview2.setBackgroundResource(R.drawable.cell_shape_no_event);
        textview2.setVisibility(View.VISIBLE);
        newTableRow.addView(textview2);
        wednesday.add(textview2);

        TextView textview3 = new TextView(this);
        textview3.setWidth(width);
        textview3.setHeight(height);
        textview3.setPadding(10,10,10,10);
        textview3.setTextAppearance(this,R.style.TextAppearance_AppCompat_Medium);
        textview3.setText("");
        textview3.setBackgroundResource(R.drawable.cell_shape_no_event);
        textview3.setVisibility(View.VISIBLE);
        newTableRow.addView(textview3);
        thursday.add(textview3);

        TextView textview4 = new TextView(this);
        textview4.setWidth(width);
        textview4.setHeight(height);
        textview4.setPadding(10,10,10,10);
        textview4.setTextAppearance(this,R.style.TextAppearance_AppCompat_Medium);
        textview4.setText("");
        textview4.setBackgroundResource(R.drawable.cell_shape_no_event);
        textview4.setVisibility(View.VISIBLE);
        newTableRow.addView(textview4);
        friday.add(textview4);

        tl.addView(newTableRow);
        newRows.add(newTableRow);


        switch (day){
            case 0: return monday;
            case 1: return tuesday;
            case 2: return wednesday;
            case 3: return thursday;
            default: return friday;
        }

    }


    // Method fills list of TextViews and clears all old courses
    public void populateTextViewLists(){
        TableLayout tl = (TableLayout)findViewById(R.id.table_layout);
        for(int i=0;i < newRows.size();i++){
            tl.removeView(newRows.get(i));
        }
        newRows.clear();

        monday.clear();
        tuesday.clear();
        wednesday.clear();
        thursday.clear();
        friday.clear();

        monday.add((TextView)findViewById(R.id.m_body));
        monday.add((TextView)findViewById(R.id.m1_body));
        monday.add((TextView)findViewById(R.id.m2_body));
        monday.add((TextView)findViewById(R.id.m3_body));

        monday.get(0).setText("");
        monday.get(1).setText("");
        monday.get(2).setText("");
        monday.get(3).setText("");

        tuesday.add((TextView)findViewById(R.id.t_body));
        tuesday.add((TextView)findViewById(R.id.t1_body));
        tuesday.add((TextView)findViewById(R.id.t2_body));
        tuesday.add((TextView) findViewById(R.id.t3_body));

        tuesday.get(0).setText("");
        tuesday.get(1).setText("");
        tuesday.get(2).setText("");
        tuesday.get(3).setText("");

        wednesday.add((TextView)findViewById(R.id.w_body));
        wednesday.add((TextView)findViewById(R.id.w1_body));
        wednesday.add((TextView)findViewById(R.id.w2_body));
        wednesday.add((TextView)findViewById(R.id.w3_body));

        wednesday.get(0).setText("");
        wednesday.get(1).setText("");
        wednesday.get(2).setText("");
        wednesday.get(3).setText("");

        thursday.add((TextView)findViewById(R.id.r_body));
        thursday.add((TextView)findViewById(R.id.r1_body));
        thursday.add((TextView)findViewById(R.id.r2_body));
        thursday.add((TextView)findViewById(R.id.r3_body));

        thursday.get(0).setText("");
        thursday.get(1).setText("");
        thursday.get(2).setText("");
        thursday.get(3).setText("");

        friday.add((TextView)findViewById(R.id.f_body));
        friday.add((TextView)findViewById(R.id.f1_body));
        friday.add((TextView)findViewById(R.id.f2_body));
        friday.add((TextView)findViewById(R.id.f3_body));

        friday.get(0).setText("");
        friday.get(1).setText("");
        friday.get(2).setText("");
        friday.get(3).setText("");

        int i = 0;
        for (TextView t : monday){
			monday.get(i).setBackgroundResource(R.drawable.cell_shape_no_event);
			tuesday.get(i).setBackgroundResource(R.drawable.cell_shape_no_event);
			wednesday.get(i).setBackgroundResource(R.drawable.cell_shape_no_event);
			thursday.get(i).setBackgroundResource(R.drawable.cell_shape_no_event);
			friday.get(i).setBackgroundResource(R.drawable.cell_shape_no_event);

			monday.get(i).setClickable(false);
			tuesday.get(i).setClickable(false);
			wednesday.get(i).setClickable(false);
			thursday.get(i).setClickable(false);
			friday.get(i).setClickable(false);
			i++;
		}


	}

    public int getCourseListSize(){
        return courseListSize;
    }

}
