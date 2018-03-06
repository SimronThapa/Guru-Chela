package com.kec.guruchela;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class MainReport extends SherlockActivity {

	private StudentReportDb dbHelper;
	private SimpleCursorAdapter dataAdapter;
	ActionMode mMode;
	long Lid;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main_report);

		dbHelper = new StudentReportDb(this);
		dbHelper.open();
		displayListView();
	}

	private void displayListView() {

		Cursor cursor = dbHelper.fetchAll();

		// The desired columns to be bound
		String[] columns = new String[] { StudentReportDb.KEY_STUDNAME , StudentReportDb.KEY_ROLLNO, StudentReportDb.KEY_SUBNAME, StudentReportDb.KEY_STOTAL  };

		// the XML defined views which the data will be bound to
		int[] to = new int[] {  R.id.NameName  , R.id.RollRoll  , R.id.SubSub , R.id.IM };

		// create the adapter using the cursor pointing to the desired data
		// as well as the layout information
		dataAdapter = new SimpleCursorAdapter(this, R.layout.list_report,
				cursor, columns, to, 0);

		listView = (ListView) findViewById(R.id.listView3);
		// Assign adapter to ListView
		listView.setAdapter(dataAdapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				mMode = startActionMode(new AnActionModeOfEpicProportions());

				Lid = arg3;
				return true;
				
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> listView, View view,
					int position, long id) {

				
				
				//
				StudentReportDb db = new StudentReportDb(MainReport.this);
				db.open();
				String content = db.getContent(id);
				db.close();
				//Dialog d = new Dialog(MainDiary.this);
				//d.setTitle(" My Diary!");
				//TextView tv = new TextView(MainDiary.this);
				//tv.setText(content);
				//d.setContentView(tv);
				//d.show();
			

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
			StudentReportDb db = new StudentReportDb(
					MainReport.this);
			db.open();
			db.deleteEntry(Lid);
			db.close();
			dataAdapter.notifyDataSetChanged();
			SimpleCursorAdapter dataAdapter1;
			Cursor cursor = dbHelper.fetchAll(); 

			// The desired columns to be bound
			String[] columns = new String[] { StudentReportDb.KEY_STUDNAME , StudentReportDb.KEY_ROLLNO, StudentReportDb.KEY_SUBNAME, StudentReportDb.KEY_STOTAL  
					
					};

			// the XML defined views which the data will be bound to
			int[] to = new int[] {  R.id.NameName  , R.id.RollRoll  , R.id.SubSub , R.id.IM }; 

			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			dataAdapter1 = new SimpleCursorAdapter(MainReport.this, R.layout.list_report,
					cursor, columns, to, 0);

			 listView = (ListView) findViewById(R.id.listView3);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter1);

			//listView.setAdapter(dataAdapter);
			mode.finish();
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub

		}
	}
}

