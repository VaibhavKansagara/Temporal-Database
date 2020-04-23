package Backend.DML;

import java.sql.*;
import java.util.*;
import Backend.Database;
public class SelectOperation {
    //private Connection connection = null;
    private static PreparedStatement stmt = null;
    private static Database db;
    public SelectOperation(Database database) {
        db=database;
    }

    public static void select(Map<String,Object> colmns, String tblname) {
	String sql_query = "select * from " + tblname + " where ";
	boolean first = true;
        for (Map.Entry<String,Object> e: colmns.entrySet()) {
            if (first) {
                first = false;
                sql_query += e.getKey() + "='" + ((String)e.getValue()) + "'";
            } else {
                sql_query += " and " + e.getKey() + "='" + ((String)e.getValue()) + "'";
            }
        }

	try {
	    stmt = db.get_connection().prepareStatement(sql_query);
	    stmt.execute(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }
}
