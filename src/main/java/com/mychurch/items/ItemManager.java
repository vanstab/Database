package com.mychurch.items;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.mychurch.dbconnectors.DBConnecter;
@Path("/item")
public class ItemManager{
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
	
	private List<Item> items = new ArrayList<Item>();
	private int maxLength = 1000;
	@POST
	@Path("/update")
	@Consumes("application/json")
	public void updateItemInfo(Item item){
		
	}
	@POST
	@Consumes("application/json")
	public void newItem(JsonObject jsonItem){
		Item item = new Item(jsonItem);
		//add to database
		String newItem = "INSERT INTO ITEM(BinNum, Department," +
				"Description, ReplacementCost, Price, Dimensions, Length, Weight, ModelNumber,PurchaseDate, WarrantyEndDate, Category) VALUES ("+
				item.binNum+",'" + item.department + "','" +
				item.description + "',"  + item.replacementCost + "," + 
				item.price + ",'" + item.dimensions + "'," +
				item.length + "," + item.weight + "," + item.modelNumber + "," + 
				item.purchaseDate + "," + item.warrantyDate +"," + item.category +  ");";

		Connection database = DBConnecter.getConnection();		
		Statement stat = null;
		try {
			System.out.println(newItem);
			stat = database.createStatement();
			stat.executeUpdate(newItem);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stat.close(); //close the query result table
				database.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		items.add(item);
	}
	/* 
	 * Get an item from the db by its id tag
	 */
	@GET
	public JsonObject getItem(@QueryParam("id") int id){
		Connection database = DBConnecter.getConnection();
	    Statement stat = null;
	    ResultSet rs = null;
	    System.out.print(id);
		try {
			Item item = null;
			stat = database.createStatement();
			String sqlQueryString = "select * from item where idTag='" + id+ "';";
			rs = stat.executeQuery(sqlQueryString);
			stat.executeQuery(sqlQueryString);

			while(rs.next()){
				item = new Item(
	            		rs.getInt(idTagString),
	            		rs.getInt(binNumString),
	            		rs.getString(departmentString),
	            		rs.getString(descriptionString),
	            		rs.getFloat(replacementCostString),
	            		rs.getFloat(priceString),
	            		rs.getString(dimensionsString),
	            		rs.getInt(lengthString),
	            		rs.getFloat(weightString),
			           	rs.getString(modelNumberString),
			          	rs.getInt(purchaseDateString),
			        	rs.getInt(warrantyEndDateString),
			        	rs.getString(categoryString)
	            		);
			}
			if(item != null)
				return item.toJSON();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stat.close();
				database.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		}
		return null;
	}
	/*
	 * Get all items from the db up to maxLength items
	 */
	@GET
	@Path("/all")
	@Produces("application/json")
	public JsonArray getAllItems(){
		 	Connection database = DBConnecter.getConnection();
		    Statement stat = null;
		    ResultSet rs = null;
			try {
				stat = database.createStatement();
				String sqlQueryString = "select * from item;";
				rs = stat.executeQuery(sqlQueryString);
				stat.executeQuery(sqlQueryString);
				while(rs.next()){
					Item itemNew = new Item(
		            		rs.getInt(idTagString),
		            		rs.getInt(binNumString),
		            		rs.getString(departmentString),
		            		rs.getString(descriptionString),
		            		rs.getFloat(replacementCostString),
		            		rs.getFloat(priceString),
		            		rs.getString(dimensionsString),
		            		rs.getInt(lengthString),
		            		rs.getFloat(weightString),
		            		rs.getString(modelNumberString),
		            		rs.getInt(purchaseDateString),
		            		rs.getInt(warrantyEndDateString),
		            		rs.getString(categoryString)
		            		);
		            items.add(itemNew);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					rs.close();
					stat.close();
					database.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
			}
			return getListAsJson(items);
	}
	@POST
	@Path("/search")
	@Consumes("application/json")
	@Produces("application/json")
	public JsonArray search(JsonObject jsonItem){
		ArrayList<Item> queryList = new ArrayList<Item>();
		Connection database = DBConnecter.getConnection();
		Statement stat = null;
		ResultSet rs =null;
		boolean flag = false;
		try{
			String sqlQueryString = "select * from item ";
	        String temp = "", builder = "";
	        //try building a string with spec. fields checking for danger cases
	        temp = jsonItem.getString(binNumString);
	        if(temp != null && !temp.equals("*") && !temp.equals( "%") && !temp.equals("")){
	        	flag = true;
	        	builder = builder.concat(" BinNum=" + temp);
	        }
	        temp = jsonItem.getString(lengthString);
	        if(queryVerify(temp)){
	        	if(flag)
	        		builder =  builder.concat(" AND Length=" + temp);
	        	else{
	        		flag = true;
	        		builder =  builder.concat(" Length=" + temp);
	        	}
	        }	        	
	        temp = jsonItem.getString(departmentString);
	        if(queryVerify(temp)){
	        	if(flag)
	        		builder =  builder.concat(" AND Department='" + temp + "' ");
	        	else{
	        		flag = true;
	        		builder =  builder.concat(" Department='" + temp + "' ");
	        	}
	        }
	        temp = jsonItem.getString(descriptionString);
	        if(queryVerify(temp)){
	        	if(flag)
	        		builder = builder.concat(" AND Description='" + temp + "' ");
	        	else{
	        		flag = true;
	        		builder = builder.concat(" Description='" + temp + "' ");
	        	}
	        }
	        temp = jsonItem.getString(dimensionsString);
	        if(queryVerify(temp)){
	        	if(flag)
	        		builder =  builder.concat(" AND Dimensions='" + temp + "' ");
	        	else{
	        		flag = true;
	        		builder =  builder.concat(" Dimensions='" + temp + "' ");
	        	}
	        }
	        temp = jsonItem.getString(replacementCostString);
	        if(queryVerify(temp)){
	        	if(flag)
	        		builder =  builder.concat(" AND ReplacementCost=" + temp);
	        	else{
	        		flag = true;
	        		builder =  builder.concat(" ReplacementCost=" + temp);
	        	}
	        }
	   			
	   		temp = jsonItem.getString(priceString);
	   		if(queryVerify(temp)){
	   			if(flag)
	   				builder =  builder.concat(" AND Price=" + temp);
	   			else{
	   				flag = true;
	   				builder =  builder.concat(" Price=" + temp);
	   			}
	   		}
	        	
	   		temp = jsonItem.getString(weightString);
	   		if(queryVerify(temp)){
	   			if(flag)
	   				builder =  builder.concat(" AND Weight='" + temp + "',");
	   			else{
	   				flag = true;
	   				builder =  builder.concat(" Weight='" + temp + "',");
	   			}
	   		}
	   		
	   		temp = jsonItem.getString(modelNumberString);
	   		if(queryVerify(temp)){
	   			if(flag)
	   				builder =  builder.concat(" AND ModelNumber='" + temp + "',");
	   			else{
	   				flag = true;
	   				builder =  builder.concat(" ModelNumber='" + temp + "',");
	   			}
	   		}
	   		temp = jsonItem.getString(warrantyEndDateString);
	   		if(queryVerify(temp)){
	   			if(flag){
	   				temp = temp.replaceAll("-", "");
	   				builder =  builder.concat(" AND WarrantyEndDate='" + temp + "',");
	   			}
	   			else{
	   				flag = true;
	   				temp = temp.replaceAll("-", "");
	   				builder =  builder.concat(" WarrantyEndDate='" + temp + "',");
	   			}
	   		}
	   		temp = jsonItem.getString(purchaseDateString);
	   		if(queryVerify(temp)){
	   			if(flag){
	   				temp = temp.replaceAll("-", "");
	   				builder =  builder.concat(" AND PurchaseDate='" + temp + "',");
	   			}
	   			else{
	   				flag = true;
	   				temp = temp.replaceAll("-", "");
	   				builder =  builder.concat(" PurchaseDate='" + temp + "',");
	   			}
	   		}
	   		temp = jsonItem.getString(categoryString);
	   		if(queryVerify(temp)){
	   			if(flag)
	   				builder =  builder.concat(" AND Category='" + temp + "',");
	   			else{
	   				flag = true;
	   				builder =  builder.concat(" Category='" + temp + "',");
	   			}
	   		}
	   		
	   		
				   			
	   		temp = jsonItem.getString(idTagString);
	   		if(queryVerify(temp)){
	   			if(flag)
	   				builder =  builder.concat(" AND IdTag=" + temp);
	   			else{
	   				flag = true;
	   				builder =  builder.concat(" IdTag=" + temp);
	   			}
	   		}
				   
	        if(!builder.equals("")){ sqlQueryString = sqlQueryString + "where " + builder + ";";
	        }
	        else sqlQueryString = sqlQueryString +";";
	        System.out.println(sqlQueryString);
	        stat = database.createStatement();
			rs = stat.executeQuery(sqlQueryString);
			database.setAutoCommit(true);
	        while (rs.next() && items.size() < 100){
	        	Item itemNew = new Item(
		            		rs.getInt(idTagString),
		            		rs.getInt(binNumString),
		            		rs.getString(departmentString),
		            		rs.getString(descriptionString),
		            		rs.getFloat(replacementCostString),
		            		rs.getFloat(priceString),
		            		rs.getString(dimensionsString),
		            		rs.getInt(lengthString),
		            		rs.getFloat(weightString),
		            		rs.getString(modelNumberString),
		            		rs.getInt(purchaseDateString),
		            		rs.getInt(warrantyEndDateString),
		            		rs.getString(categoryString)
		            		);
	        	queryList.add(itemNew);
		    }
	    	return getListAsJson(queryList);
		}catch(Exception e) {
			e.printStackTrace();
			return getListAsJson(items);
		}finally{
			try {
				rs.close();
				stat.close();
				database.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		}
	}
	
	private JsonArray getListAsJson(List<Item> itemList){
		JsonArrayBuilder builder = Json.createArrayBuilder();
		JsonArray list;
		int counter = 0;
		for(Item item:itemList){
			if(counter == maxLength)
				break;
			counter++;
			builder.add(item.toJSON());
		}
		list = builder.build();
		return list;
	}
	
	//returns proper values should call error if doesn't work
	public String getItemId(){
		return "";
	}
	
	@DELETE
	public void deleteItem(@QueryParam("id")int id){
		Connection database = DBConnecter.getConnection();
	    Statement stat = null;
	    System.out.print(id);
		try {
			stat = database.createStatement();
			String insertStatement = "INSERT INTO DeletedItem (IdTag,BinNum,ModelNumber,Department,Description,ReplacementCost,Price,Dimensions,Length,Weight,PurchaseDate,WarrantyEndDate,Category) select * From item where idTag='"+id+"';"; 
			String deleteStatement = "Delete From Item where idTag='"+id+"';";
			stat.executeUpdate(insertStatement);
			stat.executeUpdate(deleteStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stat.close();
				database.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		}
	}
	private boolean queryVerify(String temp){
		return (temp != null && !temp.equals("*") && !temp.equals( "%") && !temp.equals(""));
	}
}
