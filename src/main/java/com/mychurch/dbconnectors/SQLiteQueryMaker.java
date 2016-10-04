package com.mychurch.dbconnectors;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mychurch.bin.Bin;
import com.mychurch.bin.BinManager;
import com.mychurch.items.Item;
import com.mychurch.items.ItemManager;
import com.mychurch.user.User;


public class SQLiteQueryMaker {
	private PreparedStatement prep;
	private Connection databaseConnection;
	SQLiteQueryMaker(Connection connect){
		databaseConnection = connect;
	}/*s
	public ArrayList<Bin> search(BinManager bin){
		 ArrayList<Bin> bins = new ArrayList<Bin>();
		 //try searching for bins return lists
		
		 /*try{
			 /*
	            String sqlQueryString = "select * from Bin ";
	            String temp, builder = "";
	            //try building a string with spec. fields checking for danger cases
	            temp = bin.getBinNum();
	            if(temp != null&& temp.equals("*")&&temp.equals( "%") &&temp.equals("")) builder += " BinNum = " + temp + ",";
	            
	            temp = bin.descriptionField.getText();
	        	if(temp != null && temp.equals("*")&&temp.equals( "%") &&temp.equals("")) builder += " Description = " + temp + ",";
	        	
	        	temp = bin.dimensionsField.getText();
	        	if(temp != null && temp.equals("*")&&temp.equals( "%") &&temp.equals("")) builder += " Dimensions = " + temp + ",";
	        	
	        					   
	            if(!builder.equals("")){ sqlQueryString += "where " + builder;
	            	sqlQueryString = sqlQueryString.substring(0, sqlQueryString.length()-1);
	            }
	        
	            prep = databaseConnection.prepareStatement(sqlQueryString + ";");
				prep.addBatch();
				databaseConnection.setAutoCommit(false);
				ResultSet rs = prep.executeQuery();
				databaseConnection.setAutoCommit(true);
		                   
	            while (rs.next() && bins.size() < 100){
		            Bin binNew = new Bin(
		            		rs.getInt("BinNum"),
		            		rs.getString("Dimensions"),
		            		rs.getString("Description")		            		
		                	);
		            bins.add(binNew);
		        }
		        rs.close();
	    		return bins;
			}catch(SQLException e) {
				e.printStackTrace();
				return bins;
			}
	}
	//public boolean search(UserInfo user){return false;}
	
	//modify Bins or Items
	/*
	public boolean modify(ItemInfo item){
		try{	
			prep = databaseConnection.prepareStatement("Update Item SET BinNum=?, Length=?, Category=?, Description=?, Dimensions=?, ReplacementCost=?, Price=?, Wieght=?  where IdTag=? ;");
			prep.setInt(1, item.binNumField.toString());
			prep.setInt(2, item.getLength());
			prep.setString(3, item.categoryField.getText());
			prep.setString(4, item.descriptionField.getText());
			prep.setString(5, item.dimensionsField.getText());
			prep.setFloat(6, item.getReplacementPrice());
			prep.setFloat(7, item.getPrice());
			prep.setString(8, item.wieghtField.getText());
			prep.setInt(9, item.getItemID());
			prep.addBatch();
			databaseConnection.setAutoCommit(false);
			prep.executeBatch();
			databaseConnection.setAutoCommit(true);
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/
	public boolean modify(BinManager bin){
		/*
		try{
			//databaseConnection.
			
			prep = databaseConnection.prepareStatement("Update Bin SET Dimensions=?, Description=? where BinNum=? ;");
			prep.setString(1, bin.dimensionsField.getText());
			prep.setString(2, bin.descriptionField.getText());
			prep.setInt(3, bin.getBinNum());
			prep.addBatch();
			databaseConnection.setAutoCommit(false);
			prep.executeBatch();
			databaseConnection.setAutoCommit(true);
			
		}catch	(SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}*/
		return true;
	}
	
	//add Item, Bin or User
	//delete Item, Bin or User
	public boolean delete(Item item){return false;}
	public boolean delete(Bin bin){return false;}
	public boolean delete(User user){return false;}
}