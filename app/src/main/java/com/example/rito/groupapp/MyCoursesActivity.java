package com.example.rito.groupapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import android.widget.Toast;

import com.example.rito.groupapp.ViewUser_Information.View_UserInformation;
import com.example.rito.groupapp.old.CRN;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/**
 * This class allows the user to see a list of all courses they are registered
 * in. It also allows the user to remove any of their registered courses.
 * Future plans exist o include more course information in this activity. .
 *
 * @author   Gobii, Rito, Yuhao
 * @since    2018-07-08
 */
public class MyCoursesActivity extends AppCompatActivity {
	//MyCoursesActivity
	private ListView lv;
	private ArrayList<CRN> registeredCourses = new ArrayList<>();
	private ArrayList<CRN> deletedCourses = new ArrayList<>();
	private String username = MainActivity.currentUser.getUsername();
	private Term currentTerm;
	private Toolbar hdrToolBar;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	//refactor toolbar for i3
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.go_to_course:
				startActivity(new Intent(MyCoursesActivity.this, CourseFilterActivity.class));
				return true;

			case R.id.go_to_calender:
				startActivity(new Intent(MyCoursesActivity.this, CalendarView.class));
				return true;

			case R.id.go_to_add_crn:
				startActivity(new Intent(MyCoursesActivity.this, CourseRegistration.class));
				return true;

			case R.id.go_to_view_remove_registered:
				startActivity(new Intent(MyCoursesActivity.this, MyCoursesActivity.class));
				return true;
			case R.id.view_user_information:
				startActivity(new Intent(MyCoursesActivity.this, View_UserInformation.class));

			case R.id.log_out:
				startActivity(new Intent(MyCoursesActivity.this, Logout_Activity.class));
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
							Log.d("debug.print", "VRCR, navigation_back:");
							populateTerm();
							return true;

						case R.id.navigation_deregister:
							Log.d("debug.print", "VRCR, navigation_deregister:");

							if (deletedCourses.size() > 0){
								deregisterCourses();
								populateTerm();
								return true;
							}
							return true;

					}

					return false;

				}
			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("debug.print", "VRCR, onCreate:");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_courses);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(hdrToolBar);

		Log.d("debug.print", "VRCR, populateRegisteredCourses CALL:");
		populateTerm();
	}

	public void populateTerm() {
		currentTerm = null;
		registeredCourses.clear();
		deletedCourses.clear();

		final FirebaseListAdapter<Term> firebaseAdapter;
		Database db = new Database("TERM");
		lv = findViewById(R.id.listView2);
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
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Term term = (Term) firebaseAdapter.getItem(position);
				currentTerm = term;
				populateRegisteredCourses();
			}
		});
	}

	public void populateRegisteredCourses(){

		Log.d("debug.print", "VRCR, populateRegisteredCourses START:");

		Database db = new Database("STUDENT/" + username);
		db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Log.d("debug.print", "VRCR, onDataChange START:");
				CharSequence text;
				Context context = getApplicationContext();
				int duration = Toast.LENGTH_LONG;

				if (dataSnapshot.child("registration").exists()){
					Student std = dataSnapshot.getValue(Student.class);
					String crn;
					for (String k : std.getRegistration().keySet()){
						if (std.getRegistration().get(k)){
							Database dbCRN = new Database("CRN/" + k);
							Log.d("debug.print", "VRCR, onDataChange dbref patch: " + dbCRN.dbRef.toString());

							dbCRN.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									CRN crn = dataSnapshot.getValue(CRN.class);

									boolean sel = false;
									ArrayList<CRN> newRegisteredCourses = new ArrayList<>();

									Log.d("debug.print", "VRCR, populateCurrentSelection " +
											"crn:" + crn);
									Log.d("debug.print", "VRCR, populateCurrentSelection " +
											"registeredCourses:" + registeredCourses);

									if (!(crn == null)){
										for (CRN x : registeredCourses) {
											if(!(x.equals(crn))){
												newRegisteredCourses.add(x);
											} else {
												sel = true;
											}
										}

										registeredCourses = newRegisteredCourses;

										if (!(sel) && (crn.getTerm_code().equals(currentTerm.getTerm_code()))){
											registeredCourses.add(crn);
										}
									}

									populateCurrentSelection();
								}

								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									Log.d("debug.print", "The read failed: " + databaseError.getCode());
								}
							});
						}
					}
				} else {
					populateCurrentSelection();
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.d("debug.print", "The read failed: " + databaseError.getCode());
			}
		});
	}

	public void populateCurrentSelection(){
		Log.d("debug.print", "VRCR, populateCurrentSelection START:");
		Log.d("debug.print", "VRCR, populateCurrentSelection 1:");
		CharSequence text;
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_LONG;

		if (registeredCourses.size() == 0) {
			text = "You are not registered for any courses during this term.";
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}

		lv = findViewById(R.id.listView2);
		Log.d("debug.print", "VRCR, populateCurrentSelection 2:");

		lv.setAdapter(new ArrayAdapter<CRN>(
				this, R.layout.item_registration , registeredCourses){

			@Override
			public View getView (int position, View view, ViewGroup parent){
				Log.d("debug.print", "VRCR, populateCurrentSelection 3:");

				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_registration, parent,
							false);
				}

				Log.d("debug.print", "VRCR, populateCurrentSelection 4:");
				CRN crnObj = getItem(position);
				TextView ccode = (TextView) view.findViewById(R.id.course_code);
				TextView snumber = (TextView) view.findViewById(R.id.section_number);
				TextView stype = (TextView) view.findViewById(R.id.section_type);
				TextView tcode = (TextView) view.findViewById(R.id.term_code);
				TextView crn = (TextView) view.findViewById(R.id.crn);

				Log.d("debug.print", "VRCR, populateCurrentSelection 5:");
				ccode.setText(String.format("Course Code:%s", crnObj.getCourse_code()));
				snumber.setText(String.format("Section Number:%s", crnObj.getSection_number()));
				stype.setText(String.format("Section Type:%s", crnObj.getSection_type()));
				tcode.setText(String.format("Term Code:%s", crnObj.getTerm_code()));
				crn.setText(String.format("CRN:%s", crnObj.getCrn()));

				return view;
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.d("debug.print", "VRCR, populateCurrentSelection 6:");
				CRN crnObj = (CRN) parent.getItemAtPosition(position);
				boolean sel = false;
				ArrayList<CRN> newDeletedCourses = new ArrayList<>();

				for (CRN x : deletedCourses) {
					if(!(x.equals(crnObj))){
						newDeletedCourses.add(x);
					} else {
						sel = true;
					}
				}
				deletedCourses = newDeletedCourses;

				if (sel){
					view.setBackgroundResource(R.color.transparent);
				} else {
					view.setBackgroundResource(R.color.colorSelectedRemoved);
					deletedCourses.add(crnObj);
				}

			}
		});
		lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

	}

	public void deregisterCourses(){
		Log.d("debug.print", "VRCR, deregisterCourses START:");

		if(deletedCourses.size() > 0){
			for (CRN x : deletedCourses){
				Database db = new Database();
				db.addRemoveCourse(x.getCrn(), username, false);
			}
		}
	}
}