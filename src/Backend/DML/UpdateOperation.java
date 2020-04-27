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
			  + "set valid_end_time = NOW() where ";

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
	first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += temporal_colmns.get(i);
		} else {
			sql_query += ", " + temporal_colmns.get(i);
		}
	}

	sql_query +=  ") values( ";
	
	first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += "new." + temporal_colmns.get(i);
		} else {
			sql_query += ", new." + temporal_colmns.get(i);
		}
	}

	sql_query +=  "); END";
	try {
		stmt = db.get_connection().prepareStatement(sql_query);
		stmt.execute(); 
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}
}