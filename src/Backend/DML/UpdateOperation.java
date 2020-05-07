package Backend.DML;

import java.sql.*;
import java.util.*;
import Backend.Database;
public class UpdateOperation {
    //private static Connection connection = null;
    private static PreparedStatement stmt = null;
    private static Database db;
    public UpdateOperation(Database database) {
	db=database;
    }

    public void update(Map<String,Object> key,Map<String,Object> colmns, String tblname) {
	String sql_query = "update " + tblname + " set ";
	boolean first = true;
        for (Map.Entry<String,Object> e: colmns.entrySet()) {
            if (first) {
                first = false;
                sql_query += e.getKey() + "=" + ((String)e.getValue());
            } else {
                sql_query += ", " + e.getKey() + "=" + ((String)e.getValue());
            }
        }
	sql_query += " where ";
	first = true;
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
	    stmt.execute(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public void update_trigger(String tblname, String tbl_hist, ArrayList<String> temporal_colmns) {
	String sql_query = "create trigger update_after_" + tblname + " after update "
			  + "on " + tblname + " "
			  + "for each row "
			  + "begin update " + tbl_hist + " "
			  + "set valid_end_time = NOW() , operation_caused = 'update' "+" where ";

	Map<String,String> pk = db.get_primary_key(tblname);
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += e.getKey() + " = old." + e.getKey() + " and ";
	}

	boolean first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += temporal_colmns.get(i) + " = old." + temporal_colmns.get(i);
		} else {
			sql_query += " and " + temporal_colmns.get(i) + " = old." + temporal_colmns.get(i);
		}
	}
	sql_query += " and valid_end_time is null; ";
	
	// Now insert into the hist_table
	sql_query += "insert into " + tbl_hist + "( ";
	
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += e.getKey() + ", ";
	}
	
	first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += temporal_colmns.get(i);
		} else {
			sql_query += ", " + temporal_colmns.get(i);
		}
	}

	sql_query +=  ", operation_caused) values( ";

	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += "new." + e.getKey() + ", ";
	}

	first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += "new." + temporal_colmns.get(i);
		} else {
			sql_query += ", new." + temporal_colmns.get(i);
		}
	}

	sql_query +=  " , 'update' ); END";
	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    stmt.execute(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
	public static void main(String args[]){
        Database d= new Database("srikar","Srikar@1829","EMP");
        UpdateOperation upd= new UpdateOperation(d);
		Map <String,Object> row= new HashMap<String,Object>();
		Map<String,Object> key= new HashMap<String,Object>();
		key.put("EMP_ID","'123'");
        row.put("EMP_ID", "'123'");
        row.put("EMP_NAME","'ABC'");
        row.put("EMP_ADDR","'college'");
        row.put("EMP_PHN","1234567890");
        String tbl="employee";
		//upd.update(key,row,tbl);
		
		Map <String,Object> row2= new HashMap<String,Object>();
		Map<String,Object> key2= new HashMap<String,Object>();
		key2.put("EMP_ID","'234'");
        row2.put("EMP_ID", "'234'");
        row2.put("EMP_NAME","'GHI'");
        row2.put("EMP_ADDR","'college2'");
        row2.put("EMP_PHN","1821931750");
		//upd.update(key2,row2,tbl);
		
		Map <String,Object> row3= new HashMap<String,Object>();
		Map<String,Object> key3= new HashMap<String,Object>();
		key3.put("EMP_ID","'345'");
        row3.put("EMP_ID", "'345'");
        row3.put("EMP_NAME","'DEF'");
        row3.put("EMP_ADDR","'college3'");
        row3.put("EMP_PHN","1111111111");
		//upd.update(key3,row3,tbl);
		

		Map <String,Object> row4= new HashMap<String,Object>();
		Map<String,Object> key4= new HashMap<String,Object>();
		key4.put("EMP_ID","'123'");
        row4.put("EMP_ID", "'123'");
        row4.put("EMP_NAME","'ABC'");
        row4.put("EMP_ADDR","'college'");
        row4.put("EMP_PHN","9848032919");
		//upd.update(key,row4,tbl);
		
		Map <String,Object> row5= new HashMap<String,Object>();
		Map<String,Object> key5= new HashMap<String,Object>();
		key5.put("EMP_ID","'234'");
        row5.put("EMP_ID", "'234'");
        row5.put("EMP_NAME","'GHI'");
        row5.put("EMP_ADDR","'college2'");
        row5.put("EMP_PHN","4432129491");
		//upd.update(key5,row5,tbl);


		Map <String,Object> row6= new HashMap<String,Object>();
		Map<String,Object> key6= new HashMap<String,Object>();
		key6.put("EMP_ID","'345'");
        row6.put("EMP_ID", "'345'");
        row6.put("EMP_NAME","'DEF'");
        row6.put("EMP_ADDR","'college3'");
        row6.put("EMP_PHN","9949291909");
		//upd.update(key6,row6,tbl);
   }
}
