/*
Class for connecting to the database and for extracting information out
of the database and performing various database operations.
*/
package Backend;
import java.sql.*;
import java.util.*;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost";
    private Connection connection = null;
    PreparedStatement stmt = null;
    private String username;
    private String password;
    private String dbname;

    public Database(String username, String password, String dbname) {
	this.username = username;
	this.password = password;
	this.dbname = dbname;
	DB_URL = DB_URL + "/" + dbname;

	try {
	    // Register a JDBC Driver
	    Class.forName(JDBC_DRIVER);

	    // Open a connection to the database
	    connection = DriverManager.getConnection(DB_URL, username, password);
	} catch (ClassNotFoundException e) {
	    System.out.println("Driver Not Found: " + e);
	} catch (SQLException e) {
	    System.out.println("SQL Exception: " + e);
        }
    }

    public ArrayList<String> get_tables() {
	ArrayList<String> tables = new ArrayList<String>();
	String sql_query = "select table_name FROM information_schema.tables where table_type = 'BASE TABLE' "
		    + "table_schema = " + dbname;

        try {
	    stmt = connection.prepareStatement(sql_query);
	    ResultSet rs = stmt.executeQuery();
	    while(rs.next()) {
		tables.add(rs.getString("TABLE_NAME"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
        return tables;
    }
    
    public Map<String,String> get_Columns(String tblname) {
	Map<String,String> columns = new 	HashMap<String,String>();
	String sql_query = "select column_name, column_type from information_schema.columns where "
		    + "table_schema = " + dbname + " and table_name = " + tblname;

        try {
	    stmt = connection.prepareStatement(sql_query);
	    ResultSet rs = stmt.executeQuery();
	    while(rs.next()) {
		columns.put(rs.getString("COLUMN_NAME"), rs.getString("COLUMN_TYPE"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
        return columns;
    }

    public Map<String,String> get_primary_key(String tblname) {
	Map<String,String> pk = new HashMap<String,String>();
	String sql_query = "select column_name, column_type from information_schema.columns where "
		    + "table_schema = " + dbname + " and table_name = " + tblname
		    + " column_key = 'PRI'";

        try {
	    stmt = connection.prepareStatement(sql_query);
	    ResultSet rs = stmt.executeQuery();
	    while(rs.next()) {
		pk.put(rs.getString("COLUMN_NAME"), rs.getString("COLUMN_TYPE"));
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return pk;
    }

    public void addFKconstraint(String ref_tblname, String tblname, Map<String,String> pk, Map<String,String> colmns) {
	String sql_query = "ALTER TABLE " + ref_tblname + "ADD FOREIGN KEY(";
	boolean first = true;
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey();
	    } else {
		sql_query += "," + e.getKey();
	    }
	}
	sql_query += ") REFERENCES " + tblname + "(";
	first = true;
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    if (first) {
		first = false;
		sql_query += e.getKey();
	    } else {
		sql_query += "," + e.getKey();
	    }
	}
	sql_query += ")";
	try {
	    stmt = connection.prepareStatement(sql_query);
	    stmt.execute();
	    insert_trigger(tblname, ref_tblname, colmns);
	    update_trigger(tblname, ref_tblname, colmns);
	    delete_trigger(tblname, ref_tblname, colmns);
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }

    public void copy_table(String tblname, String tbl_hist) {
	String sql_string = "INSERT INTO " + tbl_hist + " SELECT * FROM " + tblname;
	try {
		stmt = connection.prepareStatement(sql_string);
		stmt.execute(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }

    public void close_connection() {
	try {
		connection.close();
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} 
    }
}
