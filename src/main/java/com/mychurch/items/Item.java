package com.mychurch.items;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Item {
	final private static String idTagString = "IdTag";
	final private static String binNumString = "BinNum";
	final private static String lengthString = "Length";
	final private static String departmentString = "Department";
	final private static String descriptionString = "Description";
	final private static String replacementCostString = "ReplacementCost";
	final private static String priceString = "Price";
	final private static String weightString = "Weight";
	final private static String dimensionsString = "Dimensions";
	final private static String modelNumberString = "ModelNumber";
	final private static String purchaseDateString = "PurchaseDate";
	final private static String warrantyEndDateString = "WarrantyEndDate";
	final private static String categoryString = "Category";
	//data base item fields
	JsonObject jsonItem;
	int idTag;
	int binNum;
	int length;
	int purchaseDate;
	int warrantyDate;
	String modelNumber;
	String department;
	String description;
	String dimensions;
	String category; 
	float replacementCost;
	float price;
	float weight;
	
	//constructor
	public Item(int i, int b, String dep, String desc, float rc, float p, String dim, int l, float w, String m, int pd, int wd, String c){
		idTag = i;
		binNum = b;
		length =l;
		department = dep;
		description = desc;
		dimensions = dim;
		replacementCost = rc;
		price = p;
		weight = w;
		modelNumber = m;
		purchaseDate = pd;
		warrantyDate = wd;
		category = c;
		JsonObjectBuilder factory = Json.createObjectBuilder();
		
		factory.add(idTagString, i);
		factory.add(binNumString, b);
		factory.add(lengthString, l);
		factory.add(departmentString, dep);
		factory.add(descriptionString, desc);
		factory.add(dimensionsString, dim);
		factory.add(replacementCostString, rc);
		factory.add(priceString, p);
		factory.add(weightString, w);
		factory.add(modelNumberString, m);
		factory.add(purchaseDateString, pd);
		factory.add(warrantyEndDateString, wd);
		factory.add(categoryString, c);
		jsonItem = factory.build();
	}
	public Item(JsonObject item){
		String tempString;
		System.out.println(item);
		if(item.containsKey(idTagString)) idTag = Integer.parseInt(item.getString(idTagString));
		else idTag = 0;
		tempString = item.getString(binNumString);
		if(!tempString.isEmpty())
			binNum = Integer.parseInt(tempString);
		tempString = item.getString(lengthString);
		if(!tempString.isEmpty())
			length = Integer.parseInt(tempString);
		else length = 0;
		department = item.getString(departmentString);
		description = item.getString(descriptionString);
		dimensions = item.getString(dimensionsString);
		tempString = item.getString(replacementCostString);
		if(!tempString.isEmpty())
			replacementCost =  Float.parseFloat(tempString);
		else replacementCost = 0;
		tempString = item.getString(priceString);
		if(!tempString.isEmpty())
			price=  Float.parseFloat(tempString);
		else price = 0;
		tempString = item.getString(weightString);
		if(!tempString.isEmpty())
			weight=  Float.parseFloat(tempString);
		else weight = 0;
		modelNumber = item.getString(modelNumberString);
		tempString = item.getString(purchaseDateString);
		if(!tempString.isEmpty()){
			tempString = tempString.replaceAll("-", "");
			purchaseDate = Integer.parseInt(tempString);
		}
		else purchaseDate = 0;
		tempString = item.getString(warrantyEndDateString);
		if(!tempString.isEmpty()){
			tempString = tempString.replaceAll("-", "");
			warrantyDate = Integer.parseInt(tempString);
		}
		else warrantyDate = 0;
		category = item.getString(categoryString);
		jsonItem = item;
	}
	public JsonObject toJSON(){
		return jsonItem;
	}
	public int getBinNum(){
		return binNum;
	}
}
