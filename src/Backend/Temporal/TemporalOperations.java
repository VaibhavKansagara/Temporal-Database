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
	return ans;
    }

    public ResultSet Previous(String colmn, String tblname, String val) {
	ResultSet ans = null;
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from "
			    + tblname + "_hist where valid_start_time = (select "
			    + "max(valid_start_time) from " + tblname + "_hist where "
			    + "valid_start_time < (select min(valid_start_time) where "
			    + colmn + "='" + val + "') and "
			    + colmn + "='" + val + "') and "
			    + colmn + "='" + val + "')";
	
	try {
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
	return ans;
    }

    public ResultSet Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	String sql_query = "select "+ colmn + ",valid_start_time,valid_end_time from "
			    + tblname + " where ";

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
	return ans;
    }
}
