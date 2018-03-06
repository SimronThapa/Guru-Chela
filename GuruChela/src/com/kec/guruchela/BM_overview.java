package com.kec.guruchela;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BM_overview extends SherlockActivity {
	String sub;
	String totalL;
	String bunkedL;
	String percent;
	TextView totalsub;
	TextView totallect;
	TextView bunkedlect;
	TextView current;
	
	TextView marks;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		sub = extras.getString("sub");
		totalL = extras.getString("totalL");
		bunkedL = extras.getString("bunkedL");
		percent = extras.getString("percent");
		setContentView(R.layout.bm_overview);

		TableLayout tl = (TableLayout) findViewById(R.id.table);
		TableRow row1 = (TableRow) findViewById(R.id.tableRow1);
		TableRow row2 = (TableRow) findViewById(R.id.tableRow2);
		TableRow row3 = (TableRow) findViewById(R.id.tableRow3);
		TableRow row4 = (TableRow) findViewById(R.id.tableRow4);

		 totalsub = (TextView) findViewById(R.id.tvtotalsubject);
		 totallect = (TextView) findViewById(R.id.tvtotallec);
		 bunkedlect = (TextView) findViewById(R.id.tvbunkedlec);
		 current = (TextView) findViewById(R.id.tvcurrent);
		
		 marks = (TextView) findViewById(R.id.tvmarks);
		
		ImageButton info = (ImageButton) findViewById(R.id.ibinfo);
		Button details = (Button) findViewById(R.id.ibdetails);

		ProgressBar pb = (ProgressBar) findViewById(R.id.improgres);

		info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog ask = new Dialog(BM_overview.this);
				ask.setContentView(R.layout.bm_dialog);
				ask.setTitle("Information!");
				Button ok = (Button) ask.findViewById(R.id.b_bm_ok);
				ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ask.dismiss();
					}
				});
				ask.show();
			}
		});

		details.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BM_overview.this, BM_main.class);
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_help, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_help:
			final Dialog ask = new Dialog(BM_overview.this);
			ask.setContentView(R.layout.bm_questions);
			ask.setTitle("Question and Anwers!");
			Button ok = (Button) ask.findViewById(R.id.breturn);
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					ask.dismiss();
				}
			});
			ask.show();

			return true;
		case R.id.menu_help_save:
			boolean didItWork = true;
			try {
				float Ftotal = Float.parseFloat(totalL);
				float Fbunked = Float.parseFloat(bunkedL);
				float Fpercent = Float.parseFloat(percent);
				float Fbunkedpercent = (Fbunked / Ftotal) * 100;
				float FCurrentAtt = 100 - Fbunkedpercent;
				String CurrAtt = ("" + FCurrentAtt);
				float Feff = FCurrentAtt /Fbunkedpercent;
				String eff = ("" + Feff);
				totalsub.setText(sub);
				totallect.setText(totalL);
				bunkedlect.setText(bunkedL);
				current.setText(percent);
				marks.setText(eff);
				BunkManagerDb db = new BunkManagerDb(BM_overview.this);
				db.open();
				db.createReport(sub, totalL, bunkedL, percent, CurrAtt, eff);
				db.close();
			} catch (Exception e) {
				didItWork = false;
				String error = e.toString();
				Toast.makeText(BM_overview.this, error,
						Toast.LENGTH_LONG).show();
			} finally {
				if(didItWork){
					Toast.makeText(
							BM_overview.this,
							"Success! Your entry is saved to database!",
							Toast.LENGTH_LONG).show();
				}
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}