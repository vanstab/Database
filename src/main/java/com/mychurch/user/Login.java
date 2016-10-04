/**
 * 
 */
package com.mychurch.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mychurch.dbconnectors.DBConnecter;

/**
 * @author Benjamin
 *
 */
@Path("/login")
public class Login {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
	public String loginrequest(JsonObject request){
    	String username = request.getString("username");
    	String password = request.getString("password");
        User user = null;
        Connection database = DBConnecter.getConnection();
	    Statement stat;
	    ResultSet rs = null;
		try {
			stat = database.createStatement();

			String sqlQueryString = "select * from User where UserName='" + username +"';";
			rs = stat.executeQuery(sqlQueryString);
			stat.executeQuery(sqlQueryString);
			if(rs.next()) {
				user = new User(rs.getString("UserName"), rs.getString("Password"), rs.getInt("Admin"));
		    }
		  
		    if(user != null && user.isPassword(password)){
		    	System.out.print("Logged In");
		    }
		    else  System.out.print("Failed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				rs.close(); //close the query result table
				database.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		}
		return "Got It!";
	}
}
