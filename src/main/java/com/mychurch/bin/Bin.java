package com.mychurch.bin;
import java.util.ArrayList;

import com.mychurch.items.Item;


public class Bin {
	//data base bin fields
	int binNum;
	String dimensions;
	String description;
	ArrayList<Item> items;

	//constructor
	public Bin(int i, String a, String b, ArrayList<Item> item){
		binNum = i;
		description = a;
		dimensions = b;
		items = itemsInBin(item);
	}
	public Bin(int i, String a, String b){
		binNum = i;
		description = a;
		dimensions = b;
	}
	//only puts in items that belong to bin
	private ArrayList<Item> itemsInBin(ArrayList<Item> items){
		ArrayList<Item> inBin = new ArrayList<Item>();
		for(Item i: items){
			if(i.getBinNum() == binNum){
				inBin.add(i);
			}
		}
		return inBin;
	}
	//override toString
	public String toString(){
		return "" + binNum + " " + description + " " + dimensions;
	}
	public int getbinNum(){
		return binNum;
	}
	public String getdimensions(){
		return dimensions;
	}
	public String getdescription(){
		return description;
	}
	public ArrayList<Item> getItems(){
		return items;
	}
}
