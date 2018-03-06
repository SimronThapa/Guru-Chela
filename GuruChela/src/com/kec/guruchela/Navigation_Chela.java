package com.kec.guruchela;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.support.v4.view.GravityCompat;

public class Navigation_Chela extends SherlockFragmentActivity {

	// Declare Variable
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	String[] title;
	String[] subtitle;
	int[] icon;
	EditText oldP;
	EditText newP;
	EditText confirmP;
	FragmentTransaction ft;
	private static final String SHARED_FILE_NAME = "shared.png";
	Dialog bm_add_dialog;
	Fragment fragment1 = new FProfile();
	Fragment fragment2 = new FHome();
	String Ssubname;
	String Stotal;
	String Sbunked;
	String Spercent;
	final Context context = this;
	EditText subname;
	EditText total, schedulename;
	EditText bunked;
	EditText percent;
	String selectedsemester;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	String finaluser;
	String sendingfac, sendingroll, sendingsem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		finaluser = extras.getString("username");
		setContentView(R.layout.xnavigation_drawer);

		mTitle = mDrawerTitle = getTitle();
		// Generate title
		title = new String[] { "Profile", "Home", "Schedule", "Bunk Manager",
				"Share materials", "Memo and Reminders", "General Formula",
				"Instant Mail", "Click and share", "Location Tracker", "Logout" };

		// Generate subtitle
		subtitle = new String[] { "Your info...", "Whats new!",
				"Instant scheduling!", "Bunk but dont get caught!",
				"Download and read!", "Stay updated",
				"Engineering formulae? here you go!", "Mailing made easy!",
				"Sharing made easy!", "Tap it to know your place!", "Bye-Bye!!" };

		// Generate icon
		icon = new int[] { R.drawable.social_person, R.drawable.social_cc_bcc,
				R.drawable.collections_go_to_today,
				R.drawable.device_access_time, R.drawable.content_copy,
				R.drawable.collections_labels, R.drawable.content_paste,
				R.drawable.content_email, R.drawable.device_access_camera,
				R.drawable.location_map, R.drawable.alerts_and_states_warning };

		// Locate DrawerLayout in drawer_main.xml
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// Locate ListView in drawer_main.xml
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// Pass results to MenuListAdapter Class
		mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);

		// Set the MenuListAdapter to the ListView
		mDrawerList.setAdapter(mMenuAdapter);

		// Capture button clicks on side menu
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// Enable ActionBar app icon to behave as action to toggle nav drawer
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
				// super.onDrawerClosed(view);
			}

			public void onDrawerOpened(View drawerView) {
				// TODO Auto-generated method stub
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
				// super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_ai_chat1).setVisible(!drawerOpen);

		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.cool_menu1, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case android.R.id.home:
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
			return true;
		case R.id.menu_help1:
			final Dialog help = new Dialog(context);
			help.setContentView(R.layout.help);
			help.setTitle("HELP!");
			Button btn = (Button) help.findViewById(R.id.bok);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					help.dismiss();
				}
			});
			help.show();
			return true;
		case R.id.menu_search1:
			try {
				new SelectDB().execute(finaluser);
			} catch (Exception e) {
				String error = e.toString();
				Toast.makeText(Navigation_Chela.this, error, Toast.LENGTH_LONG)
						.show();
			}
			return true;

		case R.id.menu_speak1:
			// kunai intent dine
			Intent l = new Intent("com.kec.guruchela.VOICE");
			startActivity(l);
			return true;
		case R.id.menu_ai_chat1:
			// kunai intent dine
			Intent ai = new Intent(Navigation_Chela.this,
					HelloBubblesActivity.class);
			startActivity(ai);
			return true;

		case R.id.menu_settings1:

			final Dialog settings = new Dialog(context);
			settings.setContentView(R.layout.changing_settings_main);
			settings.setTitle("Settings!");
			Button cPassword = (Button) settings
					.findViewById(R.id.b_changePassword);
			Button cSem = (Button) settings.findViewById(R.id.b_changeSemester);
			cPassword.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog changeP = new Dialog(context);
					changeP.setContentView(R.layout.changing_password);
					changeP.setTitle("Change your password!");
					oldP = (EditText) changeP.findViewById(R.id.et_oldpassword);
					newP = (EditText) changeP.findViewById(R.id.et_newpassword);
					confirmP = (EditText) changeP
							.findViewById(R.id.et_confirmpassword);
					Button ok = (Button) changeP
							.findViewById(R.id.b_changepassword_OK);
					ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if ((newP.getText().toString()).equals(confirmP
									.getText().toString())) {
								new UpdateDB().execute(finaluser, newP
										.getText().toString());
							} else {
								Toast.makeText(
										Navigation_Chela.this,
										"New password doesnt match with Confirm password!",
										Toast.LENGTH_LONG).show();
							}
						}
					});
					changeP.show();
				}
			});
			cSem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Dialog ACode = new Dialog(context);
					ACode.setContentView(R.layout.registration_semester);
					ACode.setTitle("Select your semester!");

					Button semOK = (Button) ACode
							.findViewById(R.id.b_semesterOK);
					semOK.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							switch (((RadioGroup) ACode
									.findViewById(R.id.rg_semester))
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
								throw new RuntimeException(
										"Unexpected selection!!");
							}
							ACode.dismiss();
							new UpdateDB1()
									.execute(finaluser, selectedsemester);
						}
					});
					ACode.show();
				}
			});
			settings.show();
			return true;
		case R.id.menu_bug1:
			Intent j = new Intent(Navigation_Chela.this, ReportBug.class);
			startActivity(j);
			return true;
		case R.id.menu_about_us1:

			final Dialog AU = new Dialog(context);
			AU.setContentView(R.layout.x_about_us);
			ImageButton rate = (ImageButton) AU.findViewById(R.id.bRate);
			AU.setTitle("About Us!");
			AU.show();
			return true;
		case R.id.menu_logout1:
			finish();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Bundle bundle = new Bundle();
		bundle.putString("username", finaluser);
		ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		switch (position) {
		case 0:
			fragment1 = new FProfile();
			fragment1.setArguments(bundle);
			ft.replace(R.id.content_frame, fragment1);

			break;
		case 1:
			fragment2 = new FHome();
			fragment2.setArguments(bundle);
			ft.replace(R.id.content_frame, fragment2);
			break;
		case 6:
			// ft.(R.id.content_frame, fragment3);
			Intent i = new Intent(Navigation_Chela.this,
					FragmentPagerSupport.class);
			startActivity(i);
			break;
		case 2:
			// ft.replace(R.id.content_frame, fragment3);
			final Dialog ask = new Dialog(context);
			ask.setContentView(R.layout.x_f_schedule);
			ask.setTitle("Give youe schedule a title!");
			Button btn = (Button) ask.findViewById(R.id.bSch);
			schedulename = (EditText) ask.findViewById(R.id.et_schedulename);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String str = schedulename.getText().toString();
					if (str.length() > 0) {
						Intent i = new Intent(Navigation_Chela.this,
								DynamicTable.class);
						i.putExtra("schedule", str);
						startActivity(i);
						ask.dismiss();
					} else {
						Toast.makeText(Navigation_Chela.this,
								"Give a name to your Schedule!",
								Toast.LENGTH_LONG).show();
					}
				}
			});
			ask.show();
			break;

		case 3:

			final Dialog bm_main = new Dialog(context);
			bm_main.setContentView(R.layout.bm_main);
			bm_main.setTitle("Choose an activity!");
			Button add_sub = (Button) bm_main.findViewById(R.id.baddsub);
			Button overview = (Button) bm_main.findViewById(R.id.boverview);
			ImageView expressions = (ImageView) bm_main
					.findViewById(R.id.ivexp);
			overview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Navigation_Chela.this, BM_main.class);
					startActivity(i);
					bm_main.dismiss();
				}
			});

			add_sub.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					bm_add_dialog = new Dialog(context);
					bm_add_dialog.setContentView(R.layout.bm_add);
					bm_add_dialog.setTitle("New bunking Subject!");
					subname = (EditText) bm_add_dialog
							.findViewById(R.id.et_subname_);
					total = (EditText) bm_add_dialog
							.findViewById(R.id.et_total);
					bunked = (EditText) bm_add_dialog
							.findViewById(R.id.et_bunked);
					percent = (EditText) bm_add_dialog
							.findViewById(R.id.et_percent);

					ImageButton save = (ImageButton) bm_add_dialog
							.findViewById(R.id.ivsave_bunk_info);
					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Ssubname = subname.getText().toString();
							Stotal = total.getText().toString();
							Sbunked = bunked.getText().toString();
							Spercent = percent.getText().toString();
							Intent i = new Intent(Navigation_Chela.this,
									BM_overview.class);
							i.putExtra("sub", Ssubname);
							i.putExtra("totalL", Stotal);
							i.putExtra("bunkedL", Sbunked);
							i.putExtra("percent", Spercent);
							startActivity(i);
							bm_add_dialog.dismiss();

						}
					});
					bm_add_dialog.show();
				}
			});

			bm_main.show();
			break;
		case 4:
			Intent dropbox = new Intent(Navigation_Chela.this,
					DropboxChooser.class);
			dropbox.putExtra("username", finaluser);
			startActivity(dropbox);
			break;

		case 5:
			// ft.replace(R.id.content_frame, fragment3);
			final Dialog md = new Dialog(context);
			md.setContentView(R.layout.x_memo_diary);
			md.setTitle("Choose an activity!");
			Button bdiary = (Button) md.findViewById(R.id.bdiary);
			Button bevents = (Button) md.findViewById(R.id.bevents);
			bdiary.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog ask11 = new Dialog(context);
					ask11.setContentView(R.layout.x_memo_dialog);
					ask11.setTitle("Choose an Activity!");
					Button btn11 = (Button) ask11
							.findViewById(R.id.b_readdiary);
					Button btn12 = (Button) ask11.findViewById(R.id.b_newdiary);
					btn12.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(Navigation_Chela.this,
									FNewDiary.class);
							startActivity(j);
							ask11.dismiss();
						}
					});
					btn11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(Navigation_Chela.this,
									MainDiary.class);
							startActivity(j);
						}
					});
					ask11.show();
					md.dismiss();
				}
			});
			bevents.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final Dialog ask11 = new Dialog(context);
					ask11.setContentView(R.layout.x_events_dialog);
					ask11.setTitle("Choose an Activity!");
					Button btn11 = (Button) ask11
							.findViewById(R.id.b_viewevents);
					Button btn12 = (Button) ask11
							.findViewById(R.id.b_newevents);
					btn12.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(Navigation_Chela.this,
									FNewEvent.class);
							startActivity(j);
							ask11.dismiss();
						}
					});
					btn11.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Intent j = new Intent(Navigation_Chela.this,
									AndroidListViewCursorAdaptorActivity.class);
							startActivity(j);
						}
					});
					ask11.show();
					md.dismiss();
				}
			});
			md.show();

			break;
		case 7:
			Intent j = new Intent(Navigation_Chela.this, Email.class);
			startActivity(j);
			break;
		case 8:
			// ft.replace(R.id.content_frame, fragment3);
			Intent o = new Intent(Navigation_Chela.this, FCamera.class);
			startActivity(o);
			break;

		case 10:
			final Dialog lo = new Dialog(context);
			lo.setContentView(R.layout.x_logout);
			lo.setTitle("Logging out!");
			Button nop = (Button) lo.findViewById(R.id.bnop);
			Button sure = (Button) lo.findViewById(R.id.bsure);
			nop.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					lo.dismiss();
				}
			});
			sure.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			lo.show();
			break;
		case 9:
			Intent intent = new Intent(
					android.content.Intent.ACTION_VIEW,
					Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
			intent.setClassName("com.google.android.apps.maps",
					"com.google.android.maps.MapsActivity");
			startActivity(intent);
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	// Creating AsynTask for Updating the Database
	private class UpdateDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			// publishProgress("Reading Data..."); // Calls onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/updateUserPassword_gc.php";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			try {
				// Add your data

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", params[0]));
				nameValuePairs
						.add(new BasicNameValuePair("password", params[1]));

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
			// tvDisplay.setText(result);
			Toast.makeText(Navigation_Chela.this,
					"Password is updated succesfully!", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			// tvAll.setText(text[0]);
			Toast.makeText(Navigation_Chela.this,
					"Password update in progress!", Toast.LENGTH_LONG).show();
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}

	// Creating AsynTask for Updating the Database
	private class UpdateDB1 extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			// publishProgress("Reading Data..."); // Calls onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/updateUserSemester_gc.php";
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			try {
				// Add your data

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", params[0]));
				nameValuePairs.add(new BasicNameValuePair("classname",
						params[1]));

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
			// tvDisplay.setText(result);
			Toast.makeText(Navigation_Chela.this,
					"Semester is updated succesfully!", Toast.LENGTH_LONG)
					.show();
		}

		@Override
		protected void onPreExecute() {
			// Things to be done before execution of long running operation. For
			// example showing ProgessDialog
		}

		@Override
		protected void onProgressUpdate(String... text) {
			// tvAll.setText(text[0]);
			Toast.makeText(Navigation_Chela.this,
					"Semester update in progress!", Toast.LENGTH_LONG).show();
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}

	}

	// select where bala
	private class SelectDB extends AsyncTask<String, String, String> {

		private String resp = "";

		@Override
		protected String doInBackground(String... params) {
			publishProgress("Reading Data..." + params[0]); // Calls
															// onProgressUpdate()
			String url = "http://www.toolittletoobig.com/guruchela_php/select_classname_faculty_gc.php";

			HttpClient httpclient = new DefaultHttpClient();

			try {
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("username", params[0]));

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
			String[] userList = result.split(",");
			// userList[4]=userList[4].substring(0, userList[4].indexOf(";"));

			sendingroll = (userList[0]);
			sendingfac = (userList[1]);
			sendingsem = (userList[2]);
			Log.d("Rollnumber", sendingroll);
			Log.d("Faculty", sendingfac);
			Log.d("Semester", sendingsem);
			Intent reportcard = new Intent(Navigation_Chela.this,
					ReportCard.class);
			reportcard.putExtra("rollnumber", sendingroll);
			reportcard.putExtra("faculty", sendingfac);
			reportcard.putExtra("semester", sendingsem);
			startActivity(reportcard);

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		new AlertDialog.Builder(this)
				.setIcon(R.drawable.alerts_and_states_warning)
				.setTitle("closing Activity")
				.setMessage("Are You Sure You want To exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).setNegativeButton("No", null).show();
	}

}
