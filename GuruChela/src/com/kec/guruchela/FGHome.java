package com.kec.guruchela;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragment;



public class FGHome extends SherlockFragment {
	public FGHome() {
		// Empty constructor required for fragment subclasses
	}

	// TODO Auto-generated constructor stub
	public static Fragment newInstance(Context context) {
		FGHome f = new FGHome();
		

		return f;
	}

	String finaluser;
	String classname,selectedsemester,selectedfaculty;
	String faculty;
	Button selector;
	//TextView description;
	Dialog choose;
	ViewGroup root;
	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();

	ListView lv1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			finaluser = bundle.getString("username");

		}
		root = (ViewGroup) inflater.inflate(R.layout.x_f_ghome, null); 
		//shareditem = (TextView) root.findViewById(R.id.tvDislapySharedItem);
		selector = (Button) root.findViewById(R.id.bSelect);
		lv1 = (ListView) root.findViewById(R.id.listView2);
		//description = (TextView) root.findViewById(R.id.tvDisplayDescription);
		selector.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				choose = new Dialog(getActivity());
				choose.setContentView(R.layout.home_share_dialog);
				choose.setTitle("Choose your group!");
				Button faculty = (Button) choose.findViewById(R.id.b_homesharefaculty);
				Button semester = (Button) choose.findViewById(R.id.b_homesharesemester);
				Button ok = (Button) choose.findViewById(R.id.b_homeok);  
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//String desc = description.getText().toString();
							Log.d("faculty", selectedfaculty);
							Log.d("semester", selectedsemester);
							new SelectDB().execute(selectedfaculty,selectedsemester);
							choose.dismiss();
						
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
		lv1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				ItemDetails selectedItem = results.get(position);

				Toast.makeText(getActivity(), selectedItem.getDescription(),
						Toast.LENGTH_LONG).show();

			}
		});
		
		return root;
	}
	// select where bala
			private class SelectDB extends AsyncTask<String, String, String> {

				private String resp = "";

				@Override
				protected String doInBackground(String... params) {
					
					
																	// onProgressUpdate()
					String url = "http://www.toolittletoobig.com/guruchela_php/select_share_inner2.php";

					HttpClient httpclient = new DefaultHttpClient();
					
					try {
						List<NameValuePair> param = new ArrayList<NameValuePair>();
						param.add(new BasicNameValuePair("faculty",selectedfaculty));
						param.add(new BasicNameValuePair("classname",selectedsemester));
						

						String paramString = URLEncodedUtils.format(param, "utf-8");
						// Creating get url
						url += "?" + paramString;

						URI path = new URI(url);
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
					} /*
					 * catch (ParserConfigurationException e) { // TODO Auto-generated
					 * catch block e.printStackTrace(); } catch (SAXException e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); }
					 */catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return resp;

				}

				@Override
				protected void onPostExecute(String result) {
					// execution of result of Long time consuming operation
					// display.setText(result);
					String []hotelList=result.split("!");
					
					  for(String hotel:hotelList)
					  {
						  
						  String row[]=hotel.split(",");
						  ItemDetails item_details = new ItemDetails();
						  item_details.setUname(row[0]);
						  item_details.setDescription(row[1]);
						  item_details.setShareditem(row[2]);
						  //item_details.setImageNumber(1);
						  results.add(item_details);
						 
					  }
					  lv1.setAdapter(new ItemListBaseAdapter(getActivity(), results));
					  
					 
					  
				}

				@Override
				protected void onPreExecute() {
					// Things to be done before execution of long running operation. For
					// example showing ProgessDialog
				}

				@Override
				protected void onProgressUpdate(String... text) {
					
					// Things to be done while execution of long running operation is in
					// progress. For example updating ProgessDialog
					Toast.makeText(getActivity(), text[0],
							Toast.LENGTH_LONG).show();
				}

			}}