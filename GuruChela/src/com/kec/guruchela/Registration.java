package com.kec.guruchela;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;

public class Registration extends SherlockActivity implements
		OnItemSelectedListener,
		android.widget.AdapterView.OnItemSelectedListener {
	EditText fname;
	EditText uname;
	EditText pw;
	EditText cpw;
	Spinner spin;
	TextView info;
	EditText Acode;
	ImageButton Rsave;
	String signAs[] = { "Sign in as:"," Guru", " Chela" };
	// guru-dialogbox
	EditText Institution;
	EditText Subject;
	EditText Website;
	Spinner Department;
	ImageButton Save;
	ImageButton Skip;
	ImageButton inst;
	ImageButton sub;
	ImageButton web;
	String deplist[] = { "Computer", "Electronics", "Civil", "Electrical",
			"Mechanical" };
	// chela-dialogbox
	EditText CInstitution;
	EditText CWebsite;
	Spinner CDepartment;
	ImageButton CSave;
	ImageButton CSkip;
	ImageButton Cweb;
	final Context context = this;
	int flag = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.xregistration);
		setTitle("Registration!");
		spin = (Spinner) findViewById(R.id.sRegister);
		fname = (EditText) findViewById(R.id.etFullName);
		uname = (EditText) findViewById(R.id.etUName);
		pw = (EditText) findViewById(R.id.etPassword);
		cpw = (EditText) findViewById(R.id.etCPassword);
		info = (TextView) findViewById(R.id.tvInfo);
		Acode = (EditText) findViewById(R.id.etAcode);
		Rsave = (ImageButton) findViewById(R.id.ibSave);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Registration.this, android.R.layout.simple_spinner_item, signAs);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(this);
		Rsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog Gudetail = new Dialog(context);
				Gudetail.setContentView(R.layout.xcheladetail);
				Gudetail.setTitle("Create Your Profile!");
				Institution = (EditText) Gudetail
						.findViewById(R.id.etInstitution);
				Subject = (EditText) Gudetail.findViewById(R.id.etSubject);
				Website = (EditText) Gudetail.findViewById(R.id.etWebsite);
				Department = (Spinner) Gudetail.findViewById(R.id.sDepartment);
				Save = (ImageButton) Gudetail.findViewById(R.id.bSave);
				Skip = (ImageButton) Gudetail.findViewById(R.id.ibSkip);
				inst = (ImageButton) Gudetail.findViewById(R.id.ibInstitution);
				sub = (ImageButton) Gudetail.findViewById(R.id.ibSubject);
				web = (ImageButton) Gudetail.findViewById(R.id.ibWebsite);
				flag = 1;
				Skip.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Registration.this,NavigationDrawer.class);
						startActivity(i);
					}
				});
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			Registration.this, android.R.layout.simple_spinner_item,
				 deplist);
				Department.setAdapter(adapter);
				Department.setOnItemSelectedListener(Registration.this);
				Gudetail.show();
			}
		});
		// guru-dialog

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		if (flag == 0){
		int position = spin.getSelectedItemPosition();
		switch (position) {
		case 0:
			break;
		case 1:
			Toast.makeText(Registration.this,
					"Fill up additional Information!", Toast.LENGTH_LONG)
					.show();
			info.setEnabled(true);
			Acode.setFocusable(true);
			Acode.setCursorVisible(true);
			Acode.setClickable(true);
			Acode.setFocusableInTouchMode(true);
			Rsave.setClickable(true);

			break;
		case 2:
			final Dialog Chdetail = new Dialog(context);
			Chdetail.setContentView(R.layout.xgurudetail);
			Chdetail.setTitle("Create Your Profile!");
			CInstitution = (EditText) findViewById(R.id.etGInstitution);
			CWebsite = (EditText) Chdetail.findViewById(R.id.etGWebsite);
			CDepartment = (Spinner) Chdetail.findViewById(R.id.sGDepartment);
			CSave = (ImageButton) Chdetail.findViewById(R.id.bGSave);
			CSkip = (ImageButton) Chdetail.findViewById(R.id.ibGSkip);
			Cweb = (ImageButton) Chdetail.findViewById(R.id.ibGWebsite);
			flag = 2;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			Registration.this, android.R.layout.simple_spinner_item,
			deplist);
			CDepartment.setAdapter(adapter);
			CDepartment.setOnItemSelectedListener(Registration.this);
			
			CSkip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Registration.this,Navigation_Chela.class);
					startActivity(i);
				}
			});
			Chdetail.show();
			break;
		}
		}
		else if(flag==1){
			// guru ko database ma entry
			int position = Department.getSelectedItemPosition();
			switch(position){
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			}
		}else{
			//chela ko db ma entry
			int position = CDepartment.getSelectedItemPosition();
			switch(position){
			case 0:
				
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		if(flag ==0){
			
		}else if(flag==1){
		info.setEnabled(false);
		Acode.setFocusable(false);
		Acode.setCursorVisible(false);
		Acode.setClickable(false);
		Acode.setFocusableInTouchMode(false);
		Rsave.setClickable(false);
		}else{
			
		}
	}

	@Override
	public void onItemSelected(IcsAdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNothingSelected(IcsAdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
