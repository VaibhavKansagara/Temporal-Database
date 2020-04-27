package Backend.DML;

import java.sql.*;
import java.util.*;
import Backend.Database;
public class InsertOperation {
    //private static Connection connection = null;
    private static PreparedStatement stmt = null;
    private static Database db;
    public InsertOperation(Database database) {
        db=database;
    }
    
    public void insert(Map<String,Object> colmns, String tblname) {
	String sql_query = "insert into " + tblname + "(";
	boolean first = true;
        for (Map.Entry<String,Object> e: colmns.entrySet()) {
            if (first) {
                first = false;
                sql_query += e.getKey();
            } else {
                sql_query += ", " + e.getKey();
            }
        }

	sql_query +=  ") values( ";
	first = true;
        for (Map.Entry<String,Object> e: colmns.entrySet()) {
            if (first) {
                first = false;
                sql_query += ((String)e.getValue());
            } else {
                sql_query += ", " + ((String)e.getValue());
            }
	}
	sql_query +=  ")";

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    stmt.execute(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public void insert_trigger(String tblname, String tbl_hist, ArrayList<String> temporal_colmns) {
        String sql_query = "create trigger insert_after_" + tblname + " after insert "
                  + "on " + tblname + " "
                  + "for each row "
                  + "begin "
                  + "insert into " + tbl_hist + "( ";
        boolean first = true;
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