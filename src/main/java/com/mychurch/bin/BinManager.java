package com.mychurch.bin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mychurch.dbconnectors.DBConnecter;
@Path("/bins")
public class BinManager {
	final private static String BinNum = "BinNum";
    final private static String Description = "Description";
    final private static String Dimensions = "Demensions";
	@GET
	@Path("/getAllBinNum")
	@Produces(MediaType.APPLICATION_JSON)
    public JsonArray getAllBinNums(){
    	//select BinNum from Bin
		Connection database = DBConnecter.getConnection();
		PreparedStatement prep = null;
		ResultSet rs =null;
		JsonArrayBuilder builder = Json.createArrayBuilder();

		JsonArray list = null;;
		try{
			prep = database.prepareStatement("Select BinNum from Bin;");
			prep.addBatch();
			database.setAutoCommit(false);
			rs = prep.executeQuery();
			database.setAutoCommit(true);
			while(rs.next()){
				builder.add(rs.getInt(BinNum));
			}
			list = builder.build();
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				prep.close();
				rs.close(); //close the query result table
				database.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return list;
    }
    public void getAllBins(){
    	
    }
    
    
    private boolean queryVerify(String temp){
		return (temp != null && !temp.equals("*") && !temp.equals( "%") && !temp.equals(""));
	}
}
