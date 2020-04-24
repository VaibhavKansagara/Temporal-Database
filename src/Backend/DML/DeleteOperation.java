package Backend.DML;

import java.sql.*;
import java.util.*;
import Backend.Database;
public class DeleteOperation {
    //private static Connection connection = null;
    private static PreparedStatement stmt = null;
	private static Database db;
    public DeleteOperation(Database database) {
		db=database;
    }

    public void delete(Map<String,Object> colmns, String tblname) {
	String sql_query = "delete from " + tblname + " where ";
	boolean first = true;
        for (Map.Entry<String,Object> e: colmns.entrySet()) {
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

    public void delete_trigger(String tblname, String tbl_hist, ArrayList<String> temporal_colmns) {
	String sql_query = "create trigger delete_after_" + tblname + " after delete "
			  + "on " + tblname + " "
			  + "for each row "
			  + "begin update " + tbl_hist + " "
			  + "set VALID_END_DATE = NOW() where ";

	boolean first = true;
	for (int i=0;i<temporal_colmns.size();i++) {
		if (first) {
			first = false;
			sql_query += temporal_colmns.get(i) + " = old." + temporal_colmns.get(i);
		} else {
			sql_query += " and " + temporal_colmns.get(i) + " = old." + temporal_colmns.get(i);
		}
	}
	sql_query += " and VALID_END_DATE is null; END";
	try {
		stmt = db.get_connection().prepareStatement(sql_query);
		stmt.execute(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	public static void main(String args[]){
        Database d= new Database("srikar","Srikar@1829","EMP");
        DeleteOperation ins= new DeleteOperation(d);
        Map <String,Object> row= new HashMap<String,Object>();
        row.put("EMP_ID", "'123'");
        String tbl="employee";
        ins.delete(row,tbl);
   }
}