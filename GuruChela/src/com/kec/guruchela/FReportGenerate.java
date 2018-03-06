package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class FReportGenerate extends SherlockActivity {
	LinearLayout hr;
	EditText Sname, Sroll, Satt, Tatt, Sasg, Tasg, Sast, Tast;
	String newString, sfaculty, ssemester, Stot, Tot,sroll;
	TextView fromDb;
	Button bfaculty, bsemester, bpublish;
	float aa, bb, cc, tot;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		newString = extras.getString("STRING_I_NEED");
		tot = extras.getFloat("total");
		aa = extras.getFloat("a");
		bb = extras.getFloat("b");
		cc = extras.getFloat("c");
		setContentView(R.layout.x_student_records);
		fromDb = (TextView) findViewById(R.id.tvFromDb);
		hr = (LinearLayout) findViewById(R.id.llrecords);
		hr.setDrawingCacheEnabled(true);

		Sname = (EditText) findViewById(R.id.etstname);
		Sroll = (EditText) findViewById(R.id.etroll);
		// Sfaculty = (EditText) findViewById(R.id.etfaculty);
		bfaculty = (Button) findViewById(R.id.b_stud_faculty);
		bsemester = (Button) findViewById(R.id.b_stud_sem);
		bpublish = (Button) findViewById(R.id.b_publish);
		Satt = (EditText) findViewById(R.id.etStudentAttendance);
		Tatt = (EditText) findViewById(R.id.etTotalAttendance);
		Sasg = (EditText) findViewById(R.id.etStudentAssignment);
		Tasg = (EditText) findViewById(R.id.etTotalAssignment);
		Sast = (EditText) findViewById(R.id.etStudentAssestment);
		Tast = (EditText) findViewById(R.id.etTotalAssestment);
		bfaculty.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog ACode = new Dialog(context);
				ACode.setContentView(R.layout.registration_faculty);
				ACode.setTitle("Select your faculty!");

				Button facultyOK = (Button) ACode
						.findViewById(R.id.b_facultyOK);
				facultyOK.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						switch (((RadioGroup) ACode
								.findViewById(R.id.rg_faculty))
								.getCheckedRadioButtonId()) {
						case R.id.rbComputer:
							sfaculty = "Computer";
							break;
						case R.id.rbElectronics:
							sfaculty = "Electronics";
							break;
						case R.id.rbElectrical:
							sfaculty = "Electrical";
							break;
						case R.id.rbMechanical:
							sfaculty = "Mechanical";
							break;
						case R.id.rbCivil:
							sfaculty = "Civil";
							break;
						default:
							throw new RuntimeException("Unexpected selection!!");
						}
						ACode.dismiss();
					}
				});
				ACode.show();
			}
		});
		bsemester.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog ACode = new Dialog(context);
				ACode.setContentView(R.layout.registration_semester);
				ACode.setTitle("Select your semester!");

				Button semOK = (Button) ACode.findViewById(R.id.b_semesterOK);
				semOK.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						switch (((RadioGroup) ACode
								.findViewById(R.id.rg_semester))
								.getCheckedRadioButtonId()) {
						case R.id.rb1sem:
							ssemester = "1";
							break;
						case R.id.rb2sem:
							ssemester = "2";
							break;
						case R.id.rb3sem:
							ssemester = "3";
							break;
						case R.id.rb4sem:
							ssemester = "4";
							break;
						case R.id.rb5sem:
							ssemester = "5";
							break;
						case R.id.rb6sem:
							ssemester = "6";
							break;
						case R.id.rb7sem:
							ssemester = "7";
							break;
						case R.id.rb8sem:
							ssemester = "8";
							break;
						default:
							throw new RuntimeException("Unexpected selection!!");
						}
						ACode.dismiss();
					}
				});
				ACode.show();
			}
		});
		bpublish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (newString.length() < 1 || Stot.length() < 1
						|| Tot.length() < 1 || sroll.length() < 1
						|| sfaculty.length() < 1 || ssemester.length() < 1) {
					Toast.makeText(
							FReportGenerate.this,
							"please enter value to all the fields and save the data before publishing!",
							Toast.LENGTH_LONG).show();
				} else {
					new InsertDB().execute(newString, Stot, Tot, sfaculty, ssemester, sroll);

				}
			}
		});

		this.setTitle("Report");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.save_share, menu);
		MenuItem actionItem = menu.findItem(R.id.menu_shareshare);
		ShareActionProvider actionProvider = (ShareActionProvider) actionItem
				.getActionProvider();
		actionProvider
				.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		actionProvider.setShareIntent(createShareIntent());
		return true;
	}

	private Intent createShareIntent() {
		// startActivity(Intent.createChooser(share, "Share Image"));

		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		Uri uri = Uri.fromFile(getFileStreamPath("student_records.png"));
		shareIntent.putExtra(Intent.EXTRA_STREAM,
				Uri.parse("file:///sdcard/DCIM/student_records.png"));
		// startActivity(Intent.createChooser(createShareIntent(),
		// "Share Image"));
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {

		case R.id.menu_savesave:
			boolean didItWork = true;
			try {
				String sname = Sname.getText().toString();
				sroll = Sroll.getText().toString();
				// String sfaculty = Sfaculty.getText().toString();
				String satt = Satt.getText().toString();
				String tatt = Tatt.getText().toString();
				String sasg = Sasg.getText().toString();
				String tasg = Tasg.getText().toString();
				String sast = Sast.getText().toString();
				String tast = Tast.getText().toString();
				float a = Float.parseFloat(satt) / Float.parseFloat(tatt);
				float b = Float.parseFloat(sasg) / Float.parseFloat(tasg);
				float c = Float.parseFloat(sast) / Float.parseFloat(tast);
				float d = a * aa + b * bb + c * cc;
				String Tsatt = ("" + a);
				String Tsasg = ("" + b);
				String Tsast = ("" + c);
				Tot = ("" + d);
				String Saa = ("" + aa);
				String Sbb = ("" + bb);
				String Scc = ("" + cc);
				Stot = ("" + tot);
				fromDb.setText(Tot);
				// log testing
				Log.d("test0", newString);
				Log.d("test1", Stot);
				Log.d("test2", Saa);
				Log.d("test3", Sbb);
				Log.d("test4", Scc);
				Log.d("test5", sname);
				Log.d("test6", sroll);
				Log.d("test7", sfaculty);
				Log.d("test8", Tsatt);
				Log.d("test9", Tsasg);
				Log.d("test10", Tsast);
				Log.d("test11", Tot);

				StudentReportDb db = new StudentReportDb(FReportGenerate.this);
				db.open();
				db.createReport(newString, Stot, Saa, Sbb, Scc, sname, sroll,
						sfaculty, Tot, Tsatt, Tsasg, Tsast);
				db.close();

			} catch (Exception e) {
				didItWork = false;
				String error = e.toString();
				Toast.makeText(FReportGenerate.this, error, Toast.LENGTH_LONG)
						.show();

			} finally {
				if (didItWork) {
					Toast.makeText(FReportGenerate.this,
							"Success! Your entry is saved to database!",
							Toast.LENGTH_LONG).show();
				}
			}

			getScreen();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	private void getScreen() {
		// TODO Auto-generated method stub

		Bitmap bitmap = hr.getDrawingCache();
		File file = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
						+ "/" + "student_records" + ".png");
		try {
			file.createNewFile();
			FileOutputStream ostream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, ostream);
			ostream.close();
			Toast.makeText(FReportGenerate.this,
					"" + "Your Report Card is saved!", Toast.LENGTH_LONG)
					.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Creating AsynTask for Insert into Database
		private class InsertDB extends AsyncTask<String, String, String> {

			private String resp = "";

			@Override
			protected String doInBackground(String... params) {
				// publishProgress("Reading Data..."); // Calls onProgressUpdate()
				String url = "http://www.toolittletoobig.com/guruchela_php/insertMarks_gc.php";
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);

				try {
					// Add your data

					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs
							.add(new BasicNameValuePair("subName", params[0]));
					nameValuePairs
							.add(new BasicNameValuePair("subFull", params[1]));
					nameValuePairs
							.add(new BasicNameValuePair("subPass", params[2]));
					nameValuePairs
							.add(new BasicNameValuePair("faculty", params[3]));
					nameValuePairs.add(new BasicNameValuePair("classname",
							params[4]));
					nameValuePairs.add(new BasicNameValuePair("rollnumber",
							params[5]));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

					HttpResponse response = httpclient.execute(httppost);

					// Reading data from insert.php
					InputStream is = response.getEntity().getContent();

					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));

					resp = br.readLine();
					// Execute HTTP Post Request

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					resp = "Error:" + e.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					resp = "Error:" + e.toString();
				}
				return resp;
			}

			@Override
			protected void onPostExecute(String result) {
				// execution of result of Long time consuming operation
				//fromDb.setText(result);
				Toast.makeText(
						FReportGenerate.this,
						result,
						Toast.LENGTH_LONG).show();
			}

			@Override
			protected void onPreExecute() {
				// Things to be done before execution of long running operation. For
				// example showing ProgessDialog
			}

			@Override
			protected void onProgressUpdate(String... text) {
				// tvAll.setText(text[0]);
				// Things to be done while execution of long running operation is in
				// progress. For example updating ProgessDialog
			}

		}
}
