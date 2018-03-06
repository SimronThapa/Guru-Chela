package com.kec.guruchela;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class Email extends SherlockActivity implements OnClickListener {
	EditText personsEmail, intro, outro;
	String emailAdd, beginning, out;
	ImageButton sendEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_email);
		this.setTitle("Email");
		initializeVars();
		sendEmail.setOnClickListener(this);
	}

	private void initializeVars() {
		// TODO Auto-generated method stub
		personsEmail = (EditText) findViewById(R.id.etEmails);
		intro = (EditText) findViewById(R.id.etIntro);

		outro = (EditText) findViewById(R.id.etOutro);
		sendEmail = (ImageButton) findViewById(R.id.bSentEmail);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated();
		String emailaddress[] = { emailAdd };

		String message = "" + out;

		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailaddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, beginning);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
		startActivity(emailIntent);
	}

	private void convertEditTextVarsIntoStringsAndYesThisIsAMethodWeCreated() {
		// TODO Auto-generated method stub
		emailAdd = personsEmail.getText().toString();
		beginning = intro.getText().toString();
		out = outro.getText().toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.menu_attach, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_attachment:

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
