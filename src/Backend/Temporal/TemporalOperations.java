package Backend.Temporal;

import java.sql.*;
import java.util.*;

import Backend.Database;

public class TemporalOperations {
    PreparedStatement stmt=null;
    private static Database db;

    public TemporalOperations(Database database) {
	db = database;
    }
	public void Extract_ResultSet(ResultSet rs,String colmn){
		int count=0;
		try{
			
			while(rs.next()){
			//Retrieve by column name
			System.out.println(count);
			String colmn_val  = rs.getString(colmn);
			String valid_start = rs.getString("valid_start_time");
			String valid_end = rs.getString("valid_end_time");
			count=count+1;
			//Display values
			System.out.print(colmn+":" + colmn_val);
			System.out.println(", valid_start_time: " + valid_start);
			System.out.println(", valid_end_time" + valid_end);
		 }
		}
		catch(SQLException se){
			se.printStackTrace();
		}
	}
    public ResultSet First(String colmn, String tblname) {
	ResultSet ans = null;
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from " +
			   tblname + "_hist where ";
	sql_query += "valid_start_time = (select min(valid_start_time) from "
		     + tblname + "_hist)";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
    }

    public ResultSet Last(String colmn, String tblname) {
	ResultSet ans = null;
	// code
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from " +
			   tblname + "_hist where ";
	sql_query += "valid_start_time = (select max(valid_start_time) from "
		     + tblname + "_hist)";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
    }

    public ResultSet Previous(String colmn, String tblname, String val) {
	ResultSet ans = null;
	try{
		String sql_query = "select "+ colmn + ", valid_start_time ,valid_end_time from "
			    + tblname + "_hist where "
			    + " valid_start_time = (select max(valid_start_time) from "
			    + tblname + "_hist where "
			    + "valid_start_time < (select min(valid_start_time) from "
			    + tblname + "_hist where "
			    + colmn + "='" + val + "' ))";
	    stmt = db.get_connection().prepareStatement(sql_query);
		ans = stmt.executeQuery();
	
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
	return ans;
    }

    public ResultSet Next(String colmn, String tblname, String val) {
	ResultSet ans = null;
	// code
	try{
		String sql_query = "select "+ colmn + ", valid_start_time ,valid_end_time from "
			    + tblname + "_hist where "
			    + " valid_start_time = (select min(valid_start_time) from "
			    + tblname + "_hist where "
			    + "valid_start_time > (select max(valid_start_time) from "
			    + tblname + "_hist where "
			    + colmn + "='" + val + "' ))";
	    stmt = db.get_connection().prepareStatement(sql_query);
		ans = stmt.executeQuery();
	
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
	return ans;
    }

    public ResultSet Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from "
			    + tblname +"_hist "+ " where ";

	boolean first = true;
	for (Map.Entry<String,Object> e: key.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey() + "=" + ((String)e.getValue());
	    } else {
		sql_query += " and " + e.getKey() + "=" + ((String)e.getValue());
	    }
	}
	
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
	return ans;
    }

    public ResultSet First_Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from " +
			   tblname + "_hist where ";

	boolean first = true;
	for (Map.Entry<String,Object> e: key.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey() + "=" + ((String)e.getValue());
	    } else {
		sql_query += " and " + e.getKey() + "=" + ((String)e.getValue());
	    }
	}

	sql_query += "and valid_start_time = (select min(valid_start_time) from "
		     + tblname + "_hist)";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
    }

    public ResultSet Last_Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	// code	
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from " +
			   tblname + "_hist where ";

	boolean first = true;
	for (Map.Entry<String,Object> e: key.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey() + "=" + ((String)e.getValue());
	    } else {
		sql_query += " and " + e.getKey() + "=" + ((String)e.getValue());
	    }
	}

	sql_query += "and valid_start_time = (select max(valid_start_time) from "
		     + tblname + "_hist)";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
	}



	public ResultSet History(String tblname) {
	ResultSet ans = null;
	String sql_query = "select * from "+ tblname+"_hist";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
	}


	public ResultSet At(DATETIME dat,String tblname){
	ResultSet ans = null;

	String sql_query = "select * from "+ tblname+"_hist where ";
	sql_query+= tblname.valid_start_time + "<="+((String)dat);
	sql_query+= " and (" + tblname.valid_end_time + ">="+((String)dat) + " or " + tblname.valid_end_time + "= null )";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return ans;
	}


	public ResultSet Between_And(DATETIME dat1,DATETIME dat2,String tblname){
	ResultSet ans = null;

	String sql_query = "select * from "+ tblname+"_hist where ";
	sql_query+= tblname.valid_start_time + "<="+((String)dat2);
	sql_query+= " and (" + ((String)dat1) + "<="+tblname.valid_end_time + " or " + tblname.valid_end_time + "= null )";

	// String sql_query = "select * from "+ tblname+"_hist where ";
	// sql_query+= tblname.valid_start_time + "<="+((String)dat1);
	// sql_query+= " and (" + tblname.valid_end_time + ">="+((String)dat2) + " or " + tblname.valid_end_time + "= null )";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}

	return ans;
	}


	public static void main(String args[]){
		Database d = new Database("srikar", "Srikar@1829", "EMP");
		Map <String,Object> m= new HashMap<String,Object>();
		m.put("EMP_PHN","1111111111");
		TemporalOperations temp_ops= new TemporalOperations(d);
		ResultSet r=temp_ops.First_Evolution(m,"EMP_ADDR", "employee");
		temp_ops.Extract_ResultSet(r, "EMP_ADDR");
	}
}
