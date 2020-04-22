package Backend.DDL;

import java.sql.*;
import java.util.*;

public class CreateOperation {
    private Connection connection = null;
    private PreparedStatement stmt = null;

    public CreateOperation(Connection c, PreparedStatement p) {
	connection = c;
	stmt = p;
    }

    public void create_table(String query){
	System.out.println("hello");
	try{
	    stmt = connection.prepareStatement(query);
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

	sql_query += ", VALID_START_DATE DATETIME DEFAULT NOW() VALID_END_DATE DATETIME )";

	try {
	    stmt = connection.prepareStatement(sql_query);
	    stmt.execute();
	    // copy the values from table to tbl_hist.
	    copy_table(tblname, tbl_hist);
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	}

	return true;
    }
}
