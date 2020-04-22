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
}
