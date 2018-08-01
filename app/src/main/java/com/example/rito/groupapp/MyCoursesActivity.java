package com.example.rito.groupapp;

import android.app.Dialog;
import android.app.ProgressDialog;
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
	private ProgressDialog msg;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//main navigation menu
		msg.setTitle("Loading");
		msg.setMessage("Loading, please wait...");
		msg.show();

		switch (item.getItemId()) {
			case R.id.go_to_course:
				msg.hide();
				startActivity(new Intent(MyCoursesActivity.this, CourseFilterActivity.class));
				return true;

			case R.id.go_to_calender:
				msg.hide();
				startActivity(new Intent(MyCoursesActivity.this, CalendarView.class));
				return true;

			case R.id.go_to_add_crn:
				msg.hide();
				startActivity(new Intent(MyCoursesActivity.this, CourseRegistration.class));
				return true;

			case R.id.go_to_view_remove_registered:
				msg.hide();
				startActivity(new Intent(MyCoursesActivity.this, MyCoursesActivity.class));
				return true;
			case R.id.view_user_information:
				msg.hide();
				startActivity(new Intent(MyCoursesActivity.this, View_UserInformation.class));
				return true;
			case R.id.log_out:
				msg.hide();
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
					msg.setTitle("Loading");
					msg.setMessage("Loading, please wait...");
					msg.show();

					String title, msg_str;
					int option;
					switch (item.getItemId()) {
						case R.id.navigation_back:
							populateTerm();
							msg.hide();
							return true;

						case R.id.navigation_deregister:
							title = "Deregister Selected Courses";
							msg_str = "You are about to deregister for all selected courses. " +
									"Please " +
									"confirm to continue.";
							option = 0;
							popupMsg(title, msg_str, option);
							msg.hide();
							return true;
					}
					msg.hide();
					return false;
				}
			};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_courses);

		msg = new ProgressDialog(MyCoursesActivity.this);
		msg.setCancelable(false);
		msg.setTitle("Loading");
		msg.setMessage("Loading, please wait...");
		msg.show();

		Log.d("debug.print","onCreate MyCoursesActivity");
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		hdrToolBar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(hdrToolBar);
		populateTerm();
		msg.hide();
	}

	//populate a list of terms
	public void populateTerm() {
		msg.setTitle("Populating");
		msg.setMessage("Populating tables, please wait...");
		msg.show();
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
				msg.setTitle("Populating");
				msg.setMessage("Populating tables, please wait...");
				msg.show();
				TextView termRow = (TextView)v.findViewById(android.R.id.text1);
				termRow.setText(model.toString());
				msg.hide();

			}
		};
		lv.setAdapter(firebaseAdapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				msg.setTitle("Populating");
				msg.setMessage("Populating tables, please wait...");
				msg.show();
				Term term = (Term) firebaseAdapter.getItem(position);
				currentTerm = term;
				populateRegisteredCourses();
				msg.hide();
			}
		});
		msg.hide();
	}

	//activity specific popup messages
	public void popupMsg(String t, String m, int o){
		msg.setTitle("Loading");
		msg.setMessage("Loading, please wait...");
		msg.show();
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
						msg.setTitle("Loading");
						msg.setMessage("Loading, please wait...");
						msg.show();
						if (deletedCourses.size() > 0){
							deregisterCourses();
							populateTerm();
						}
						msg.hide();
					}
				});
				break;
			default:
				msg.hide();
				popUpMsgButtonOK.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		}
		msg.hide();
		dialog.show();
	}

	//populate a listed of registered courses for the selected term
	public void populateRegisteredCourses(){
		msg.setTitle("Populating");
		msg.setMessage("Populating tables, please wait...");
		msg.show();

		Database db = new Database("STUDENT/" + username);
		db.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				msg.setTitle("Populating");
				msg.setMessage("Populating tables, please wait...");
				msg.show();

				if (dataSnapshot.child("registration").exists()){
					User std = dataSnapshot.getValue(User.class);
					String crn;
					for (String k : std.getRegistration().keySet()){
						if (std.getRegistration().get(k)){
							Database dbCRN = new Database("CRN_DATA/" + k);

							dbCRN.getDbRef().addListenerForSingleValueEvent(new ValueEventListener() {
								@Override
								public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
									msg.setTitle("Populating");
									msg.setMessage("Populating tables, please wait...");
									msg.show();
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
									msg.hide();
								}

								@Override
								public void onCancelled(@NonNull DatabaseError databaseError) {
									msg.hide();
									Log.d("debug.print", "The read failed: " + databaseError.getCode());
								}
							});
						}
					}
				} else {
					populateCurrentSelection();
				}
				msg.hide();
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.d("debug.print", "The read failed: " + databaseError.getCode());
			}
		});
		msg.hide();
	}


	public void populateCurrentSelection(){
		msg.setTitle("Populating");
		msg.setMessage("Populating tables, please wait...");
		msg.show();
		lv = findViewById(R.id.listView);
		lv.setAdapter(new ArrayAdapter<CRN_Data>(
				this, R.layout.item_registration , registeredCourses){

			@Override
			public View getView (int position, View view, ViewGroup parent){
				msg.setTitle("Populating");
				msg.setMessage("Populating tables, please wait...");
				msg.show();
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
				msg.hide();
				return view;
			}
		});

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			// onItemClick method is called everytime a user clicks an item on the list
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				msg.setTitle("Loading");
				msg.setMessage("Loading, please wait...");
				msg.show();
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
				msg.hide();
			}
		});
		lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		msg.hide();
	}

	//used to deregister all selected courses
	public void deregisterCourses(){
		msg.setTitle("Deregistration");
		msg.setMessage("Deregistering course, please wait...");
		msg.show();
		if(deletedCourses.size() > 0){
			for (CRN_Data x : deletedCourses){
				Database db = new Database();
				db.addRemoveCourse(x.getCrn(), username, false);
			}
		}
		msg.hide();
	}
}
