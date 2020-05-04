/*
Class for connecting to the database and for extracting information out
of the database and performing various database operations.
*/
package Backend;
import java.sql.*;
import java.util.*;
//import Backend.DML.InsertOperation;
public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost";
    private static Connection connection = null;
    private static PreparedStatement stmt = null;
    private String username;
    private String password;
    private static String dbname;

    public Database(String username, String password, String database_name) {
	this.username = username;
	this.password = password;
	dbname = database_name;
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
		    + "table_schema = " + "'"+dbname+"'";

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
		    + "table_schema = " + "'"+dbname + "'" + " and table_name = " + "'"+tblname+"'";

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
		    + "table_schema = " + "'"+dbname +"'"+ " and table_name = " + "'"+tblname+"'"
		    + " and column_key = 'PRI'";

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

    public void copy_table(String tblname, String tbl_hist,ArrayList<String> temporal_colmns) {
	String sql_query = "INSERT INTO " + tbl_hist + "(";

	Map<String,String> pk = get_primary_key(tblname);
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += e.getKey() + ", ";
	}
	
	boolean first=true;
	for(int i=0;i<temporal_colmns.size();i++){
		if(first){
			first=false;
			sql_query+=temporal_colmns.get(i);
		}
		else{
			sql_query+= ", "+ temporal_colmns.get(i);
		}
	}
	sql_query +=") SELECT ";
	for (Map.Entry<String,String> e: pk.entrySet()) {
	    sql_query += e.getKey() + ", ";
	}
	first=true;
	for(int i=0;i<temporal_colmns.size();i++){
		if(first){
			first=false;
			sql_query+=temporal_colmns.get(i);
		}
		else{
			sql_query+= ", "+ temporal_colmns.get(i);
		}
	}
	sql_query+=" FROM "+ tblname;
	try {
		stmt = connection.prepareStatement(sql_query);
		stmt.execute(); 
	} catch (SQLException e) {
		e.printStackTrace();
	}
    }
    
    public Connection get_connection(){
	return connection;
    }

    public void close_connection() {
	try {
	    connection.close();
	} catch (Exception e) {
	    e.printStackTrace(); 
	} 
    }
	public static void main(String args[]){
		Database db= new Database("srikar","Srikar@1829","EMP");
		Map<String,String> columns=new HashMap<String,String>();
		columns.putAll(db.get_Columns("employee"));
		for(Map.Entry<String,String> e: columns.entrySet()){
			System.out.println(e.getKey());
		}
	}
}
