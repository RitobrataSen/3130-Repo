package com.example.rito.groupapp;

import android.content.ServiceConnection;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/*
will need to add in the following:
- activity navigation
	- go back / cancel
	- confirm delete
	- activity titles
-
 */
public class ViewRemoveCourseRegistrationActivity extends AppCompatActivity {
	private ListView lv;
	private ArrayList<CRN> registeredCourses = new ArrayList<>();
	private ArrayList<CRN> deletedCourses = new ArrayList<>();
	private String username = "Student1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_remove_course_registration);

		final Button button = findViewById(R.id.deregisterButton);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deregisterCourses();
				registeredCourses.clear();
				deletedCourses.clear();

			}
		});

		populateRegisteredCourses();
	}

	public void populateRegisteredCourses(){
		Database db = new Database("STUDENT/" + username);
		db.getDbRef().addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				Student std = dataSnapshot.getValue(Student.class);
				String crn;
				for (String k : std.getRegistration().keySet()){
					if (std.getRegistration().get(k)){
						Database dbCRN = new Database("CRN/" + k);
						dbCRN.getDbRef().addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
								CRN crn = dataSnapshot.getValue(CRN.class);

								boolean sel = false;
								ArrayList<CRN> newRegisteredCourses = new ArrayList<>();

								for (CRN x : registeredCourses) {
									if(!(x.equals(crn))){
										newRegisteredCourses.add(x);
									} else {
										sel = true;
									}
								}

								registeredCourses = newRegisteredCourses;

								if (sel){
								} else {
									registeredCourses.add(crn);
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
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				Log.d("debug.print", "The read failed: " + databaseError.getCode());
			}
		});
	}

	public void populateCurrentSelection(){
		lv = findViewById(R.id.listView2);
		lv.setAdapter(new ArrayAdapter<CRN>(
				this, R.layout.item_registration , registeredCourses){
			@Override
			public View getView (int position, View view, ViewGroup parent){
				if (view == null) {
					view = LayoutInflater.from(getContext()).inflate(R.layout.item_registration, parent,
							false);
				}

				CRN crnObj = getItem(position);
				TextView ccode = (TextView) view.findViewById(R.id.course_code);
				TextView snumber = (TextView) view.findViewById(R.id.section_number);
				TextView stype = (TextView) view.findViewById(R.id.section_type);
				TextView tcode = (TextView) view.findViewById(R.id.term_code);
				TextView crn = (TextView) view.findViewById(R.id.crn);

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
		if(deletedCourses.size() > 0){
			for (CRN x : deletedCourses){
				Database db = new Database();
				db.addRemoveCourse(x.getCrn(), username, false);
			}
		}
	}
}
