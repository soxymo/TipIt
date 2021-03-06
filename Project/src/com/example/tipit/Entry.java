package com.example.tipit;

import android.util.Log;

public class Entry {
	String text;
	int weight;
	boolean isFirst;
	int currentValue;
	String negText;
	
	public Entry(String text, String negText, int weight, boolean isFirst, int currentValue){
		this.text=text;
		this.weight=weight;
		this.isFirst=isFirst;
		this.currentValue=currentValue;
		this.negText=negText;
	}
	
	public String getText(){
		return text;
	}
	public void setText(String editText){
		text=editText;
	}
	
	public String getNegText(){
		return negText;
	}
	public void setNegText(String editText){
		negText=editText;
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
	
	public void resetCurrentValue(){
		currentValue=0;
	}
	
	public boolean getIsFirst(){
		return isFirst;
	}
	
	
	public String serialize(){
		String output=text+","+String.valueOf(weight)+","+isFirst+","+String.valueOf(currentValue);
		Log.d("RSS",output);
		return output;
	}

}
