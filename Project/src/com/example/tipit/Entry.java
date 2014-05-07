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
		if(currentValue==0)
			currentValue=1;
		else if(currentValue==1)
			currentValue=-1;
		else if(currentValue==-1)
			currentValue=0;
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
