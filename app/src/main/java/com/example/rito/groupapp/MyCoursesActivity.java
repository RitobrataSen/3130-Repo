package com.example.rito.groupapp;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
	private ListView lv;
	private ArrayList<CRN_Data> registeredCourses = new ArrayList<>();
	private ArrayList<CRN_Data> deletedCourses = new ArrayList<>();
	private String username = MainActivity.currentUser.getUsername();
	private Term currentTerm;
	private Toolbar hdrToolBar;
	private Context context = this;

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
				return true;

			case R.id.log_out:
				startActivity(new Intent(MyCoursesActivity.this, Logout_Activity.class));
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
					String title, msg;
					int option;
					switch (item.getItemId()) {
						case R.id.navigation_back:
							populateTerm();
							return true;

						case R.id.navigation_deregister:
							title = "Deregister Selected Courses";
							msg = "You are about to deregister for all selected courses. Please " +
									"confirm to continue.";
							option = 0;
							popupMsg(title, msg, option);
							return true;
					}
					return false;
				}
			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_courses);
		Log.d("debug.print","onCreate MyCoursesActivity");
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(hdrToolBar);
		populateTerm();
	}

	//populate a list of terms
	public void populateTerm() {
		currentTerm = null;
		registeredCourses.clear();
		deletedCourses.clear();

		final FirebaseListAdapter<Term> firebaseAdapter;
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
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Term term = (Term) firebaseAdapter.getItem(position);
				currentTerm = term;
				populateRegisteredCourses();
			}
		});
	}

	//activity specific popup messages
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
						if (deletedCourses.size() > 0){
							deregisterCourses();
							populateTerm();
						}
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

	//populate a listed of registered courses for the selected term
	public void populateRegisteredCourses(){

		Database db = new Database("STUDENT/" + username);
		db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				if (dataSnapshot.child("registration").exists()){
					User std = dataSnapshot.getValue(User.class);
					String crn;
					for (String k : std.getRegistration().keySet()){
						if (std.getRegistration().get(k)){
							Database dbCRN = new Database("CRN_DATA/" + k);

							dbCRN.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									CRN_Data crn = dataSnapshot.getValue(CRN_Data.class);

									boolean sel = false;
									ArrayList<CRN_Data> newRegisteredCourses = new ArrayList<>();

									if (!(crn == null)){
										for (CRN_Data x : registeredCourses) {
											if(!(x.equals(crn))){
												newRegisteredCourses.add(x);
											} else {
												sel = true;
											}
										}

										registeredCourses = newRegisteredCourses;

										if (!(sel) && (crn.getTerm_Code().equals(currentTerm.getTerm_code()
										))){
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

		lv = findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<CRN_Data>(
				this, R.layout.item_registration , registeredCourses){

			@Override
			public View getView (int position, View view, ViewGroup parent){

				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_registration, parent,
							false);
				}

				CRN_Data crnObj = getItem(position);
				TextView ccode = (TextView) view.findViewById(R.id.course_code);
				TextView snumber = (TextView) view.findViewById(R.id.section_number);
				TextView stype = (TextView) view.findViewById(R.id.section_type);
				TextView tcode = (TextView) view.findViewById(R.id.term_code);
				TextView crn = (TextView) view.findViewById(R.id.crn);

				ccode.setText(String.format("Course Code:%s", crnObj.getCourse_Code()));
				snumber.setText(String.format("Section Number:%s", crnObj.getSection_Number()));
				stype.setText(String.format("Section Type:%s", crnObj.getSection_Type()));
				tcode.setText(String.format("Term Code:%s", crnObj.getTerm_Code()));
				crn.setText(String.format("CRN:%s", crnObj.getCrn()));

				return view;
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				CRN_Data crnObj = (CRN_Data) parent.getItemAtPosition(position);
				boolean sel = false;
				ArrayList<CRN_Data> newDeletedCourses = new ArrayList<>();

				for (CRN_Data x : deletedCourses) {
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

	//used to deregister all selected courses
	public void deregisterCourses(){

		if(deletedCourses.size() > 0){
			for (CRN_Data x : deletedCourses){
				Database db = new Database();
				db.addRemoveCourse(x.getCrn(), username, false);
			}
		}
	}
}
