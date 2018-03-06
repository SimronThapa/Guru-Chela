package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import com.dropbox.chooser.android.DbxChooser;


public class DropboxChooser extends SherlockActivity { 
	static final String APP_KEY = "oeqa7rnf5nc567v"/*
													 * This is for you to fill
													 * in!
													 */;
	static final int DBX_CHOOSER_REQUEST = 0; // You can change this if needed

	private ImageButton mChooserButton;
	ImageButton share;
	private DbxChooser mChooser;
	EditText description1;
	String finaluser,selectedsemester,selectedfaculty;
	Dialog choose;
	TextView uri,filename,size;
	String shareditem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		finaluser = extras.getString("username");
		setContentView(R.layout.dropbox_chooser); 

		mChooser = new DbxChooser(APP_KEY);

		mChooserButton = (ImageButton) findViewById(R.id.ibDropboxIcon);
		share = (ImageButton) findViewById(R.id.ibDropboxShare);
		description1 = (EditText) findViewById(R.id.etDropbox);
		uri = (TextView) findViewById(R.id.uri);
		filename = (TextView) findViewById(R.id.filename);
		size = (TextView) findViewById(R.id.size);
		

		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				choose = new Dialog(DropboxChooser.this);
				choose.setContentView(R.layout.profile_share_dialog);
				choose.setTitle("Choose your receiver!");
				Button faculty = (Button) choose.findViewById(R.id.b_sharefaculty);
				Button semester = (Button) choose.findViewById(R.id.b_sharesemester);
				Button ok = (Button) choose.findViewById(R.id.b_share_OK);
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String desc = description1.getText().toString();
						shareditem = "Filename-"+ filename.getText().toString() +"\n"+"Size-" + size.getText().toString()+"\n" + "Link-" + uri.getText().toString();
						if(desc.length()<1){
							Toast.makeText(DropboxChooser.this,
									"Please write the thing what you want to share!",
									Toast.LENGTH_LONG).show();
						}else{
							new InsertDB().execute(shareditem, desc, finaluser, selectedsemester,
									selectedfaculty);
						}
					}
				});
				faculty.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Dialog fac = new Dialog(DropboxChooser.this);
						fac.setContentView(R.layout.registration_faculty);
						fac.setTitle("Choose faculty!");
						
						Button facultyOK = (Button) fac
								.findViewById(R.id.b_facultyOK);
						facultyOK.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								switch (((RadioGroup) fac.findViewById(R.id.rg_faculty))
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
								fac.dismiss();
							}
						});
						fac.show();
						
					}
				});
				semester.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Dialog sem = new Dialog(DropboxChooser.this);
						sem.setContentView(R.layout.registration_semester);
						sem.setTitle("Choose semester!");
						
						Button semOK = (Button) sem
								.findViewById(R.id.b_semesterOK);
						semOK.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								switch (((RadioGroup) sem.findViewById(R.id.rg_semester))
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
								sem.dismiss();
							}
						});
						sem.show();
					}
				});
				choose.show();
			}
		});
		mChooserButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DbxChooser.ResultType resultType;
				resultType = DbxChooser.ResultType.PREVIEW_LINK; 
				
				mChooser.forResultType(resultType).launch(DropboxChooser.this,
						DBX_CHOOSER_REQUEST);
				
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == DBX_CHOOSER_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				DbxChooser.Result result = new DbxChooser.Result(data);
				Log.d("main", "Link to selected file: " + result.getLink());

				showLink(R.id.uri, result.getLink());
				((TextView) findViewById(R.id.filename)).setText(result
						.getName().toString(), TextView.BufferType.NORMAL);
				((TextView) findViewById(R.id.size)).setText(
						String.valueOf(result.getSize()),
						TextView.BufferType.NORMAL);
				String description = description1.getText().toString();

			} else {
				// Failed or was cancelled by the user.
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void showLink(int id, Uri uri) {
		TextView v = (TextView) findViewById(id);
		if (uri == null) {
			v.setText("", TextView.BufferType.NORMAL);
			return;
		}
		v.setText(uri.toString(), TextView.BufferType.NORMAL);
		v.setMovementMethod(LinkMovementMethod.getInstance());
	}
	// Creating AsynTask for Insert into Database
			private class InsertDB extends AsyncTask<String, String, String> {

				private String resp = "";

				@Override
				protected String doInBackground(String... params) {
					publishProgress("Reading Data..."); // Calls onProgressUpdate()
					String url = "http://www.toolittletoobig.com/guruchela_php/insertShare_gc.php";
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					try {
						// Add your data

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs
								.add(new BasicNameValuePair("shareitem", params[0]));
						nameValuePairs
								.add(new BasicNameValuePair("description", params[1]));
						nameValuePairs.add(new BasicNameValuePair("sender",
								params[2]));
						nameValuePairs
								.add(new BasicNameValuePair("classname", params[3]));
						nameValuePairs.add(new BasicNameValuePair("faculty",
								params[4]));
						
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
					
					Toast.makeText(DropboxChooser.this,
							result, Toast.LENGTH_LONG)
							.show();
					choose.dismiss();
					//description.setText("");
					//AuthenticationCode.setText("");
					
				}

				@Override
				protected void onPreExecute() {
					// Things to be done before execution of long running operation. For
					// example showing ProgessDialog
				}

				@Override
				protected void onProgressUpdate(String... text) {
					// tvAll.setText(text[0]);
					Toast.makeText(DropboxChooser.this,
							text[0], Toast.LENGTH_LONG)
							.show();
					//AuthenticationCode.setText(text[0]);
					// Things to be done while execution of long running operation is in
					// progress. For example updating ProgessDialog
				}

			}
		
		
}
