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

class FirstEvol{
    Database db= new Database("root", "root","EMP");
    static JFrame frame;
    static JLabel TableName;
    static JTextField tablename;
    static JLabel ColumnName;
    static JTextField columnname;
    static JLabel KeyCount;
    static JTextField key_no;
    static JButton OK;
    static JComboBox<String> columns;
    static JLabel column_name;
    static JLabel Value;
    static JTextField value;
    static JButton Back;
    static JButton Run;
    static Map<String,Object> key_val;
    static ArrayList<JComboBox<String>> columns2= new ArrayList<JComboBox<String>>();
    static ArrayList<JTextField> values=new ArrayList<JTextField>();
    public FirstEvol(){
        frame = new JFrame("First Evolution");
        frame.setBounds(400,400,900,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName= new JLabel("Table Name");
        tablename = new JTextField();
        ColumnName = new JLabel("Column Name");
        columnname= new JTextField();
        KeyCount = new JLabel("number of keys");
        key_no = new JTextField();
        OK= new JButton("OK");
        Back= new JButton("Back");

        TableName.setBounds(20,20,100,70);
        tablename.setBounds(120,45,100,20);
        ColumnName.setBounds(20,50,200,70);
        columnname.setBounds(130,75,100,20);
        KeyCount.setBounds(20,100,150,20);
        key_no.setBounds(140,100,30,20);
        OK.setBounds(20,140,60,20);
        Back.setBounds(170,170,100,20);

        c.add(TableName);
        c.add((tablename));
        c.add(ColumnName);
        c.add(columnname);
        c.add(KeyCount);
        c.add(key_no);
        c.add(OK);
        c.add(Back);


        OK.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int count= Integer.parseInt(key_no.getText());
                Map<String,String> key_val= new HashMap<String,String>();
                int i=0;
                Vector<String> v=new Vector<String>();
                for(i=0;i<count;i++){
                    Map<String,String> m=db.get_Columns(tablename.getText()+"_hist");
                    for (Map.Entry<String,String> ed: m.entrySet()) {
                        v.add(ed.getKey());
                }
                    JComboBox<String> columns=new JComboBox<>(v);
                    columns.setEditable(true);
                    column_name=new JLabel("Column Name");
                    Value = new JLabel("Value");
                    value= new JTextField();
                    
                    column_name.setBounds(20,175+(40*i),120,20);
                    Value.setBounds(210,175+(40*i),50,20);
                    columns.setBounds(20,215+(40*i),150,20);
                    value.setBounds(210,215+(40*i),90,20);

                    columns2.add(columns);
                    values.add(value);
                    

                    c.add(columns);
                    c.add(value);
                }
                Run= new JButton("Run");
                Run.setBounds(20,245+(40*i),70,20);
                Run.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        ArrayList<String> col =new ArrayList<String>();
                        TemporalOperations temp_op= new TemporalOperations(db);
                        for(int i=0;i<count;i++){
                            key_val.put(columns2.get(i).getSelectedItem().toString(),values.get(i).getText());
                        }
                        ResultSet rs=null;
                        rs=temp_op.First_Evolution(key_val,columnname.getText(),tablename.getText());
                        for (int j=0; j< v.size();j++) {
                                col.add(v.get(j));
                        }
                        ViewTable view = new ViewTable(rs,col);
                    }
                });
                c.add(Run);
                c.add(column_name);
                c.add(Value);
            }  
         });

         Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }  
             });
    }
    public static void main(String args[]){
        FirstEvol evolution = new FirstEvol();
    }
}