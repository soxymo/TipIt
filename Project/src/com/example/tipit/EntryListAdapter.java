package com.example.tipit;



import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EntryListAdapter extends ArrayAdapter<Entry> {
	ArrayList<Entry> entryArray;
	Context context;
	Drawable thumbsUp;
	Drawable thumbsDown;
	TextView totalViewHandle;
	
	public EntryListAdapter(Context context1, ArrayList<Entry> entryArray, TextView totalView){
		super(context1, R.layout.entry_layout, entryArray);
		this.context=context1;
		this.entryArray=entryArray;
		totalViewHandle=totalView;
		thumbsUp=(Drawable)context1.getResources().getDrawable( R.drawable.thumbs_up_icon );
		thumbsDown=(Drawable)context1.getResources().getDrawable( R.drawable.thumbs_down_icon );
	}
	
	public View getView(final int position, View convertView, ViewGroup parent){
		
		ViewHolder hold;
		View rowView=convertView;
		
		if(rowView==null){
			hold=new ViewHolder();
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView=inflater.inflate(R.layout.entry_layout, parent, false);
			hold.textSlot=(TextView) rowView.findViewById(R.id.content);
			hold.percentSlot=(TextView) rowView.findViewById(R.id.percent);
			hold.thumbSlot=(ImageView) rowView.findViewById(R.id.thumbPic);
			rowView.setTag(hold);
		}
		else{
			hold=(ViewHolder)rowView.getTag();
		}
		hold.textSlot.setText(entryArray.get(position).text);
		if(entryArray.get(position).getCurrentValue()==0){		//default value
			hold.thumbSlot.setImageDrawable(null);
			hold.percentSlot.setText("0%");
			((TextView)hold.percentSlot).setTextColor(0xFF000000);

		}else if(entryArray.get(position).getCurrentValue()==1){		//positive
			hold.percentSlot.setText("+"+String.valueOf(entryArray.get(position).getWeight())+"%");
			((TextView)hold.percentSlot).setTextColor(0xFF20D43B);
			hold.thumbSlot.setImageDrawable(thumbsUp);

		}else if(entryArray.get(position).getCurrentValue()==-1){		//negative
			hold.percentSlot.setText("-"+String.valueOf(entryArray.get(position).getWeight())+"%");
			((TextView)hold.percentSlot).setTextColor(0xFFFF0000);
			hold.thumbSlot.setImageDrawable(thumbsDown);
		}

		rowView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(position!=0 ){
					entryArray.get(position).toggleCurrentValue();
					notifyDataSetChanged();
					updateTotal();
				}
			}

		});
		
		rowView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				editDialog(entryArray.get(position), position);
				
				return true;
			}


		});

		return rowView;
	}
	
	public void updateTotal(){
		int total=0;
		for(int i=0; i<entryArray.size(); i++){
			total+=entryArray.get(i).getWeight()*entryArray.get(i).getCurrentValue();
		}
		if(total<0)
			total=0;
		
		totalViewHandle.setText(String.valueOf(total)+"%");
		
		
	}
	
	
	public void editDialog(final Entry editEntry, final int position){
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.edit_dialog_box);
	
		final EditText textField = (EditText) dialog.findViewById(R.id.edit_text);
		final EditText weightField=(EditText) dialog.findViewById(R.id.edit_weight);
		Button saveButton = (Button) dialog.findViewById(R.id.save_button);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		Button deleteButton = (Button) dialog.findViewById(R.id.delete_button);
		
		dialog.setTitle("Edit Entry");
		textField.setText(editEntry.text);
		weightField.setText(String.valueOf(editEntry.weight));
		if(editEntry.isFirst){
			textField.setTextColor(0xFFFF0000);
			textField.setText("Cannot change Modifier Name");
			deleteButton.setVisibility(View.GONE);
			textField.setKeyListener(null);
		}

		// if button is clicked, close the custom dialog
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(!editEntry.isFirst){
					entryArray.remove(position);
					notifyDataSetChanged();
					updateTotal();
				}
				dialog.dismiss();
			}
			
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Entry customEntry=new Entry("Hello?", 3, "af", 0);
				//Entry customEntry= new Entry(textField.getText().toString(), Integer.parseInt(weightField.getText().toString()),false, 0);
				//addToList(customEntry);
				if(!editEntry.isFirst)
					editEntry.text=textField.getText().toString();
				editEntry.weight=Integer.parseInt(weightField.getText().toString());
				updateTotal();
				dialog.dismiss();
			}
		});
		dialog.show();
		//return editEntry;
	}
	
	
	public class ViewHolder
	{
	    TextView textSlot;
	    TextView percentSlot;
	    ImageView thumbSlot;
	    
	    
	}

}
