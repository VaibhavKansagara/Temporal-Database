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

class Between_And {
    static JFrame frame;
    static JLabel TableName;
    static JTextField tablename;
    static JLabel Date1;
    static JTextField date1;
    static JLabel Date2;
    static JTextField date2;
    static JButton OK;

    public Between_And(){
        frame = new JFrame("At Operation");
        frame.setBounds(400,400,900,800);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName= new JLabel("Table Name");
        tablename = new JTextField();
        Date1 = new JLabel("Date1");
        date1= new JTextField();
        Date2 = new JLabel("Date2");
        date2 = new JTextField();
        OK= new JButton("OK");

        TableName.setBounds(20,20,100,70);
        tablename.setBounds(130,45,100,20);
        Date1.setBounds(20,50,200,70);
        date1.setBounds(130,75,100,20);
        Date2.setBounds(20,80,200,70);
        date2.setBounds(130,105,100,20);
        OK.setBounds(20,140,60,20);


        c.add(TableName);
        c.add((tablename));
        c.add(Date1);
        c.add(date1);
        c.add(Date2);
        c.add(date2);
        c.add(OK);


        OK.addActionListener(new ActionListener() {
	    Database db = new Database("srikar", "Srikar@1829", "EMP");
	    TemporalOperations temporal = new TemporalOperations(db);

            public void actionPerformed(ActionEvent e) {

                ResultSet ans = temporal.Between_And(date1.getText(), date1.getText(), tablename.getText());
                ArrayList<String> colmns=temporal.get_Temporal_Columns(tablename.getText()+"_hist");
                colmns.add("valid_start_time");
                colmns.add("valid_end_time");
                colmns.add("operation_caused");


                ViewTable view = new ViewTable(ans, colmns);
            }  
        });
    }

    public static void main(String args[]){
             Between_And between_And = new Between_And();
    }

}
