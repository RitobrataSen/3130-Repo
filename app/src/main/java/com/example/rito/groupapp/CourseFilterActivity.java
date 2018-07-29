package com.example.rito.groupapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.rito.groupapp.ViewUser_Information.View_UserInformation;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * CourseFilterActivity allows users to drill down courses. plans exists to
 * further expand the courses filter to include specific course information
 * such as schedule, instructor, enrollment, and the option to auto generate
 * schedules. Currently, clicking on a course row stores a course object inside
 * an arraylist that can easily be processed.
 *
 * @author   Gobii, Rito, Yuhao
 * @since    2018-07-08
 */

public class CourseFilterActivity extends AppCompatActivity {
	private ListView lv;
	private ArrayList<CRN_Data> selectedCoreCourses = new ArrayList<>();
	private ArrayList<CRN_Data> selectedSupplementCourses = new ArrayList<>();

	private ArrayList<CRN_Data> viewCoreCourses = new ArrayList<>();
	private ArrayList<CRN_Data> viewSupplementCourses = new ArrayList<>();

	private ArrayList<ProcessedCRN> processedCRN = new ArrayList<>();

	private TextView mTextMessage;

	private Context context = this;
	private int duration = Toast.LENGTH_LONG;
	private String text = "";


	private int filterState = 0;
	private Term filterTerm = null;
	private Subject filterSubject = null;
	private Course filterCourse = null;
	private CourseType filterCourseType = null;
	private CRN_Data filterCRN = null;

	private boolean displaySelection = false;

	private String student = "Student2";

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
				Log.d("debug.print", "CFA, MENU CourseFilterActivity:");
				startActivity(new Intent(CourseFilterActivity.this, CourseFilterActivity.class));
				return true;

			case R.id.go_to_calender:
				Log.d("debug.print", "CFA, MENU CalendarView:");
				startActivity(new Intent(CourseFilterActivity.this, CalendarView.class));
				return true;

			case R.id.go_to_add_crn:
				Log.d("debug.print", "CFA, MENU CourseRegistration:");
				startActivity(new Intent(CourseFilterActivity.this, CourseRegistration.class));
				return true;

			case R.id.go_to_view_remove_registered:
				Log.d("debug.print", "CFA, MENU MyCoursesActivity:");
				startActivity(new Intent(CourseFilterActivity.this, MyCoursesActivity.class));
				return true;
			case R.id.view_user_information:
				Log.d("debug.print","CFA, MENU View_Information");
				startActivity(new Intent(CourseFilterActivity.this, View_UserInformation.class));
				return true;
			case R.id.log_out:
				Log.d("debug.print", "CFA, MENU Logout_Activity:");
				startActivity(new Intent(CourseFilterActivity.this, Logout_Activity.class));
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
					if (displaySelection){
						getCurrentState();
					} else {
						getPreviousState();
					}

					displaySelection = false;
					return true;

				case R.id.navigation_list:
					populateCurrentSelection(1);
					displaySelection = true;
					return true;

				case R.id.navigation_reset:

					t = "Reset Filter";
					m = "Are you sure you want to reset your selection?";
					o = 1;
					popupMsg(t, m, o);
					return true;

				case R.id.navigation_done:
					//getPreviousState();
					if (selectedCoreCourses.size() == 0 && selectedSupplementCourses.size() == 0){
						text = "You have not selected any courses. Please select at least 1 " +
								"course to register, and try again.";
						Toast toast = Toast.makeText(getApplicationContext(), text, duration);
						toast.show();

					} else {
						Log.d("debug.print", "DONE");
						t = "Register Classes";
						m = "Do you want register for all selected classes identified by " +
								"their CRNs?";
						o = 0;
						popupMsg(t, m, o);
						return true;
					}

			}

			return false;

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("debug.print", "line: " + new Exception().getStackTrace()[0].getLineNumber());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_filter);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		Toolbar hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(hdrToolBar);

		Intent intent = getIntent();
		//selectedCourses = new ArrayList<>();
		populateTerm();

	}

	public void getCurrentState(){
		switch (filterState){
			case 0:
				populateTerm();
				break;
			case 1:
				populateSubject(filterTerm);
				break;
			case 2:
				populateCourse(filterSubject);
				break;
			case 3:
				populateCourseType(filterCourse);
				break;
			case 4:
				populateCRN(filterCourseType);
				break;
			default:
				populateTerm();
				break;
		}
	}

	public void getPreviousState(){
		switch (filterState){
			case 0:
				populateTerm();
				break;
			case 1:
				populateTerm();
				break;
			case 2:
				populateSubject(filterTerm);
				break;
			case 3:
				populateCourse(filterSubject);
				break;
			case 4:
				populateCourseType(filterCourse);
				break;
			case 5:
				selectedCoreCourses.clear();
				selectedSupplementCourses.clear();
				viewCoreCourses.clear();
				viewSupplementCourses.clear();
				processedCRN.clear();
				populateTerm();
				break;
			default:
				populateTerm();
				break;
		}
	}

	public void addCRNs(){

	}

	public void populateCurrentSelection(int type){
		//0 = view crn
		//1 = view selected crn
		filterCRN = null;
		Log.d("debug.print", "selected CORE" + selectedCoreCourses.toString());
		Log.d("debug.print", "selected SUPPLEMENT" + selectedSupplementCourses.toString());
		Log.d("debug.print", "view CORE" + viewCoreCourses.toString());
		Log.d("debug.print", "view SUPPLEMENT" + viewSupplementCourses.toString());

		lv = findViewById(R.id.listView);

		ArrayList<CRN_Data> selectedCRN;

		//get arraylist
		if (type == 0) { //view crns
			selectedCRN = filterCourseType.getCore() ?
					viewCoreCourses : viewSupplementCourses;

			//set arraylist to adapter
			lv.setAdapter(new ArrayAdapter<CRN_Data>(
					this, R.layout.item_crn_selection_basic, selectedCRN){
				@Override
				public View getView (int position, View view, ViewGroup parent){
					if (view == null) {
						view = LayoutInflater.from(getContext()).inflate(R.layout.item_crn_selection_basic, parent,
								false);
					}

					CRN_Data crn_data = getItem(position);
					String []  arr = crn_data.getToStringArray(0);

					TextView line1 = (TextView) view.findViewById(R.id.line1);
					TextView line2 = (TextView) view.findViewById(R.id.line2);
					TextView line3 = (TextView) view.findViewById(R.id.line3);
					TextView line4 = (TextView) view.findViewById(R.id.line4);

					line1.setText(arr[0]);
					line2.setText(arr[1]);
					line3.setText(arr[2]);
					line4.setText(arr[3]);

					boolean cc = false;
					for (CRN_Data x : selectedCoreCourses) {
						if(x.equals(crn_data)){
							view.setBackgroundResource(R.color.colorSelected);
							cc = true;
							break;
						}
					}

					if (!(cc)){
						for (CRN_Data x : selectedSupplementCourses) {
							if(x.equals(crn_data)){
								view.setBackgroundResource(R.color.colorSelected);
								cc = true;
								break;
							}
						}
					}


					if (!(cc)){
						view.setBackgroundResource(R.color.transparent);
					}

					return view;
				}
			});

			// set On Item Click Listener for the listview
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					CRN_Data crn_data = (CRN_Data) parent.getItemAtPosition(position);
					filterCRN = crn_data;

					// custom dialog
					final Dialog dialog = new Dialog(context);
					dialog.setContentView(R.layout.item_crn_selection_full);
					//dialog.setTitle("Title...");

					// set the custom dialog components - text, image and button
					String []  arr = crn_data.getToStringArray(1);

					TextView title = (TextView) dialog.findViewById(R.id.title);
					title.setText("Add/Remove Course");

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
						Log.d("debug.print", t + " " + v + " " + "line" + i);
					}


					/*
					TextView line1 = (TextView) dialog.findViewById(R.id.line1);
					TextView line2 = (TextView) dialog.findViewById(R.id.line2);
					TextView line3 = (TextView) dialog.findViewById(R.id.line3);
					TextView line4 = (TextView) dialog.findViewById(R.id.line4);
					TextView line5 = (TextView) dialog.findViewById(R.id.line5);
					TextView line6 = (TextView) dialog.findViewById(R.id.line6);
					TextView line7 = (TextView) dialog.findViewById(R.id.line7);
					TextView line8 = (TextView) dialog.findViewById(R.id.line8);

					line1.setText(arr[0]);
					line2.setText(arr[1]);
					line3.setText(arr[2]);
					line4.setText(arr[3]);
					line5.setText(arr[4]);
					line6.setText(arr[5]);
					line7.setText(arr[6]);
					line8.setText(arr[7]);
					*/


					Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
					// if button is clicked, close the custom dialog
					dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});

					Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
					// if button is clicked, close the custom dialog
					dialogButtonOK.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							boolean sel = markSelection();
							//view.setBackgroundResource(R.color.transparent);
							//view.setBackgroundResource(R.color.colorSelected);
							dialog.dismiss();
							populateCurrentSelection(0);
						}
					});

					dialog.show();

				}
			});

			lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

		} else if (type == 1) { //selected crns
			selectedCRN = new ArrayList<>(selectedCoreCourses);
			selectedCRN.removeAll(selectedSupplementCourses);
			selectedCRN.addAll(selectedSupplementCourses);

			//set arraylist to adapter
			lv.setAdapter(new ArrayAdapter<CRN_Data>(
					this, R.layout.item_crn_selection_basic, selectedCRN){
				@Override
				public View getView (int position, View view, ViewGroup parent){
					if (view == null) {
						view = LayoutInflater.from(getContext()).inflate(R.layout.item_crn_selection_basic, parent,
								false);
					}

					CRN_Data crn_data = getItem(position);
					String []  arr = crn_data.getToStringArray(0);

					TextView line1 = (TextView) view.findViewById(R.id.line1);
					TextView line2 = (TextView) view.findViewById(R.id.line2);
					TextView line3 = (TextView) view.findViewById(R.id.line3);
					TextView line4 = (TextView) view.findViewById(R.id.line4);

					line1.setText(arr[0]);
					line2.setText(arr[1]);
					line3.setText(arr[2]);
					line4.setText(arr[3]);

					return view;
				}
			});

			// set On Item Click Listener for the listview
			lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//go to selection
					CRN_Data crn_data = (CRN_Data) parent.getItemAtPosition(position);
					navigateToSelection(crn_data);
				}
			});
			lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

		}


	}

	public void populateTerm() {
		filterState = 0;
		filterTerm = null;
		filterSubject = null;
		filterCourse = null;
		filterCourseType = null;
		filterCRN = null;

		FirebaseListAdapter<Term> firebaseAdapter;
		Database db = new Database("TERM");
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Term>(this, Term.class,
				android.R.layout.simple_list_item_1, db.getDbRef()) {
			@Override
			protected void populateView(View v, Term model, int position) {
				TextView termRow = (TextView)v.findViewById(android.R.id.text1);
				termRow.setText(model.toString());
				boolean cc = false;

				for (CRN_Data x : selectedCoreCourses) {
					if((x.getTerm_Code().equals(model.getTerm_code()))){
						v.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
					}
				}

				if (!(cc)){
					for (CRN_Data x : selectedSupplementCourses) {
						if((x.getTerm_Code().equals(model.getTerm_code()))){
							v.setBackgroundResource(R.color.colorSelected);
							cc = true;
							break;
						}
					}
				}

				if (!(cc)){
					v.setBackgroundResource(R.color.transparent);
				}

			}
		};
		lv.setAdapter(firebaseAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Term term = (Term) parent.getItemAtPosition(position);
				populateSubject(term);
			}
		});
	}

	public void populateSubject(Term term){
		filterState = 1;
		filterTerm = term;
		filterSubject = null;
		filterCourse = null;
		filterCourseType = null;
		filterCRN = null;

		FirebaseListAdapter<Subject> firebaseAdapter;
		Database db = new Database("SUBJECT/" + term.getTerm_code());
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Subject>(this, Subject.class,
				android.R.layout.simple_list_item_1, db.getDbRef()) {
			@Override
			protected void populateView(View v, Subject model, int position) {
				TextView subjectRow = (TextView)v.findViewById(android.R.id.text1);
				subjectRow.setText(model.toString());
				boolean cc = false;

				for (CRN_Data x : selectedCoreCourses) {
					if(
							x.getTerm_Code().equals(model.getTerm_code())
							&& x.getSubject_Code().equals(model.getSubject_code())
					){
						v.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
					}
				}

				if (!(cc)) {
					for (CRN_Data x : selectedSupplementCourses) {
						if(
								x.getTerm_Code().equals(model.getTerm_code())
								&& x.getSubject_Code().equals(model.getSubject_code())
						){
							v.setBackgroundResource(R.color.colorSelected);
							cc = true;
							break;
						}
					}
				}

				if (!(cc)){
					v.setBackgroundResource(R.color.transparent);
				}

			}
		};
		lv.setAdapter(firebaseAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Subject subject = (Subject) parent.getItemAtPosition(position);
				populateCourse(subject);
			}
		});
	}

	public void populateCourse(Subject subject){// params: listview reference, selected object
		filterState = 2;
		//filterTerm = null;
		filterSubject = subject;
		filterCourse = null;
		filterCourseType = null;
		filterCRN = null;

		FirebaseListAdapter<Course> firebaseAdapter;
		Database db = new Database("COURSE/" +
				subject.getTerm_code() +
				"/" +  subject.getSubject_code());
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Course>(this, Course.class,

				R.layout.item_course_selection, db.getDbRef()) {

			@Override
			protected void populateView(View v, Course model, int position) {

				boolean cc = false;
				Course course = getItem(position);
				TextView ccode = (TextView) v.findViewById(R.id.courseCode);
				TextView cname = (TextView) v.findViewById(R.id.courseName);
				TextView cterm = (TextView) v.findViewById(R.id.courseTerm);
				TextView csupp = (TextView) v.findViewById(R.id.courseSupplement);

				ccode.setText(String.format("Course Code:%s", course.getCourse_code()));
				cname.setText(String.format("Course Name:%s", course.getCourse_name()));
				cterm.setText(String.format("Term Code:%s", course.getTerm_code()));
				csupp.setText(String.format("Has Supplement:%s", course.getHas_supplement()));

				for (CRN_Data x : selectedCoreCourses) {
					if(
							x.getTerm_Code().equals(model.getTerm_code())
							&& x.getSubject_Code().equals(model.getSubject_code())
							&& x.getCourse_Code().equals(model.getCourse_code())

					){
						v.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
					}
				}

				if (!(cc)) {
					for (CRN_Data x : selectedSupplementCourses) {
						if(
								x.getTerm_Code().equals(model.getTerm_code())
								&& x.getSubject_Code().equals(model.getSubject_code())
								&& x.getCourse_Code().equals(model.getCourse_code())
						){
							v.setBackgroundResource(R.color.colorSelected);
							cc = true;
							break;
						}
					}
				}

				if (!(cc)){
					v.setBackgroundResource(R.color.transparent);
				}
			}
		};

		lv.setAdapter(firebaseAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//--------------------------------------------------------------------------------
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Course course = (Course) parent.getItemAtPosition(position);
				populateCourseType(course);
			}
		});

	}

	public void populateCourseType(Course course) {// params: listview reference,
		filterState = 3;
		//filterTerm = null;
		//filterSubject = null;
		filterCourse = course;
		filterCourseType = null;
		filterCRN = null;

		ArrayList<CourseType> al = new ArrayList<>();

		al.add(new CourseType(
				course.getTerm_code(), course.getSubject_code(), course.getCourse_code(), true,
				course.getCore()
		));

		al.add(new CourseType(
				course.getTerm_code(), course.getSubject_code(), course.getCourse_code(), false,
				course.getSupplement()
		));

		lv = findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<CourseType>(
				this, android.R.layout.simple_list_item_1 , al){
			//R.layout.**** IS THE DESIGN FOR THE ROW
			@Override
			public View getView (int position, View view, ViewGroup parent){
				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent,
							false);
				}
				CourseType coursetype = getItem(position);
				TextView row = (TextView)view.findViewById(android.R.id.text1);
				row.setText(coursetype.toString());

				Log.d("debug.print", String.format("%s %s", coursetype.getCore(), coursetype.getDescrip()));
				boolean cc = false;

				for (CRN_Data x : selectedCoreCourses) {
					Log.d("debug.print", String.format("%s %s", x.isCore(), x.toString()));
					Log.d("debug.print", String.format("%s %s", coursetype.getCore(), coursetype.getDescrip()));

					if(
							x.getTerm_Code().equals(coursetype.getTerm_Code())
							&& x.getSubject_Code().equals(coursetype.getSubject_Code())
							&& x.getCourse_Code().equals(coursetype.getCourse_Code())
							&& x.isCore() == coursetype.getCore()
					){
						view.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
					}
				}

				if (!(cc)) {
					for (CRN_Data x : selectedSupplementCourses) {
						if(
								x.getTerm_Code().equals(coursetype.getTerm_Code())
								&& x.getSubject_Code().equals(coursetype.getSubject_Code())
								&& x.getCourse_Code().equals(coursetype.getCourse_Code())
								&& x.isCore() == coursetype.getCore()
						){
							view.setBackgroundResource(R.color.colorSelected);
							cc = true;
							break;
						}
					}
				}

				if (!(cc)){
					view.setBackgroundResource(R.color.transparent);
				}

				return view;
			}
		});
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CourseType coursetype = (CourseType) parent.getItemAtPosition(position);
				populateCRN(coursetype);
			}
		});

	}

	public void displayProcessedCRN(){
		lv = findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<ProcessedCRN>(
				this, R.layout.item_processed_crn , processedCRN){
			//R.layout.**** IS THE DESIGN FOR THE ROW
			@Override
			public View getView (int position, View view, ViewGroup parent){
				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_processed_crn, parent,
							false);
				}
				ProcessedCRN p = getItem(position);
				TextView row1 = (TextView) view.findViewById(R.id.crn_pc);
				TextView row2 = (TextView) view.findViewById(R.id.msg_pc);
				ImageView row3 = (ImageView) view.findViewById(R.id.status_pc);

				row1.setText(p.getCrn());
				row2.setText(p.getMsg());

				if (p.getStatus()){
					row3.setImageDrawable(getResources().getDrawable(R.drawable.ic_pass_green_24dp,
							getApplicationContext().getTheme()));
				} else {
					row3.setImageDrawable(getResources().getDrawable(R.drawable.ic_fail_red_24dp,
							getApplicationContext().getTheme()));
				}


				return view;
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ProcessedCRN p = (ProcessedCRN) parent.getItemAtPosition(position);
				ArrayList<ProcessedCRN> newProcessedCRN = new ArrayList<>();

				for (ProcessedCRN x : processedCRN){
					if (!(x.equals(p))){
						newProcessedCRN.add(x);
					}
				}
				processedCRN = newProcessedCRN;
				displayProcessedCRN();
			}
		});

	}


	public void addSelection(){
		filterState = 5;

		ArrayList<CRN_Data> selectedCRN;
		selectedCRN = new ArrayList<>(selectedCoreCourses);
		selectedCRN.removeAll(selectedSupplementCourses);
		selectedCRN.addAll(selectedSupplementCourses);
		final Database db = new Database();
		DatabaseReference ref;
		final ProcessedCRN pcd = new ProcessedCRN();

		for (CRN_Data c : selectedCRN){
			pcd.setCrn(c.getCrn());
			ref = db.getDb().getReference("CRN_DATA/" + c.getCrn());
			ref.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					//Read the inputted string from users
					//Checking if crn exists
					if (dataSnapshot.exists()) {
						final CRN_Data crn_data = dataSnapshot.getValue(CRN_Data.class);
						long max = Integer.parseInt(crn_data.getMax());
						long cur = crn_data.getCur();
						//Checking the number of student that enrolled in this course is full or not
						if (max > cur) {
							DatabaseReference ref;
							ref = db.getDb().getReference("STUDENT/" +
									MainActivity.currentUser.getUsername());
							ref.addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(DataSnapshot dataSnapshot) {
									if (!(dataSnapshot.hasChild("registration")) ||
											!(dataSnapshot.child("registration").hasChild(crn_data.getCrn()))) {

										//save data
										db.addRemoveCourse(
												crn_data.getCrn(),
												MainActivity.currentUser.getUsername(),
												true);

										//append Processed_CRN object
										pcd.setCrn(crn_data.getCrn());
										pcd.setMsg("Registration successful!");
										pcd.setStatus(true);
										//append Processed_CRN object
										processedCRN.add(pcd.cloneData());
										displayProcessedCRN();

									} else {
										//case for duplicate enrollment
										pcd.setCrn(crn_data.getCrn());
										pcd.setMsg("Registration failed: " +
												"You are already enrolled " +
												"for this CRN!");
										pcd.setStatus(false);
										//append Processed_CRN object
										processedCRN.add(pcd.cloneData());
										displayProcessedCRN();
									}
								}

								@Override
								public void onCancelled(DatabaseError databaseError) {
								}
							});
						} else {
							//case for course is full
							pcd.setCrn(dataSnapshot.getKey());
							pcd.setMsg("Registration failed: Course is full. " +
									"Please contact the instructor.");
							pcd.setStatus(false);
							//append Processed_CRN object
							processedCRN.add(pcd.cloneData());
							displayProcessedCRN();
						}
					} else {
						//case for the inputted CRN is not exists
						pcd.setCrn(dataSnapshot.getKey());
						pcd.setMsg("Registration failed: CRN does not exist.");
						pcd.setStatus(false);
						//append Processed_CRN object
						processedCRN.add(pcd.cloneData());
						displayProcessedCRN();
					}
				}

				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
		}

		filterTerm = null;
		filterSubject = null;
		filterCourse = null;
		filterCourseType = null;
		filterCRN = null;

		selectedCoreCourses.clear();
		selectedSupplementCourses.clear();
		viewCoreCourses.clear();
		viewSupplementCourses.clear();

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
						dialog.dismiss();
						addSelection();
					}
				});
				break;
			case 1:
				popUpMsgButtonOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						selectedCoreCourses.clear();
						selectedSupplementCourses.clear();
						viewCoreCourses.clear();
						viewSupplementCourses.clear();
						processedCRN.clear();
						populateTerm();
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

	public void populateCRN(CourseType coursetype){// params: listview reference,

		filterState = 4;

		filterCourseType = coursetype;
		filterCRN = null;

		boolean core = coursetype.getCore();

		viewCoreCourses = new ArrayList<>();
		viewSupplementCourses = new ArrayList<>();

		Log.d("debug.print", String.format("%s\n%s\n%s\n%s\n%s\n%s\n",
				coursetype.getCore(),
				coursetype.getDescrip(),
				coursetype.getTerm_Code(),
				coursetype.getSubject_Code(),
				coursetype.getCourse_Code(),
				coursetype.getKeys()
		));

		for (String k : coursetype.getKeys()){
			Database dbCRN = new Database("CRN_DATA/" + k);
			dbCRN.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() { //addValueEventListener
				@Override
				public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
					CRN_Data crn_data = dataSnapshot.getValue(CRN_Data.class);
					Log.d("debug.print", crn_data.toString());
					Log.d("debug.print", "days: " + crn_data.getDays().toString());

					if (filterCourseType.getCore()){
						viewCoreCourses.add(crn_data);

					} else {
						viewSupplementCourses.add(crn_data);
					}
					populateCurrentSelection(0);
				}

				@Override
				public void onCancelled(@NonNull DatabaseError databaseError) {
					Log.d("debug.print", "The read failed: " + databaseError.getCode());
				}
			});
		}

	}

	//public boolean markSelection(boolean add){
	public boolean markSelection(){
		CRN_Data crn = filterCRN;
		boolean core = filterCourseType.getCore();
		boolean sel = false;

		ArrayList<CRN_Data> newSelectedCourses = new ArrayList<>();
		ArrayList<CRN_Data> selectedCourses = core ? selectedCoreCourses :
				selectedSupplementCourses;

		if (!(crn == null)){
			for (CRN_Data x : selectedCourses) {
				if(!(x.equals(crn))){
					newSelectedCourses.add(x);
				} else {
					sel = true;
				}
			}

			if (core){
				selectedCoreCourses = newSelectedCourses;
				if (!(sel)){
					selectedCoreCourses.add(crn);
				}
			} else {
				selectedSupplementCourses = newSelectedCourses;
				if (!(sel)){
					selectedSupplementCourses.add(crn);
				}
			}
		}

		return sel;
	}

	public void navigateToSelection(CRN_Data crn_data){
		filterTerm = null;
		filterSubject = null;
		filterCourse = null;
		filterCourseType = null;
		filterCRN = crn_data;
		Database db = new Database(
				"TERM/" +
				filterCRN.getTerm_Code()
		);
		//set filterTerm
		db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				filterTerm = dataSnapshot.getValue(Term.class);
				if (filterTerm != null){
					Database db = new Database(
							"SUBJECT/"  +
									filterCRN.getTerm_Code() + "/" +
									filterCRN.getSubject_Code()
					);
					//set filterSubject
					db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							filterSubject = dataSnapshot.getValue(Subject.class);
							if (filterSubject != null){
								Database db = new Database(
										"COURSE/"  +
												filterCRN.getTerm_Code() + "/" +
												filterCRN.getSubject_Code() + "/" +
												filterCRN.getCourse_Code()
								);
								//set filterCourse
								db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
									@Override
									public void onDataChange(DataSnapshot dataSnapshot) {
										filterCourse = dataSnapshot.getValue(Course.class);
										if (filterCourse != null){
											boolean is_core = filterCRN.isCore();
											//set filterCourseType
											filterCourseType = new CourseType(
													filterCourse.getTerm_code(), filterCourse.getSubject_code(), filterCourse.getCourse_code(), is_core,
													is_core ?
															filterCourse.getCore() :
															filterCourse.getSupplement()
											);
											populateCRN(filterCourseType);
										} else {
											text = "Error loading course. Restarting Course Filter";
											Toast toast = Toast.makeText(getApplicationContext(), text, duration);
											toast.show();
											populateTerm();
										}
									}
									@Override
									public void onCancelled(DatabaseError databaseError) {
										Log.d("debug.print", "The read failed: " + databaseError.getCode());
									}
								});
							} else {
								text = "Error loading subject. Restarting Course Filter";
								Toast toast = Toast.makeText(getApplicationContext(), text, duration);
								toast.show();
								populateTerm();
							}
						}
						@Override
						public void onCancelled(DatabaseError databaseError) {
							Log.d("debug.print", "The read failed: " + databaseError.getCode());
						}
					});
				} else {
					text = "Error loading term. Restarting Course Filter";
					Toast toast = Toast.makeText(getApplicationContext(), text, duration);
					toast.show();
					populateTerm();
				}
			}
			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.d("debug.print", "The read failed: " + databaseError.getCode());
			}
		});
	}

}
