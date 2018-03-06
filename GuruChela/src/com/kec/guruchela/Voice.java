package com.kec.guruchela;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class Voice extends SherlockActivity implements OnClickListener,
		OnItemClickListener {
	ListView lv;
	static final int check = 1111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.x_sound);
		lv = (ListView) findViewById(R.id.lvVoiceReturn);
		ImageButton b = (ImageButton) findViewById(R.id.bVoice);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak up!");
		startActivityForResult(i, check);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == check && resultCode == RESULT_OK) {
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			// lv.setAdapter(new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, results));
			// lv.setOnItemClickListener(this);

			Intent i = null;
			boolean found = false;
			for (String intentList : results) {
				if (intentList.equalsIgnoreCase("Email")
						|| intentList.equalsIgnoreCase("email")) {
					i = new Intent(Voice.this, Email.class);
					found = true;
					break;
				} else if (intentList.equalsIgnoreCase("Camera")
						|| intentList.equalsIgnoreCase("camera")) {
					i = new Intent(Voice.this, FCamera.class);
					found = true;
					break;
				}
				/*
				else if (intentList.equalsIgnoreCase("Map")
						|| intentList.equalsIgnoreCase("map")) {
					Intent intent = new Intent(
							android.content.Intent.ACTION_VIEW,
							Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
					intent.setClassName("com.google.android.apps.maps",
							"com.google.android.maps.MapsActivity");

					found = true;
					break;
				}*/
				else if (intentList.equalsIgnoreCase("Share")
						|| intentList.equalsIgnoreCase("share")) {
					i = new Intent(Voice.this, DropboxChooser.class);
					found = true;
					break;
				}
			}
			if (found) {
				startActivity(i);
				finish();
			} else {
				Toast.makeText(this, "No Activity found", Toast.LENGTH_LONG)
						.show();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		/*
		 * String item = lv.getSelectedItem().toString();
		 * if(item.equals("Camera")||item.equals("camera")){ //Intent o = new
		 * Intent(Voice.this, FCamera.class); //startActivity(o); }
		 */
	}
}
