package GUI.Temporal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Key;
import java.sql.ResultSet;
import java.util.*;
import Backend.Database;
import Backend.DDL.CreateOperation;
import Backend.Temporal.*;
import GUI.ViewTable;

public class Non_temporal_cross_join {
    Database db= new Database("srikar", "Srikar@1829","EMP");
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static ArrayList<JTextField> tables=new ArrayList<JTextField>();
    static JButton Run;
    public Non_temporal_cross_join(){
        frame = new JFrame("Non Temporal Cross Join");
        frame.setBounds(400,400,900,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1= new JLabel("Table Name 1");
        tablename1 = new JTextField();
        TableName2= new JLabel("Table Name 2");
        tablename2 = new JTextField();
        Run = new JButton("Run");

        TableName1.setBounds(20,20,100,70);
        tablename1.setBounds(120,45,100,20);
        TableName2.setBounds(20,50,200,70);
        tablename2.setBounds(130,75,100,20);
        Run.setBounds(20,120,70,20);
        tables.add(tablename1);
        tables.add(tablename2);

        c.add(TableName1);
        c.add((tablename1));
        c.add(TableName2);
        c.add(tablename2);
        c.add(Run);


        Run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> col =new ArrayList<String>();
                TemporalOperations temp_op= new TemporalOperations(db);
                ResultSet rs=null;
                rs=temp_op.non_temporal_cross_join(tablename1.getText(),tablename2.getText());
                Map <String,String> s1=db.get_Columns(tablename1.getText());
		    for (Map.Entry<String,String> ed: s1.entrySet()) {
        		col.add(ed.getKey());
		    }
		    Map <String,String> s2=db.get_Columns(tablename2.getText());
		    for (Map.Entry<String,String> ed: s2.entrySet()) {
        		col.add(ed.getKey());
		    }
                ViewTable view = new ViewTable(rs,col);
            }  
         });
    }
    public static void main(String args[]){
        Non_temporal_cross_join n1 = new Non_temporal_cross_join();
    }
}