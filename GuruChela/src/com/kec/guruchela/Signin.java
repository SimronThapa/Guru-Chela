package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Signin extends SherlockActivity implements OnItemSelectedListener {

	Spinner spin;
	Button signup;
	EditText un;
	EditText pw;
	TextView display;
	String usern;
	String signAs[] = { "Sign in", " Chela", " Guru" };
	final Context context = this;
	int flag;
	EditText code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.xsignin);

		spin = (Spinner) findViewById(R.id.sSignin);
		signup = (Button) findViewById(R.id.bSignUp);
		un = (EditText) findViewById(R.id.etUserName);
		pw = (EditText) findViewById(R.id.etPassword);
		display = (TextView) findViewById(R.id.tvSigninDisplay);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(Signin.this,
				android.R.layout.simple_spinner_item, signAs);
		spin.setAdapter(adapter);
		spin.setOnItemSelectedListener(this);
		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent i = new Intent(Signin.this, NewRegistration.class);
				// startActivity(i);
				final Dialog reg = new Dialog(context);
				reg.setContentView(R.layout.register_as_dialog);
				reg.setTitle("Register as:");
				Button ok = (Button) reg.findViewById(R.id.b_registrationOK);
				ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						switch (((RadioGroup) reg
								.findViewById(R.id.rg_registration))
								.getCheckedRadioButtonId()) {
						case R.id.rbChela:
							Intent i = new Intent(Signin.this, NewRegistration.class);
							startActivity(i);
							break;
						case R.id.rbGuru:
							Intent j = new Intent(Signin.this, NewGRegistration.class);
							startActivity(j);
							break;

						default:
							throw new RuntimeException("Unexpected selection!!");
						}
						reg.dismiss();
					}
				});
				reg.show();
			}
		});
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		int position = spin.getSelectedItemPosition();
		switch (position) {

		case 1:
			// Intent j = new Intent(Signin.this,Navigation_Chela.class);
			// startActivity(j);
			usern = un.getText().toString();
			String passw = pw.getText().toString();
			if (usern.length() < 1 || passw.length() < 1) {
				Toast.makeText(Signin.this, "please enter value to both field",
						Toast.LENGTH_LONG).show();
			} else {

				String level = "1";
				flag = 1;
				new SelectDB().execute(usern, passw, level);

			}

			break;
		case 2:
			usern = un.getText().toString();
			String passw1 = pw.getText().toString();
			if (usern.length() < 1 || passw1.length() < 1) {
				Toast.makeText(Signin.this, "please enter value to both field",
						Toast.LENGTH_LONG).show();
			} else {

				String level = "0";
				flag = 0;
				new SelectDB().execute(usern, passw1, level);

			}

			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		// ACode.dismiss();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	private class SelectDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			publishProgress("Signing in..."); // Calls onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/selectUserSignin_gc.php";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			try {

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", params[0]));
				nameValuePairs
						.add(new BasicNameValuePair("password", params[1]));
				nameValuePairs.add(new BasicNameValuePair("level", params[2]));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

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
			display.setText(result);
			if (flag == 1) {
				Toast.makeText(Signin.this, result, Toast.LENGTH_LONG).show();
				if ((result.toString()).equals("Valid")) {
					final Dialog ACode = new Dialog(context);
					ACode.setContentView(R.layout.x_authentication);
					ACode.setTitle("Hit Authentication Code!");
					final EditText code1 = (EditText) ACode
							.findViewById(R.id.etAuthenCode);
					ImageButton ok = (ImageButton) ACode
							.findViewById(R.id.ibOKey);
					ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (code1.getText().toString().length() < 1) {
								Toast.makeText(Signin.this,
										"Please give your code!",
										Toast.LENGTH_LONG).show();
							} else {
								int check = Integer.parseInt(code1.getText()
										.toString());
								if (check == 222) {
									Intent i = new Intent(
											"com.kec.guruchela.NAVIGATION_CHELA");
									i.putExtra("username", usern);
									startActivity(i);
								} else {
									Toast.makeText(Signin.this,
											"Authentication code missmatch",
											Toast.LENGTH_LONG).show();
								}
							}

						}
					});
					ACode.show();
				} else {
					Toast.makeText(Signin.this, result, Toast.LENGTH_LONG)
							.show();
				}
			} else if (flag == 0) {
				Toast.makeText(Signin.this, result, Toast.LENGTH_LONG).show();
				if ((result.toString()).equals("Valid")) {
					final Dialog ACode = new Dialog(context);
					ACode.setContentView(R.layout.x_authentication);
					ACode.setTitle("Hit Authentication Code!");
					code = (EditText) ACode.findViewById(R.id.etAuthenCode);
					ImageButton ok = (ImageButton) ACode
							.findViewById(R.id.ibOKey);
					ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (code.getText().toString().length() < 1) {
								Toast.makeText(Signin.this,
										"Please give your code!",
										Toast.LENGTH_LONG).show();
							} else {
								int check = Integer.parseInt(code.getText()
										.toString());
								if (check == 111) {
									Intent i = new Intent(
											"com.kec.guruchela.NAVIGATIONDRAWER");
									i.putExtra("username", usern);
									startActivity(i);
								} else {
									Toast.makeText(Signin.this,
											"Authentication code missmatch",
											Toast.LENGTH_LONG).show();
								}
							}
						}
					});
					ACode.show();
				} else {
					Toast.makeText(Signin.this, result, Toast.LENGTH_LONG)
							.show();
				}
			}

		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			display.setText(text[0]);
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}

}
