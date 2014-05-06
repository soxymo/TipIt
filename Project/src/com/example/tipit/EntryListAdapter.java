package com.example.tipit;



import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryListAdapter extends ArrayAdapter<Entry> {
	ArrayList<Entry> entryArray;
	Context context;
	
	public EntryListAdapter(Context context1, ArrayList<Entry> entryArray){
		super(context1, R.layout.entry_layout, entryArray);
		this.context=context1;
		this.entryArray=entryArray;
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
			rowView.setTag(hold);
		}
		else{
			hold=(ViewHolder)rowView.getTag();
		}
		
		
			hold.textSlot.setText(entryArray.get(position).text);
			if(entryArray.get(position).getCurrentValue()==0){
				//remove thumb drawable
				hold.percentSlot.setText("0%");
			}else if(entryArray.get(position).getCurrentValue()==1){
				hold.percentSlot.setText(String.valueOf(entryArray.get(position).getWeight())+"%");
				((TextView)hold.percentSlot).setTextColor(0xFF20D43B);
				
			}else if(entryArray.get(position).getCurrentValue()==-1){
				hold.percentSlot.setText("-"+String.valueOf(entryArray.get(position).getWeight())+"%");
				((TextView)hold.percentSlot).setTextColor(0xFFFF0000);
			}
		
		return rowView;
	}
	
	public class ViewHolder
	{
	    TextView textSlot;
	    TextView percentSlot;
	    
	    
	}

}
