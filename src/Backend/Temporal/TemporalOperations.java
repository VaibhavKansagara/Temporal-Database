package Backend.Temporal;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import Backend.Database;

public class TemporalOperations {
    PreparedStatement stmt=null;
    private static Database db;

    public TemporalOperations(Database database) {
	db = database;
    }
	public void Extract_ResultSet(ResultSet rs,ArrayList<String> colmn){
		try{
			while(rs.next()){
			//Retrieve by column name
			for(int i=0;i<colmn.size();i++){
				String colmn_val  = rs.getString(colmn.get(i));
				System.out.println(colmn.get(i)+" : " + colmn_val+"\n");
			}
		}
	}
		catch(SQLException se){
			se.printStackTrace();
		}
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
		Map <String,String> s=db.get_Columns(tblname+"_hist");
		ArrayList <String> cols= new ArrayList<String>();
		for (Map.Entry<String,String> e: s.entrySet()) {
        		cols.add(e.getKey());
		}
		Extract_ResultSet(ans,cols);
		return ans;
	}
	
	
	public ResultSet At(String dat,String tblname){
		ResultSet ans = null; 
		String sql_query = "select * from "+ tblname+"_hist where ";
		sql_query+= "valid_start_time " + "<= '"+ dat+"' ";
		sql_query+= " and (" + "valid_end_time" + ">= '"+dat+"' " + " or " +"valid_end_time " + "= null )";
	
		try {
			stmt = db.get_connection().prepareStatement(sql_query);
			ans = stmt.executeQuery(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map <String,String> s=db.get_Columns(tblname+"_hist");
		ArrayList <String> cols= new ArrayList<String>();
		for (Map.Entry<String,String> e: s.entrySet()) {
            		cols.add(e.getKey());
		}
		Extract_ResultSet(ans,cols);
		return ans;
	}
	
	
	public ResultSet Between_And(String dat1,String dat2,String tblname){
		ResultSet ans = null;  
		String sql_query = "select * from "+ tblname+"_hist where ";
		sql_query+= "valid_start_time " + "<= '"+dat2+"' ";
		sql_query+= " and ('" + dat1 +"' " +"<="+"valid_end_time "+ " or " + "valid_end_time "+ "= null ) ";
	
		try {
			stmt = db.get_connection().prepareStatement(sql_query);
			ans = stmt.executeQuery(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Map <String,String> s=db.get_Columns(tblname+"_hist");
		ArrayList <String> cols= new ArrayList<String>();
		for (Map.Entry<String,String> e: s.entrySet()) {
        		cols.add(e.getKey());
		}
		Extract_ResultSet(ans,cols);
		return ans;
	}

    public ResultSet First(String colmn, String tblname) {
	ResultSet ans = null;
	ArrayList<String> col = new ArrayList<String>();
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
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet Last(String colmn, String tblname) {
	ResultSet ans = null;
	// code
	ArrayList<String> col = new ArrayList<String>();
	String sql_query = "select distinct "+ colmn + ",valid_start_time,valid_end_time from " +
			   tblname + "_hist where ";
	sql_query += "valid_start_time = (select max(valid_start_time) from "
		     + tblname + "_hist)";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    ans = stmt.executeQuery(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet Previous(String colmn, String tblname, String val) {
	ResultSet ans = null;
	ArrayList<String> col = new ArrayList<String>();
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
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet Next(String colmn, String tblname, String val) {
	ResultSet ans = null;
	// code
	ArrayList<String> col = new ArrayList<String>();
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
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	ArrayList<String> col = new ArrayList<String>();
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
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet First_Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	ArrayList<String> col = new ArrayList<String>();
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
				+ tblname + "_hist where ";
	first = true;
	for (Map.Entry<String,Object> e: key.entrySet()) {
		if (first) {
		first = false;
		sql_query += e.getKey() + "=" + ((String)e.getValue());
		} else {
		sql_query += " and " + e.getKey() + "=" + ((String)e.getValue());
		}
	}
	sql_query += ")";
	try {
		stmt = db.get_connection().prepareStatement(sql_query);
		ans = stmt.executeQuery(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
	col.add(colmn);
	Extract_ResultSet(ans, col);
	return ans;
    }

    public ResultSet Last_Evolution(Map<String,Object> key, String colmn, String tblname) {
	ResultSet ans = null;
	ArrayList<String> col = new ArrayList<String>();
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
				+ tblname + "_hist where ";
	first = true;
	for (Map.Entry<String,Object> e: key.entrySet()) {
		if (first) {
			first = false;
			sql_query += e.getKey() + "=" + ((String)e.getValue());
		} else {
			sql_query += " and " + e.getKey() + "=" + ((String)e.getValue());
		}
	}
	sql_query += ")";
	try {
		stmt = db.get_connection().prepareStatement(sql_query);
		ans = stmt.executeQuery(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
	Extract_ResultSet(ans, col);
	return ans;
    }

	public ResultSet Evolution_Val12(Map<String,Object> key, String colmn, String tblname,
				     String val1, String val2) {
		ResultSet ans = null;
		String query1 = "select min(valid_start_time) from " + tblname + "_hist where ";
		boolean first = true;
		for (Map.Entry<String,Object> e: key.entrySet()) {
			if (first) {
				first = false;
				query1 += e.getKey() + "=" + ((String)e.getValue());
			} else {
				query1 += " and " + e.getKey() + "=" + ((String)e.getValue());
			}
		}
		query1 += " and " + colmn + "=" + val1;

		String query2 = "select max(valid_start_time) from " + tblname + "_hist where ";
		first = true;
		for (Map.Entry<String,Object> e: key.entrySet()) {
			if (first) {
				first = false;
				query2 += e.getKey() + "=" + ((String)e.getValue());
			} else {
				query2 += " and " + e.getKey() + "=" + ((String)e.getValue());
			}
		}
		query2 += " and " + colmn + "=" + val2;
		try {
			stmt = db.get_connection().prepareStatement(query1);
			ResultSet temp = stmt.executeQuery(); temp.next();
			String date1 = temp.getString("min(valid_start_time)");
			stmt = db.get_connection().prepareStatement(query2);
			temp = stmt.executeQuery(); temp.next();
			String date2 = temp.getString("max(valid_start_time)");

			if (date1.compareTo(date2) < 0) {
				String sql_query = "select " + colmn + ", valid_start_time, valid_end_time from "
						+ tblname + "_hist where valid_start_time between '" + date1
						+ "' and '" + date2 + "'";
				stmt = db.get_connection().prepareStatement(sql_query);
				ans = stmt.executeQuery();
			}
			return ans;
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
		//temp_ops.First("EMP_ADDR", "employee");
		
		//temp_ops.Last("EMP_ADDR","employee");


		//temp_ops.Previous("EMP_ADDR","employee", "college2");
		//temp_ops.Next("EMP_ADDR","employee","home");

		//temp_ops.Evolution(m, "EMP_ADDR", "employee");

		//temp_ops.First_Evolution(m,"EMP_ADDR","employee");
		temp_ops.Last_Evolution(m, "EMP_ADDR", "employee");
		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("2020-04-29 18:00:00");
		String currentTime = sdf.format(dt);
		java.util.Date dt2 = new java.util.Date();

		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("2020-04-29 17:00:00");
		String currentTime2 = sdf2.format(dt2);
		//temp_ops.Between_And(currentTime2,currentTime,"employee");
}
}
 
