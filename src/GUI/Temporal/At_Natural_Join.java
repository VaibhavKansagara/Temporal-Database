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

class At_Natural_Join {
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel Date;
    static JTextField date;
    static JButton OK;
    static JTextField value;
    public At_Natural_Join(){
        frame = new JFrame("At_Natural_Join Operation");
        frame.setBounds(400,400,900,800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1 = new JLabel("First Table Name");
        tablename1 = new JTextField();
        TableName2 = new JLabel("Second Table Name");
	tablename2 = new JTextField();
	Date = new JLabel("Date");
        date = new JTextField();
        OK= new JButton("OK");

        TableName1.setBounds(20,20,100,70);
        tablename1.setBounds(130,45,100,20);
        TableName2.setBounds(20,50,200,70);
	tablename2.setBounds(130,75,100,20);
	Date.setBounds(20,80,200,70);
	date.setBounds(130,105,100,20);
        OK.setBounds(20,140,60,20);


        c.add(TableName1);
        c.add((tablename1));
        c.add(TableName2);
	c.add(tablename2);
	c.add(Date);
	c.add(date);
        c.add(OK);


        OK.addActionListener(new ActionListener() {
	    Database db = new Database("srikar", "Srikar@1829", "EMP");
	    TemporalOperations temporal = new TemporalOperations(db);
            public void actionPerformed(ActionEvent e) {
		String tbl1 = tablename1.getText();
		String tbl2 = tablename2.getText();

		java.util.Date dt = new java.util.Date();
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(date.getText());
		String currentTime = sdf.format(dt);
		
		ResultSet ans = temporal.At_Natural_join(tbl1, tbl2,currentTime);
		ArrayList<String> colmns = new ArrayList<String>();

		Map<String,String> pk = db.get_primary_key(tbl1);
		ArrayList <String> cols1 = temporal.get_Temporal_Columns(tbl1);
		ArrayList <String> cols2 = temporal.get_Temporal_Columns(tbl2);
		for(int i=1;i<cols1.size();i++){
		    colmns.add(cols1.get(i));
		}
		for(int i=1;i<cols2.size();i++){
		    if (pk.get(cols2.get(i)) == null)
		   	colmns.add(cols2.get(i));
		}
		colmns.add("valid_start_time");
		colmns.add("valid_end_time");
		ViewTable view = new ViewTable(ans, colmns);
            }  
         });
    }
}