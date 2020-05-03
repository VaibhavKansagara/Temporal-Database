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

    public boolean create_hist_table(String tblname, String tbl_hist,ArrayList<String>temporal_colmns,
    				     ArrayList<String> temporal_col) {
	String s1="insert";
	String sql_query = "create table if not exists " + tbl_hist + "( ";
	boolean first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
	    if (first) {
		first = false;
		sql_query += temporal_colmns.get(i) + " ";
	    } else {
		sql_query += "," + temporal_colmns.get(i);
	    }
	}

	//sql_query += ", valid_start_time DATETIME DEFAULT NOW(), valid_end_time DATETIME NULL )";
	sql_query += ", valid_start_time DATETIME DEFAULT NOW(), valid_end_time DATETIME NULL, operation_caused char(100) DEFAULT 'insert'" +" ) ";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    stmt.execute();
	    // copy the values from table to tbl_hist.
	    db.copy_table(tblname, tbl_hist,temporal_col);
	    InsertOperation ins_obj= new InsertOperation(db);
	    UpdateOperation upd_obj= new UpdateOperation(db);
	    DeleteOperation del_obj= new DeleteOperation(db);
	    ins_obj.insert_trigger(tblname, tbl_hist, temporal_col);
	    upd_obj.update_trigger(tblname, tbl_hist, temporal_col);
	    del_obj.delete_trigger(tblname, tbl_hist, temporal_col);
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}

	return true;
    }
}
