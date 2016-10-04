package com.mychurch.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mychurch.dbconnectors.DBConnecter;
@Path("/query")
public class queryController {
	@GET
	@Path("/sum")
	@Produces(MediaType.TEXT_PLAIN)
	public long sumDatabase(@QueryParam("column") String column){
		 // TODO ADD column query protection possibly add bin support
		System.out.println(column);
		Connection database = DBConnecter.getConnection();
		ResultSet rs = null;
		Statement stat = null;
		long sum= 0;
		try{
			stat = database.createStatement();
			rs = stat.executeQuery("Select Sum("+column+") from item;");
			if(rs.next()){
				sum = rs.getLong("Sum("+column+")");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				stat.close();
				rs.close(); //close the query result table
				database.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return sum;
	}
	//add ability to search by bin or team ext;
	public int sumAdvance(){
		return 0;
	}
}
