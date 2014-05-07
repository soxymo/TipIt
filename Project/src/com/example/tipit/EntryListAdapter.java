package com.example.tipit;



import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EntryListAdapter extends ArrayAdapter<Entry> {
	ArrayList<Entry> entryArray;
	Context context;
	Drawable thumbsUp;
	Drawable thumbsDown;
	
	public EntryListAdapter(Context context1, ArrayList<Entry> entryArray){
		super(context1, R.layout.entry_layout, entryArray);
		this.context=context1;
		this.entryArray=entryArray;
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
			if(entryArray.get(position).getCurrentValue()==0){
				hold.thumbSlot.setImageDrawable(null);
				hold.percentSlot.setText("0%");
				((TextView)hold.percentSlot).setTextColor(0xFF000000);
				
			}else if(entryArray.get(position).getCurrentValue()==1){
				hold.percentSlot.setText(String.valueOf(entryArray.get(position).getWeight())+"%");
				((TextView)hold.percentSlot).setTextColor(0xFF20D43B);
				hold.thumbSlot.setImageDrawable(thumbsUp);
				//thumbsUp.setBounds(0, 0, 50, 50);
				//hold.percentSlot.setCompoundDrawables(thumbsUp, null, null, null); //up right down and left puts image in listview
				
				
			}else if(entryArray.get(position).getCurrentValue()==-1){
				hold.percentSlot.setText("-"+String.valueOf(entryArray.get(position).getWeight())+"%");
				((TextView)hold.percentSlot).setTextColor(0xFFFF0000);
				hold.thumbSlot.setImageDrawable(thumbsDown);
				//hold.percentSlot.setCompoundDrawablesWithIntrinsicBounds(thumbsDown, null, null, null); //up right down and left puts image in listview
				//thumbsUp.setBounds(0, 0, 50, 50);
			}
			
			rowView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					if(position!=0 && position!=entryArray.size()-1){
						entryArray.get(position).toggleCurrentValue();
						notifyDataSetChanged();
					}else if(position==entryArray.size()-1){
						//do add entry listener here
					}
					
				}
				
			});
		
		return rowView;
	}
	
	public void updateList(ArrayList<Entry> newEntries){
		entryArray=newEntries;
		notifyDataSetChanged();
		
	}
	
	public class ViewHolder
	{
	    TextView textSlot;
	    TextView percentSlot;
	    ImageView thumbSlot;
	    
	    
	}

}
