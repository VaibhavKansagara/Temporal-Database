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
	
	Map<String,String> pk = db.get_primary_key(tblname);
	for (Map.Entry<String,String> e: pk.entrySet()) {
		sql_query += e.getKey() + ", ";
	}

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

	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += "new." + e.getKey() + ", ";
	}

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
    public static void main(String args[]){
        Database d= new Database("srikar","Srikar@1829","EMP");
        InsertOperation ins= new InsertOperation(d);
        Map <String,Object> row= new HashMap<String,Object>();
        row.put("EMP_ID", "'123'");
        row.put("EMP_NAME","'ABC'");
        row.put("EMP_ADDR","'home'");
        row.put("EMP_PHN","1234567890");
        String tbl="employee";
        //ins.insert(row,tbl);


        Map <String,Object> row2= new HashMap<String,Object>();
        row2.put("EMP_ID", "'345'");
        row2.put("EMP_NAME","'DEF'");
        row2.put("EMP_ADDR","'home'");
        row2.put("EMP_PHN","1111111111");
        //ins.insert(row2,tbl);



        Map <String,Object> row3= new HashMap<String,Object>();
        row3.put("EMP_ID", "'234'");
        row3.put("EMP_NAME","'GHI'");
        row3.put("EMP_ADDR","'home'");
        row3.put("EMP_PHN","1821931750");
        //ins.insert(row3,tbl);
        
        
        
        Map <String,Object> row4= new HashMap<String,Object>();
        String tbl2="department";
        row4.put("DEPT_ID", "'232'");
        row4.put("DEPT_NAME","'abc'");
        row4.put("DEPT_ADDR","'Ecity'");
        //ins.insert(row4,tbl2);
        
        Map <String,Object> row5= new HashMap<String,Object>();
        row5.put("DEPT_ID", "'275'");
        row5.put("DEPT_NAME","'def'");
        row5.put("DEPT_ADDR","'Silkboard'");
        ins.insert(row5,tbl2);
   }
}