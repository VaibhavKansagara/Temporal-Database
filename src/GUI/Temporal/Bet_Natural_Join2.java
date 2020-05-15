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

class Bet_Natural_Join2 {
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel Date1;
    static JTextField date1;
    static JLabel Date2;
    static JTextField date2;
    static JButton OK;
    static JTextField value;
    public Bet_Natural_Join2(){
	frame = new JFrame("Bet_Natural_Join2 Operation");
	frame.setBounds(400,400,900,800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	Container c=frame.getContentPane();
	c.setLayout(null);

	TableName1 = new JLabel("non temporal table name");
	tablename1 = new JTextField();
	TableName2 = new JLabel("temporal table name");
	tablename2 = new JTextField();
	Date1 = new JLabel("Date1");
	date1 = new JTextField();
	Date2 = new JLabel("Date2");
	date2 = new JTextField();
	OK= new JButton("OK");

    TableName1.setBounds(20,20,200,70);
    tablename1.setBounds(200,45,100,20);
    TableName2.setBounds(20,50,200,70);
	tablename2.setBounds(200,75,100,20);
	Date1.setBounds(20,80,200,70);
	date1.setBounds(130,105,100,20);
	Date2.setBounds(20,110,200,70);
	date2.setBounds(130,145,100,20);
    OK.setBounds(20,170,60,20);

    c.add(TableName1);
    c.add((tablename1));
    c.add(TableName2);
	c.add(tablename2);
	c.add(Date1);
	c.add(date1);
	c.add(Date2);
	c.add(date2);
    c.add(OK);

        OK.addActionListener(new ActionListener() {
	    Database db = new Database("root", "root", "EMP");
	    TemporalOperations temporal = new TemporalOperations(db);
            public void actionPerformed(ActionEvent e) {
		String tbl1 = tablename1.getText();
		String tbl2 = tablename2.getText();

		java.util.Date dt1 = new java.util.Date();
		java.text.SimpleDateFormat sdf1 = new java.text.SimpleDateFormat(date1.getText());
		String Time1 = sdf1.format(dt1);

		java.util.Date dt2 = new java.util.Date();
		java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat(date2.getText());
		String Time2 = sdf2.format(dt2);
		
		ResultSet ans = temporal.Between_Natural_join2(tbl1, tbl2,Time1, Time2);
		ArrayList<String> colmns = new ArrayList<String>();

		Map<String,String> pk = db.get_primary_key(tbl1);
		Map<String,String> cols1 = db.get_Columns(tbl1);
		ArrayList <String> cols2 = temporal.get_Temporal_Columns(tbl2 + "_hist");

        for (Map.Entry<String,String> e1: cols1.entrySet()) {	
                colmns.add(e1.getKey());
        }

		for(int i=0;i<cols2.size();i++){
		    if (pk.get(cols2.get(i)) == null) {
			   colmns.add(cols2.get(i));
		    }
		}
		colmns.add("valid_start_time");
		colmns.add("valid_end_time");


		ViewTable view = new ViewTable(ans, colmns);
            }  
         });
	}

    public static void main(String args[]){
             Bet_Natural_Join2 bet_natural_join2 = new Bet_Natural_Join2();
    }

}