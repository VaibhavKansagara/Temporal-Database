package Backend.DML;

import java.sql.*;
import java.util.*;

public class InsertOperation {
    private Connection connection = null;
    private PreparedStatement stmt = null;

    public InsertOperation(Connection c, PreparedStatement p) {
	connection = c;
	stmt = p;
    }

    public void insert_trigger(String tblname, String tbl_hist, Map<String,String> colmns) {
        String sql_query = "create trigger insert_after_" + tblname + " after insert "
                  + "on " + tblname + " "
                  + "for each row "
                  + "begin "
                  + "insert into " + tbl_hist + "( ";
        boolean first = true;
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