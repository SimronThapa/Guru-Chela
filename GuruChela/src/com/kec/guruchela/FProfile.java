package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;






public class FProfile extends SherlockFragment {
	 public FProfile() {
         // Empty constructor required for fragment subclasses
     }
	// TODO Auto-generated constructor stub
	public static Fragment newInstance(Context context) {
		FProfile f = new FProfile();

		return f;
	}
	EditText username,institution,email,faculty,semester,description;
	ImageButton share;
	String selectedfaculty,selectedsemester;
	Dialog choose;
	String finaluser;
	TextView  display;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {    
		Bundle bundle = this.getArguments();
		if(bundle!=null){
			finaluser = bundle.getString("username");
			Toast.makeText(getActivity(),
					finaluser,
					Toast.LENGTH_LONG).show();
		}
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.x_f_profile,
				null);
		username = (EditText) root.findViewById(R.id.et_Cprofile_username);
		institution = (EditText) root.findViewById(R.id.et_Cprofile_institution);
		email = (EditText) root.findViewById(R.id.et_Cprofile_email);
		faculty = (EditText) root.findViewById(R.id.et_Cprofile_faculty);
		semester = (EditText) root.findViewById(R.id.et_Cprofile_semester);
		description = (EditText) root.findViewById(R.id.et_Cprofile_description);
		share = (ImageButton) root.findViewById(R.id.ib_Cprofile_share);
		display = (TextView) root.findViewById(R.id.tvTest);
		new SelectDB().execute(finaluser);
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				choose = new Dialog(getActivity());
				choose.setContentView(R.layout.profile_share_dialog);
				choose.setTitle("Choose your receiver!");
				Button faculty = (Button) choose.findViewById(R.id.b_sharefaculty);
				Button semester = (Button) choose.findViewById(R.id.b_sharesemester);
				Button ok = (Button) choose.findViewById(R.id.b_share_OK);
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String desc = description.getText().toString();
						if(desc.length()<1){
							Toast.makeText(getActivity(),
									"Please write the thing what you want to share!",
									Toast.LENGTH_LONG).show();
						}else{
							new InsertDB().execute(" ", desc, finaluser, selectedsemester,
									selectedfaculty);
						}
					}
				});
				faculty.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Dialog fac = new Dialog(getActivity());
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
						final Dialog sem = new Dialog(getActivity());
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
		
		return root;
	}
	// Creating AsynTask for Insert into Database
		private class InsertDB extends AsyncTask<String, String, String> {

			private String resp = "";

			@Override
			protected String doInBackground(String... params) {
				publishProgress("Sharing Data..."); // Calls onProgressUpdate()
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
				
				Toast.makeText(getActivity(),
						result, Toast.LENGTH_LONG)
						.show();
				choose.dismiss();
				description.setText("");
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
				Toast.makeText(getActivity(),
						text[0], Toast.LENGTH_LONG)
						.show();
				//AuthenticationCode.setText(text[0]);
				// Things to be done while execution of long running operation is in
				// progress. For example updating ProgessDialog
			}

		}
		//select where bala
		private class SelectDB extends AsyncTask<String, String, String> {

			  private String resp="";

			  @Override
			  protected String doInBackground(String... params) {
			   publishProgress("Reading Data..."+params[0]); // Calls onProgressUpdate()
			   String url = "http://www.toolittletoobig.com/guruchela_php/select_CProfile_gc.php";
			   
			   HttpClient httpclient = new DefaultHttpClient();
	           
			   try
				{
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("username", params[0]));
				
					String paramString = URLEncodedUtils.format(param, "utf-8");
					//Creating get url
					url+="?"+paramString;
				   
					URI path=new URI(url);
				    HttpGet httpget = new HttpGet(path);
						
		   			 HttpResponse response = httpclient.execute(httpget);
		   			 
		   			HttpEntity entity = response.getEntity();
		   			resp = EntityUtils.toString(entity, "UTF-8");

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					resp = "Error:" + e.toString();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					resp = "Error:" + e.toString();
				} /*catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/ catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return resp;
		   			 
			  
			  }

			  @Override
			  protected void onPostExecute(String result) {
			   // execution of result of Long time consuming operation
			  //display.setText(result);
			  String []userList=result.split(",");
			  //userList[4]=userList[4].substring(0, userList[4].indexOf(";"));
			  
				  username.setText("Username:"+userList[0]);
				  institution.setText("Institution:"+userList[1]);
				  email.setText("Email:"+userList[2]);
				  faculty.setText("Faculty:"+userList[3]);
				 // semester.setText("Semester:"+userList[4]);
				  if(userList[4].equals("1:")){
					  semester.setText("Semester: 1st");
				  }
				  else if(userList[4].equals("2:")){
					  semester.setText("Semester: 2nd");
				  }
				  else if(userList[4].equals("3:")){
					  semester.setText("Semester: 3rd");
				  }
				  else if(userList[4].equals("4:")){
					  semester.setText("Semester: 4th");
				  }
				  else if(userList[4].equals("5:")){
					  semester.setText("Semester: 5th");
				  }
				  else if(userList[4].equals("6:")){
					  semester.setText("Semester: 6th");
				  }
				  else if(userList[4].equals("7:")){
					  semester.setText("Semester: 7th");
				  }
				  else if(userList[4].equals("8:")){
					  semester.setText("Semester: 8th");
				  }
			  
			  
			  }
			  @Override
			  protected void onPreExecute() {
			   // Things to be done before execution of long running operation. For
			   // example showing ProgessDialog
			  }

			  @Override
			  protected void onProgressUpdate(String... text) {
			 // display.setText(text[0]);
			   // Things to be done while execution of long running operation is in
			   // progress. For example updating ProgessDialog
			  }
			 
			}
		

	
}