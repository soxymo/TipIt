package com.example.tipit;

public class Entry {
	String text;
	int weight;
	int currentValue;
	
	
	public Entry(String text, int weight){
		this.text=text;
		this.weight=weight;
		currentValue=0;
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

}
