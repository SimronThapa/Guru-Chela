package com.kec.guruchela;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class BM_main extends SherlockActivity {

	private BunkManagerDb dbHelper;
	private SimpleCursorAdapter dataAdapter;
	ActionMode mMode;
	long Lid;
	ListView listView;
	final Context context = this;
	long idd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bm_main_bm);

		dbHelper = new BunkManagerDb(this);
		dbHelper.open();

		// Clean all data
		// dbHelper.deleteAllCountries();
		// Add some data

		// Generate ListView from SQLite Database
		displayListView();

	}

	private void displayListView() {

		Cursor cursor = dbHelper.fetchAll();

		// The desired columns to be bound
		String[] columns = new String[] { BunkManagerDb.KEY_SUBNAME,
				BunkManagerDb.KEY_TLECTURES, BunkManagerDb.KEY_BLECTURES,
				BunkManagerDb.KEY_BEFFECIENCY };

		// the XML defined views which the data will be bound to
		int[] to = new int[] { R.id.subject, R.id.Tlect, R.id.Blect, R.id.Beff, };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(this, R.layout.bm_list_bm,
				cursor, columns, to, 0);

		listView = (ListView) findViewById(R.id.listView4);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long id1) {
				// TODO Auto-generated method stub
				mMode = startActionMode(new AnActionModeOfEpicProportions());

				Lid = id1;
				return false;
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, final long id) {
				
				final Dialog bm_main = new Dialog(context);
				bm_main.setContentView(R.layout.bm_bunk);
				bm_main.setTitle("Bunked again? Hit bunk!");
				Button bunkagain = (Button) bm_main
						.findViewById(R.id.b_hitbunk);
				bunkagain.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							BunkManagerDb db = new BunkManagerDb(BM_main.this);
							db.open();
							String tot = db.getTotal(id);
							String bunk = db.getBunked(id);
							Float tt = Float.parseFloat(tot)+1;
							Float bb = Float.parseFloat(bunk)+1;
							String ttt = (""+tt);
							String bbb = (""+bb);
							db.updateEntry(id, ttt, bbb);
							db.close();
							Toast.makeText(BM_main.this, "Bunk added!",
									Toast.LENGTH_LONG).show();
						} catch (Exception e) {
							String error = e.toString();
							Toast.makeText(BM_main.this, error,
									Toast.LENGTH_LONG).show();

						}
						bm_main.dismiss();
					}
				});
				bm_main.show();

			}
		});

	}

	private final class AnActionModeOfEpicProportions implements
			ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// Used to put dark icons on light action bar

			menu.add("Delete!").setIcon(R.drawable.content_discard)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			// TODO Auto-generated method stub
			BunkManagerDb db = new BunkManagerDb(BM_main.this);
			db.open();
			db.deleteEntry(Lid);
			db.close();
			dataAdapter.notifyDataSetChanged();
			SimpleCursorAdapter dataAdapter1;
			Cursor cursor = dbHelper.fetchAll();

			// The desired columns to be bound
			String[] columns = new String[] { BunkManagerDb.KEY_SUBNAME,
					BunkManagerDb.KEY_TLECTURES, BunkManagerDb.KEY_BLECTURES,
					BunkManagerDb.KEY_BEFFECIENCY };

			// the XML defined views which the data will be bound to
			int[] to = new int[] { R.id.subject, R.id.Tlect, R.id.Blect,
					R.id.Beff, };

			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			dataAdapter1 = new SimpleCursorAdapter(BM_main.this,
					R.layout.bm_list_bm, cursor, columns, to, 0);

			listView = (ListView) findViewById(R.id.listView4);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter1);

			// listView.setAdapter(dataAdapter);
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub

		}

	}
}
