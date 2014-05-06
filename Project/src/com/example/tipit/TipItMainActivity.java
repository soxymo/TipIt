package com.example.tipit;


import java.util.ArrayList;
import java.util.HashSet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class TipItMainActivity extends ActionBarActivity {
	private ArrayList<Entry> entries;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_it_main);
		String[] entryText=getResources().getStringArray(R.array.entry_text);
		int[] entryWeight=getResources().getIntArray(R.array.entry_weight);
		
		entries=new ArrayList<Entry>();
		Entry firstOne= new Entry("Base Modifier", 15, "first", 1);
		entries.add(firstOne);
		for(int i=0; i<entryText.length; i++){
			Entry temp= new Entry(entryText[i], entryWeight[i], "standard", -1);
			entries.add(temp);
		}
		Entry lastOne = new Entry("Add", 0, "last", 0);
		entries.add(lastOne);
		
		EntryListAdapter adapt = new EntryListAdapter(this, entries);
		ListView mainList= (ListView) findViewById(R.id.mainList);
		mainList.setAdapter(adapt);
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_it_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
