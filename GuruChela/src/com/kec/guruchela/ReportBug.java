package com.kec.guruchela;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;

public class ReportBug extends SherlockActivity implements OnItemSelectedListener, OnClickListener {
	ImageButton bugSend;
	EditText bugDescription;
	Spinner bugType;
	String intro;
	String bugAt[] = { "AI Chat", "Browser", "Profile", "Home","Share","Speech Detector","Schedule",
	"Upload/Download","Settings","Mail","Memo/Reminders","Map functions" };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_reportbug);
		bugDescription = (EditText) findViewById(R.id.etDescription_bug);
		bugSend = (ImageButton) findViewById(R.id.ibBug);
		bugType = (Spinner) findViewById(R.id.sbug);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ReportBug.this, android.R.layout.simple_spinner_item, bugAt);
		bugType.setAdapter(adapter);
		bugType.setOnItemSelectedListener(this);
		bugSend.setOnClickListener(this);
		
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		 intro = bugType.getItemAtPosition(arg2).toString();
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String emailaddress[] = { "simi_niks@live.com" };
		String subject = ("Bug at " + intro);
		String message = bugDescription.getText().toString();
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(emailIntent);
	}

}
