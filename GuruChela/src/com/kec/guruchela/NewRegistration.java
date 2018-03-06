package com.kec.guruchela;

import java.io.BufferedReader;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class NewRegistration extends SherlockActivity {
	EditText firstn, lastn, email, roll, usern, passw, cpassw;
	Button faculty, semester, cont;
	TextView AuthenticationCode;
	String FN, LN, UN, PW, CPW, EMAIL, ROLL;
	String getCode;
	String selectedfaculty;
	String selectedsemester;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chela_registration);
		firstn = (EditText) findViewById(R.id.et_reg_fn);
		lastn = (EditText) findViewById(R.id.et_reg_ln);
		email = (EditText) findViewById(R.id.et_reg_email);
		roll = (EditText) findViewById(R.id.et_reg_rollno);
		usern = (EditText) findViewById(R.id.et_reg_un);
		passw = (EditText) findViewById(R.id.et_reg_pw);
		cpassw = (EditText) findViewById(R.id.et_reg_cpw);
		faculty = (Button) findViewById(R.id.bFaculty);
		semester = (Button) findViewById(R.id.bSemester);
		cont = (Button) findViewById(R.id.b_reg_continue);
		
		AuthenticationCode = (TextView) findViewById(R.id.tvAuthenticationCode);
		faculty.setOnClickListener(new OnClickListener() {

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
						switch (((RadioGroup) ACode.findViewById(R.id.rg_faculty))
								.getCheckedRadioButtonId()) {
						case R.id.rbComputer:
							selectedfaculty = "Computer";
							break;
						case R.id.rbElectronics:
							selectedfaculty = "Electronics";
							break;
						case R.id.rbElectrical:
							selectedfaculty = "Electrical";
							break;
						case R.id.rbMechanical:
							selectedfaculty = "Mechanical";
							break;
						case R.id.rbCivil:
							selectedfaculty = "Civil";
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
		semester.setOnClickListener(new OnClickListener() {

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
						switch (((RadioGroup) ACode.findViewById(R.id.rg_semester))
								.getCheckedRadioButtonId()) {
						case R.id.rb1sem:
							selectedsemester = "1";
							break;
						case R.id.rb2sem:
							selectedsemester = "2";
							break;
						case R.id.rb3sem:
							selectedsemester = "3";
							break;
						case R.id.rb4sem:
							selectedsemester = "4";
							break;
						case R.id.rb5sem:
							selectedsemester = "5";
							break;
						case R.id.rb6sem:
							selectedsemester = "6";
							break;
						case R.id.rb7sem:
							selectedsemester = "7";
							break;
						case R.id.rb8sem:
							selectedsemester = "8";
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

		cont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {

					getCode = "222";
					FN = firstn.getText().toString();
					LN = lastn.getText().toString();
					EMAIL = email.getText().toString();
					ROLL = roll.getText().toString();
					UN = usern.getText().toString();
					PW = passw.getText().toString();
					CPW = cpassw.getText().toString();
					if (PW.equals(CPW)) {
						if (FN.length() < 1 || LN.length() < 1
								|| EMAIL.length() < 1 || ROLL.length() < 1
								|| UN.length() < 1 || PW.length() < 1
								|| CPW.length() < 1
								|| selectedsemester.length() < 1
								|| selectedfaculty.length() < 1) {
							Toast.makeText(NewRegistration.this,
									"please enter values to all the fields",
									Toast.LENGTH_LONG).show();
						} else {
							new InsertDB().execute(UN, PW, FN, LN, getCode,
									EMAIL, selectedsemester, "KEC",
									selectedfaculty, ROLL, "1");
						}
					} else {
						Toast.makeText(NewRegistration.this,
								"Password missmatch!", Toast.LENGTH_LONG)
								.show();
					}

				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	// Creating AsynTask for Insert into Database
	private class InsertDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			publishProgress("Reading Data..."); // Calls onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/insertUser_gc.php";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			try {
				// Add your data

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", params[0]));
				nameValuePairs
						.add(new BasicNameValuePair("password", params[1]));
				nameValuePairs.add(new BasicNameValuePair("firstname",
						params[2]));
				nameValuePairs
						.add(new BasicNameValuePair("lastname", params[3]));
				nameValuePairs.add(new BasicNameValuePair("authentication",
						params[4]));
				nameValuePairs
						.add(new BasicNameValuePair("website", params[5]));
				nameValuePairs.add(new BasicNameValuePair("classname",
						params[6]));
				nameValuePairs.add(new BasicNameValuePair("institution",
						params[7]));
				nameValuePairs
						.add(new BasicNameValuePair("faculty", params[8]));
				nameValuePairs.add(new BasicNameValuePair("rollnumber",
						params[9]));
				nameValuePairs.add(new BasicNameValuePair("level", params[10]));

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
			
			Toast.makeText(NewRegistration.this,
					result, Toast.LENGTH_LONG)
					.show();
			AuthenticationCode.setText("");
			final Dialog ACode = new Dialog(context);
			ACode.setContentView(R.layout.x_authentication);
			ACode.setTitle("Hit Authentication Code!");
			final EditText code = (EditText) ACode
					.findViewById(R.id.etAuthenCode);
			ImageButton ok = (ImageButton) ACode
					.findViewById(R.id.ibOKey);
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(code.getText().toString().length()<1){
						Toast.makeText(NewRegistration.this,
								"Please give your code!", Toast.LENGTH_LONG)
								.show();
					}else{
					int check = Integer.parseInt(code.getText().toString());
					 if(check==222){
					Intent i = new Intent(
							"com.kec.guruchela.NAVIGATION_CHELA");
					i.putExtra("username",UN);
					startActivity(i);}
					else{
						Toast.makeText(NewRegistration.this,
								"Authentication code missmatch", Toast.LENGTH_LONG)
								.show();
					}
					}

				}
			});
			ACode.show();
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			// tvAll.setText(text[0]);
			AuthenticationCode.setText(text[0]);
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}
}