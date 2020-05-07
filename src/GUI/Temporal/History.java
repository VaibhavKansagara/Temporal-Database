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

class History {
    static JFrame frame;
    static JLabel TableName;
    static JTextField tablename;
    static JButton OK;

    public History(){
        frame = new JFrame("History Operation");
        frame.setBounds(400,400,900,800);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName= new JLabel("Table Name");
        tablename = new JTextField();
        OK= new JButton("OK");

        TableName.setBounds(20,20,100,70);
        tablename.setBounds(130,45,100,20);
        OK.setBounds(20,100,60,20);


        c.add(TableName);
        c.add((tablename));
        c.add(OK);


        OK.addActionListener(new ActionListener() {
            Database db = new Database("root", "root", "EMP");
            TemporalOperations temporal = new TemporalOperations(db);

            public void actionPerformed(ActionEvent e) {

                ResultSet ans = temporal.History(tablename.getText());
                ArrayList<String> colmns = new ArrayList<String>();

                Map <String,String> s=db.get_Columns(tablename.getText()+"_hist");
        		for (Map.Entry<String,String> col: s.entrySet()) {
            		colmns.add(col.getKey());
		        }

                ViewTable view = new ViewTable(ans, colmns);

            }  
        });
    }

    // public static void main(String args[]){
    //         History history = new History();
    // }


}