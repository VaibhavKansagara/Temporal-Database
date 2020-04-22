package Backend.DML;

import java.sql.*;
import java.util.*;

public class UpdateOperation {
    private static Connection connection = null;
    private static PreparedStatement stmt = null;

    public UpdateOperation(Connection c, PreparedStatement p) {
	connection = c;
	stmt = p;
    }

    public static void update(Map<String,String> key,Map<String,String> colmns, String tblname) {
	String sql_query = "update " + tblname + " set ";
	boolean first = true;
        for (Map.Entry<String,String> e: colmns.entrySet()) {
            if (first) {
                first = false;
                sql_query += e.getKey() + "='" + e.getValue() + "'";
            } else {
                sql_query += ", " + e.getKey() + "='" + e.getValue() + "'";
            }
        }
	sql_query += " where ";
	first = true;
        for (Map.Entry<String,String> e: key.entrySet()) {
            if (first) {
                first = false;
                sql_query += e.getKey() + "='" + e.getValue() + "'";
            } else {
                sql_query += " and " + e.getKey() + "='" + e.getValue() + "'";
            }
	}

	try {
	    stmt = connection.prepareStatement(sql_query);
	    stmt.execute(); 
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    public static void update_trigger(String tblname, String tbl_hist, Map<String,String> colmns) {
	String sql_query = "create trigger update_after_" + tblname + " after update "
			  + "on " + tblname + " "
			  + "for each row "
			  + "begin update " + tbl_hist + " "
			  + "set VALID_END_DATE = NOW() where ";

	boolean first = true;
	for (Map.Entry<String,String> e: colmns.entrySet()) {
		if (first) {
			first = false;
			sql_query += e.getKey() + " = old." + e.getKey();
		} else {
			sql_query += " and " + e.getKey() + " = old." + e.getKey();
		}
	}
	sql_query += " and VALID_END_DATE is null; ";
	
	// Now insert into the hist_table
	sql_query += "insert into " + tbl_hist + "( ";
	first = true;
	for (Map.Entry<String,String> e: colmns.entrySet()) {
		if (first) {
			first = false;
			sql_query += e.getKey();
		} else {
			sql_query += ", " + e.getKey();
		}
	}

	sql_query +=  ") values( ";
	
	first = true;
	for (Map.Entry<String,String> e: colmns.entrySet()) {
		if (first) {
			first = false;
			sql_query += "new." + e.getKey();
		} else {
			sql_query += ", new." + e.getKey();
		}
	}

	sql_query +=  "); END";
	try {
		stmt = connection.prepareStatement(sql_query);
		stmt.execute(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }
}