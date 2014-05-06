package com.example.tipit;

import android.util.Log;

public class Entry {
	String text;
	int weight;
	String type;
	int currentValue;
	
	public Entry(String text, int weight, String type, int currentValue){
		this.text=text;
		this.weight=weight;
		this.type=type;
		this.currentValue=currentValue;
	}
	
	public String getText(){
		return text;
	}
	public void setText(String editText){
		text=editText;
	}

	public int getWeight(){
		return weight;
	}
	
	public void setWeight(int editWeight){
		weight=editWeight;
	}
	
	public int getCurrentValue(){
		return currentValue;
	}
	
	public void toggleCurrentValue(){
		if(weight==0)
			weight=1;
		if(weight==1)
			weight=-1;
		if(weight==-1)
			weight=0;
	}
	
	public String getType(){
		return type;
	}
	
	
	public String serialize(){
		String output=text+","+String.valueOf(weight)+","+type+","+String.valueOf(currentValue);
		Log.d("RSS",output);
		return output;
	}

}
