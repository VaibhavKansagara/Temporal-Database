package GUI.Temporal;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;
import Backend.Database;
import Backend.Temporal.TemporalOperations;
import GUI.ViewTable;

class First {
    static JFrame frame;
    static JLabel TableName;
    static JTextField tablename;
    static JLabel ColumnName;
    static JTextField columnname;
    static JButton OK;
    static JTextField value;
    public First(){
        frame = new JFrame("First Operation");
        frame.setBounds(400,400,900,800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName= new JLabel("Table Name");
        tablename = new JTextField();
        ColumnName = new JLabel("Column Name");
        columnname= new JTextField();
        OK= new JButton("OK");

        TableName.setBounds(20,20,100,70);
        tablename.setBounds(130,45,100,20);
        ColumnName.setBounds(20,50,200,70);
        columnname.setBounds(130,75,100,20);
        OK.setBounds(20,140,60,20);


        c.add(TableName);
        c.add((tablename));
        c.add(ColumnName);
        c.add(columnname);
        c.add(OK);


        OK.addActionListener(new ActionListener() {
	    Database db = new Database("root", "root", "EMP");
	    TemporalOperations temporal = new TemporalOperations(db);
            public void actionPerformed(ActionEvent e) {
		ResultSet ans = temporal.First(columnname.getText(), tablename.getText());
		ArrayList<String> colmns = new ArrayList<String>();
		colmns.add(columnname.getText());
		colmns.add("valid_start_time");
		colmns.add("valid_end_time");
		colmns.add("operation_caused");
		ViewTable view = new ViewTable(ans, colmns);
            }  
         });
    }
}