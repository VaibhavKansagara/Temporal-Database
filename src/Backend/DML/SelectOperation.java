package Backend.DML;

import java.sql.*;
import java.util.*;

public class SelectOperation {
    private Connection connection = null;
    private PreparedStatement stmt = null;

    public SelectOperation(Connection c, PreparedStatement p) {
	connection = c;
	stmt = p;
    }

    public static select() {
	String sql_query = "select * from " + tblname + " where ";
	boolean first = true;
        for (Map.Entry<String,String> e: colmns.entrySet()) {
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
}
