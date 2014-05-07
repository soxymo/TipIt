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

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class TipItMainActivity extends ActionBarActivity {
	private ArrayList<Entry> entries;
	private Context context;
	private EntryListAdapter adapt;
	private TextView totalView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_it_main);
		context=this;
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
		
		
		totalView = (TextView) findViewById(R.id.totalView);
		
		Entry firstOne= new Entry("Base Modifier", 15, true, 1);
		entries.add(firstOne);
		for(int i=0; i<entryText.length; i++){
			Entry temp= new Entry(entryText[i], entryWeight[i], false, -1);
			entries.add(temp);
		}
		
		
		adapt = new EntryListAdapter(this, entries, totalView);
		ListView mainList= (ListView) findViewById(R.id.mainList);
		View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.add_layout, null, false);
		mainList.addFooterView(footerView);
		footerView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				makeNewDialog();
				addUpTotal();
			}

		});

		mainList.setAdapter(adapt);
		
		//norifyDataSetChange
		
		//adapt.updateList(entries);
		
		Button checkButton=(Button)findViewById(R.id.checkButton);
		addUpTotal();
		
	}
	
	public void makeNewDialog(){
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_box);
	
		final EditText textField = (EditText) dialog.findViewById(R.id.edit_text);
		final EditText weightField=(EditText) dialog.findViewById(R.id.edit_weight);
		Button saveButton = (Button) dialog.findViewById(R.id.save_button);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		dialog.setTitle("Make Your Own Entry");

		// if button is clicked, close the custom dialog
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Entry customEntry=new Entry("Hello?", 3, "af", 0);
				Entry customEntry= new Entry(textField.getText().toString(), Integer.parseInt(weightField.getText().toString()),false, 0);
				addToList(customEntry);
				dialog.dismiss();
			}
		});
		dialog.show();
		//return editEntry;
	}
	
	public void addToList(Entry newEntry){
		entries.add(newEntry);
		adapt.notifyDataSetChanged();
		
	}
	
	public void addUpTotal(){
		int total=0;
		for(int i=0; i<entries.size(); i++){
			total+=entries.get(i).getWeight()*entries.get(i).getCurrentValue();
		}
		if(total<0)
			total=0;
		totalView.setText(String.valueOf(total)+"%");
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
