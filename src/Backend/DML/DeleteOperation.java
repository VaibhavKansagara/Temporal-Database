package Backend.DML;

import java.sql.*;
import java.util.*;

public class DeleteOperation {
    private static Connection connection = null;
    private static PreparedStatement stmt = null;

    public DeleteOperation(Connection c, PreparedStatement p) {
	connection = c;
	stmt = p;
    }

    public static void delete_trigger(String tblname, String tbl_hist, Map<String,String> colmns) {
	String sql_query = "create trigger delete_after_" + tblname + " after delete "
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
	sql_query += " and VALID_END_DATE is null; END";
	try {
		stmt = connection.prepareStatement(sql_query);
		stmt.execute(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }
}