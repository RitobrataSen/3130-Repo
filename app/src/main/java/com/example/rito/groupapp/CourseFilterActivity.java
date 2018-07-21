package com.example.rito.groupapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.Collections;

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
	private ArrayList<Course> selectedCourses = new ArrayList<>();

	private TextView mTextMessage;

	/*
	0 = term selection
	1 = subject selection
	2 = course selection
	*/
	private int filterState = 0;
	private Term filterTerm = null;
	private Subject filterSubject = null;
	private Course filterCourse = null;
	private CRN_Data filterCRN = null;

	private boolean displayList = true;
	// flag to determine the current view type,
	// true = course selection display,
	// false = selected courses display;

	/*
	NOTE: we will need to pass in the user data or access
	it through a public variable. for development purposes
	we will set it as a static variable here.
	*/

	/*
	for iteration 3 we will need to do the following:
	1) go deeper into the course filter where clicking
	a course lists all crn and they schedules

	2) auto schedule generation that recursively builds
	non overlapping schedules (ie overlap between courses
	such that no two courses can occur during the same time
	for a single user)

	3) allow easy conversion to adding courses by crn. for example
	add a crn to a pool that is used by the schedule generator.
	 */
	private String student = "Student2";
	private Toolbar hdrToolBar;

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
				Log.d("debug.print", "CFA, MENU ViewRemoveCourseRegistrationActivity:");
				startActivity(new Intent(CourseFilterActivity.this, ViewRemoveCourseRegistrationActivity.class));
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

			switch (item.getItemId()) {
				case R.id.navigation_back:
					if (displayList){
						getCurrentState();
					} else {
						getPreviousState();
					}

					displayList = false;
					return true;

				case R.id.navigation_list:
					populateCurrentSelection();
					displayList = true;
					return true;

				/*
				case R.id.navigation_add:

					//mTextMessage.setText(R.string.title_dashboard);//title_my_courses
					Log.d("debug.print", "NAVI, ADD BY CRN:" + selectedCourses);
					return true;
				*/

				case R.id.navigation_reset:
					selectedCourses.clear();
					populateTerm();
					return true;

				/*
				case R.id.navigation_done:
					//getPreviousState();
					Log.d("debug.print", "NAVI, DONE:" + selectedCourses);
					return true;
				*/
			}

			return false;

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_filter);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
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
			default:
				populateTerm();
				break;
		}
	}

	public void populateCurrentSelection(){
		lv = findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<Course>(
				this, R.layout.item_course_selection , selectedCourses){
				//R.layout.**** IS THE DESIGN FOR THE ROW
			@Override
			public View getView (int position, View view, ViewGroup parent){
				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_course_selection, parent,
							false);
				}

				Course course = getItem(position);
				TextView ccode = (TextView) view.findViewById(R.id.courseCode);
				TextView cname = (TextView) view.findViewById(R.id.courseName);
				TextView cterm = (TextView) view.findViewById(R.id.courseTerm);
				TextView csupp = (TextView) view.findViewById(R.id.courseSupplement);

				ccode.setText(String.format("Course Code:%s", course.getCourse_code()));
				cname.setText(String.format("Course Name:%s", course.getCourse_name()));
				cterm.setText(String.format("Term Code:%s", course.getTerm_code()));
				csupp.setText(String.format("Has Supplement:%s", course.getHas_supplement()));

				return view;
			}
		});
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Course course = (Course) parent.getItemAtPosition(position);
				filterState = 2;
				filterTerm = new Term(course.getTerm_code(), "", "");
				filterSubject = new Subject(course.getSubject_code(), "", course.getTerm_code());
				filterCourse = null;
				filterCRN = null;

				populateCourse(filterSubject);
			}
		});
	}

	public void populateTerm() {
		filterState = 0;
		filterTerm = null;
		filterSubject = null;
		filterCourse = null;
		filterCRN = null;

		final FirebaseListAdapter<Term> firebaseAdapter;
		Database db = new Database("TERM");
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Term>(this, Term.class,
				android.R.layout.simple_list_item_1, db.getDbRef()) {
			@Override
			protected void populateView(View v, Term model, int position) {
				TextView termRow = (TextView)v.findViewById(android.R.id.text1);
				termRow.setText(model.toString());
				boolean cc = false;

				for (Course x : selectedCourses) {
					if((x.getTerm_code().equals(model.getTerm_code()))){
						v.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
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
				Term term = (Term) firebaseAdapter.getItem(position);
				populateSubject(term);
			}
		});
	}

	public void populateSubject(Term term){// params: listview reference, selected object
		filterState = 1;
		filterTerm = term;
		filterSubject = null;
		filterCourse = null;
		filterCRN = null;

		final FirebaseListAdapter<Subject> firebaseAdapter;
		Database db = new Database("SUBJECT/" + term.getTerm_code());
//		Database db = new Database("SUBJECT");
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Subject>(this, Subject.class,
				android.R.layout.simple_list_item_1, db.getDbRef()) {
			@Override
			protected void populateView(View v, Subject model, int position) {
				TextView subjectRow = (TextView)v.findViewById(android.R.id.text1);
				subjectRow.setText(model.toString());
				boolean cc = false;

				for (Course x : selectedCourses) {
					if( (x.getSubject_code().equals(model.getSubject_code())) &&
						(x.getTerm_code().equals(model.getTerm_code())) ){

						v.setBackgroundResource(R.color.colorSelected);
						cc = true;
						break;
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
				Subject subject = (Subject) firebaseAdapter.getItem(position);
				populateCourse(subject);
			}
		});
	}

	public void populateCourse(Subject subject){// params: listview reference, selected object
		filterState = 2;
		//filterTerm = null;
		filterSubject = subject;
		filterCourse = null;
		filterCRN = null;

		final FirebaseListAdapter<Course> firebaseAdapter;
		Database db = new Database("COURSE/" +
				subject.getTerm_code() +
				"/" +  subject.getSubject_code());
		lv = findViewById(R.id.listView);
		firebaseAdapter = new FirebaseListAdapter<Course>(this, Course.class,
				//android.R.layout.simple_list_item_1, db.getDbRef()) {
				R.layout.item_course_selection, db.getDbRef()) {

			@Override
			protected void populateView(View v, Course model, int position) {
				//TextView courseRow = (TextView)v.findViewById(android.R.id.text1);
				boolean cc = false;
				//courseRow.setText(model.toString2());
				Course course = getItem(position);
				TextView ccode = (TextView) v.findViewById(R.id.courseCode);
				TextView cname = (TextView) v.findViewById(R.id.courseName);
				TextView cterm = (TextView) v.findViewById(R.id.courseTerm);
				TextView csupp = (TextView) v.findViewById(R.id.courseSupplement);

				ccode.setText(String.format("Course Code:%s", course.getCourse_code()));
				cname.setText(String.format("Course Name:%s", course.getCourse_name()));
				cterm.setText(String.format("Term Code:%s", course.getTerm_code()));
				csupp.setText(String.format("Has Supplement:%s", course.getHas_supplement()));


				for (Course x : selectedCourses) {
					if(x.equals(model)){
						v.setBackgroundResource(R.color.colorSelected);
						//v.setBackgroundColor(getResources().getColor(R.color.colorSelected));
						cc = true;
						break;
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
		Course course = (Course) firebaseAdapter.getItem(position);
		boolean sel = false;
		ArrayList<Course> newSelectedCourses = new ArrayList<>();

		for (Course x : selectedCourses) {
			if(!(x.equals(course))){
				newSelectedCourses.add(x);
			} else {
				sel = true;
			}
		}
		selectedCourses = newSelectedCourses;

		if (sel){
			view.setBackgroundResource(R.color.transparent);
		} else {
			view.setBackgroundResource(R.color.colorSelected);
			selectedCourses.add(course);
		}

			}
		});
		lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

	}

	//resume here
	//public void populateCRN(Course course){// params: listview reference, selected object


}
