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
import Backend.Temporal.*;

public class When_Cross_Join2 {
    Database db= new Database("srikar", "Srikar@1829","EMP");
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel KeyCount1;
    static JTextField key_no1;
    static JLabel KeyCount2;
    static JTextField key_no2;
    static JButton OK;
    static JComboBox<String> columns1;
    static JComboBox<String> columns2;
    static JLabel column_name1;
    static JLabel Value1;
    static JTextField value1;
    static JTextField value2;
    static JButton Run;
    static Map<String,String> key_val1;
    static Map<String,String> key_val2;
    static Map<String,String> m;
    static ArrayList<JComboBox<String>> columns_list1= new ArrayList<JComboBox<String>>();
    static ArrayList<JComboBox<String>> columns_list2= new ArrayList<JComboBox<String>>();
    static ArrayList<JTextField> values1=new ArrayList<JTextField>();
    static ArrayList<JTextField> values2=new ArrayList<JTextField>();
    public When_Cross_Join2(){
        frame = new JFrame("When Cross Join");
        frame.setBounds(400,400,900,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1= new JLabel("Table Name");
        tablename1 = new JTextField();
        TableName2 = new JLabel("Table Name");
        tablename2= new JTextField();
        KeyCount1 = new JLabel("number of keys");
        key_no1 = new JTextField();
        KeyCount2 = new JLabel("number of keys");
        key_no2 = new JTextField();
        OK= new JButton("OK");

        TableName1.setBounds(20,20,100,70);
        tablename1.setBounds(120,45,100,20);
        KeyCount1.setBounds(20,50,200,70);
        key_no1.setBounds(130,75,30,20);
        TableName2.setBounds(20,100,150,20);
        tablename2.setBounds(140,100,100,20);
        KeyCount2.setBounds(20,130,150,20);
        key_no2.setBounds(150,130,30,20);
        OK.setBounds(20,190,60,20);


        c.add(TableName1);
        c.add((tablename1));
        c.add(TableName2);
        c.add(tablename2);
        c.add(KeyCount1);
        c.add(key_no1);
        c.add(KeyCount2);
        c.add(key_no2);
        c.add(OK);

        OK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int count1= Integer.parseInt(key_no1.getText());
                int count2= Integer.parseInt(key_no2.getText());
                Map<String,String> key_val1= new HashMap<String,String>();
                Map<String,String> key_val2= new HashMap<String,String>();
                int i=0;
                TemporalOperations temp_ops= new TemporalOperations(db);
                ArrayList<String> arr1=temp_ops.get_Temporal_Columns(tablename1.getText()+"_hist");
                ArrayList<String> arr2= new ArrayList<String>();
                Map<String,String> m=db.get_Columns(tablename2.getText());
                for (Map.Entry<String,String> ed: m.entrySet()) {
                    arr2.add(ed.getKey());
                }
                column_name1=new JLabel("Column Name");
                Value1 = new JLabel("Value");
                column_name1.setBounds(20,210,120,20);
                Value1.setBounds(210,210,50,20);
                for(i=0;i<count1;i++){
                   
                    Vector<String> v1=new Vector<String>();
                    
                    for (int j=0;j<arr1.size();j++) {
                        v1.add(arr1.get(j));
                    }
                    
                    JComboBox<String> columns1=new JComboBox<>(v1);
                   

                    columns1.setEditable(true);
                    
                    value1= new JTextField();
                    
                    
                    columns1.setBounds(20,255+(40*i),150,20);
                    value1.setBounds(210,255+(40*i),90,20);

                    columns_list1.add(columns1);
                    values1.add(value1);
                    

                    c.add(columns1);
                    c.add(value1);
                }
                for(i=count1;i<count1+count2;i++){
                    Vector<String> v2=new Vector<String>();
                    for (int j=0;j<arr2.size();j++) {
                        v2.add(arr2.get(j));
                    }
                    JComboBox<String> columns2=new JComboBox<>(v2);

                    columns2.setEditable(true);

                    value2= new JTextField();
                    
                    
                    columns2.setBounds(20,255+(40*i),150,20);
                    value2.setBounds(210,255+(40*i),90,20);

                    columns_list2.add(columns2);
                    values2.add(value2);
                    

                    c.add(columns2);
                    c.add(value2);

                }
                Run= new JButton("Run");
                Run.setBounds(20,285+(40*i),70,20);
                i=0;
                Run.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        ArrayList<String> col =new ArrayList<String>();
                        TemporalOperations temp_op= new TemporalOperations(db);
                        for(int i=0;i<count1;i++){
                            key_val1.put(columns_list1.get(i).getSelectedItem().toString(),values1.get(i).getText());
                        }
                        for(int i=0;i<count1;i++){
                            key_val2.put(columns_list2.get(i).getSelectedItem().toString(),values2.get(i).getText());
                        }
                        ResultSet rs=null;
                        rs=temp_op.when_cross_join2(tablename1.getText(),tablename2.getText(),key_val1,key_val2);
                        for(int i=0;i<arr1.size();i++){
                            col.add(arr1.get(i));
                        }
                        for(int i=0;i<arr2.size();i++){
                            col.add(arr2.get(i));
                        }
                        col.add("valid_start_time");
                        col.add("valid_end_time");
                        ViewTable view = new ViewTable(rs,col);
                    }
                });

                c.add(Run);
                c.add(column_name1);
                c.add(Value1);
                
            }  
         });
    }
    public static void main(String args[]){
        When_Cross_Join2 w = new When_Cross_Join2();
    }
}