package com.example.tipit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
		
		String filePath=String.valueOf(this.getFilesDir())+"/myfile";
		
		File file=new File(filePath);
		
		if(!file.exists()){
			Log.d("RSS", "Creating new file now");
			String filename = "myfile";
			String string = "Hello world!!!!!!!!!\n";
			String string2 ="Stick it to the Man!";
			FileOutputStream outputStream;
	
			try {
			  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			  outputStream.write(string.getBytes());
			  outputStream.write(string2.getBytes());
			  outputStream.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}
		}
		
		
		if(file.exists())      
			Log.d("RSS", "File Found ");
		else{
			Log.d("RSS", "File doesn't exist");
		}
		
		//Read from File;
		InputStream is;
		StringBuilder sb = new StringBuilder();
		try {
			is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    String line = null;
		    try {
				while ((line = reader.readLine()) != null) {
				  sb.append(line).append("\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		    try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Log.d("RSS", sb.toString());
		
		
		
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
		
		//entries.get(1).setText("HELLO!!");
		//norifyDataSetChange
		
		//adapt.updateList(entries);
		
		Button checkButton=(Button)findViewById(R.id.checkButton);
		
		//checkBut
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
