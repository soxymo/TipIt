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
		if(entryArray.get(position).getCurrentValue()==0){		//default value
			hold.thumbSlot.setImageDrawable(null);
			hold.textSlot.setText(entryArray.get(position).getText());
			hold.percentSlot.setText("0%");
			((TextView)hold.percentSlot).setTextColor(0xFF000000);

		}else if(entryArray.get(position).getCurrentValue()==1){		//positive
			hold.textSlot.setText(entryArray.get(position).getText());
			hold.percentSlot.setText("+"+String.valueOf(entryArray.get(position).getWeight())+"%");
			((TextView)hold.percentSlot).setTextColor(0xFF20D43B);
			hold.thumbSlot.setImageDrawable(thumbsUp);

		}else if(entryArray.get(position).getCurrentValue()==-1){		//negative
			hold.textSlot.setText(entryArray.get(position).getNegText());
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
		
		rowView.setOnLongClickListener(new OnLongClickListener() {	//allow edit text on long click

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
	
		final EditText textField = (EditText) dialog.findViewById(R.id.edit_text);	//get handles on everything
		final EditText weightField=(EditText) dialog.findViewById(R.id.edit_weight);
		final EditText negField=(EditText) dialog.findViewById(R.id.edit_neg);
		Button saveButton = (Button) dialog.findViewById(R.id.save_button);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		Button deleteButton = (Button) dialog.findViewById(R.id.delete_button);
		
		dialog.setTitle("Edit Entry");		//put current values into edit text
		textField.setText(editEntry.text);
		weightField.setText(String.valueOf(editEntry.weight));
		negField.setText(String.valueOf(editEntry.negText));
		if(editEntry.isFirst){					//cannont edit first view
			textField.setTextColor(0xFFFF0000);
			textField.setText("Cannot change Modifier Text");
			negField.setTextColor(0xFFFF0000);
			negField.setText("Can only change Weight");
			deleteButton.setVisibility(View.GONE);
			textField.setKeyListener(null);
		}

		// if button is clicked, close the custom dialog
		cancelButton.setOnClickListener(new OnClickListener() {			//cancel button click listener
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener(){			//delete button click listener

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
		
		saveButton.setOnClickListener(new OnClickListener() {			//Save button click listener
			@Override
			public void onClick(View v) {
				if(!editEntry.isFirst){
					editEntry.text=textField.getText().toString();
					editEntry.negText=negField.getText().toString();
				}
				editEntry.weight=Integer.parseInt(weightField.getText().toString());
				updateTotal();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	
	public class ViewHolder
	{
	    TextView textSlot;
	    TextView percentSlot;
	    ImageView thumbSlot;
	    
	    
	}

}
