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

public class Between_And_Cross_Join {
    Database db= new Database("srikar", "Srikar@1829","EMP");
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel Date1;
    static JTextField date1;
    static JLabel Date2;
    static JTextField date2;
    static ArrayList<JTextField> tables=new ArrayList<JTextField>();
    static JButton Run;
    public Between_And_Cross_Join(){
        frame = new JFrame("Between Ands Cross Join");
        frame.setBounds(400,400,900,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1= new JLabel("Table Name 1");
        tablename1 = new JTextField();
        TableName2= new JLabel("Table Name 2");
        tablename2 = new JTextField();
        Date1= new JLabel("Date 1");
        date1= new JTextField();
        Date2= new JLabel("Date 2");
        date2= new JTextField();
        Run = new JButton("Run");

        TableName1.setBounds(20,20,100,70);
        tablename1.setBounds(120,45,100,20);
        TableName2.setBounds(20,50,200,70);
        tablename2.setBounds(130,75,100,20);
        Date1.setBounds(20,120,150,20);
        date1.setBounds(140,120,150,20);
        Date2.setBounds(20,150,150,20);
        date2.setBounds(140,150,150,20);
        Run.setBounds(20,180,70,20);
        tables.add(tablename1);
        tables.add(tablename2);

        c.add(TableName1);
        c.add((tablename1));
        c.add(TableName2);
        c.add(tablename2);
        c.add(Date1);
        c.add(date1);
        c.add(Date2);
        c.add(date2);


        Run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                TemporalOperations temp_op= new TemporalOperations(db);
                ResultSet rs=null;
                rs=temp_op.Between_And_cross_join(tablename1.getText(),tablename2.getText(),date1.getText(),date2.getText());
                ArrayList<String> s1=new ArrayList<String>();
                ArrayList<String> s2=new ArrayList<String>();
                s1=temp_op.get_Temporal_Columns(tablename1.getText()+"_hist");
            s2=temp_op.get_Temporal_Columns(tablename2.getText()+"_hist");
            System.out.println(s1.size());
            s1.add("valid_start_time");
            s1.add("valid_end_time");
                ViewTable view = new ViewTable(rs,s1);
            }  
         });
         c.add(Run);
    }
    public static void main(String args[]){
        Between_And_Cross_Join h1= new Between_And_Cross_Join();
    }
}