package Backend.DDL;

import java.sql.*;
import java.util.*;

import Backend.DML.DeleteOperation;
import Backend.DML.InsertOperation;
import Backend.DML.UpdateOperation;
import Backend.Database;

public class CreateOperation {
    PreparedStatement stmt=null;
    private static Database db;

    public CreateOperation(Database database) {
		db = database;
    }

    public void create_table(String query){
	try{
	    stmt = db.get_connection().prepareStatement(query);
	    stmt.executeUpdate();
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public boolean create_hist_table(String tblname, String tbl_hist, Map<String,String> colmns) {
	String sql_query = "create table if not exists " + tbl_hist + "( ";
	boolean first = true;
	for (Map.Entry<String,String> e: colmns.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey() + " " + e.getValue();
	    } else {
		sql_query += "," + e.getKey() + " " + e.getValue();
	    }
	}

	sql_query += ", valid_start_time DATETIME DEFAULT NOW(), valid_end_time DATETIME NULL )";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    stmt.execute();
	    // copy the values from table to tbl_hist.
	    db.copy_table(tblname, tbl_hist,colmns);
	    InsertOperation.insert_trigger(tblname, tbl_hist, colmns);
	    UpdateOperation.update_trigger(tblname, tbl_hist, colmns);
	    DeleteOperation.delete_trigger(tblname, tbl_hist, colmns);
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}

	return true;
    }
}
