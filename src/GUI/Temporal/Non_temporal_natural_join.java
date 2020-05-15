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

class Non_temporal_natural_join {
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel columnname;
    static JComboBox<String>  Columnlist1;
    static JComboBox<String>  Columnlist2;
    static JButton OK1;
    static JButton OK2;


    public Non_temporal_natural_join(){
        frame = new JFrame("non temporal natural join");
        frame.setBounds(400,400,900,800);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1 = new JLabel("Table Name 1");
        tablename1 = new JTextField();
        TableName2 = new JLabel("Table Name 2");
        tablename2 = new JTextField();
        columnname = new JLabel("Column names");
        OK1= new JButton("OK");
        OK2= new JButton("OK");

        TableName1.setBounds(20,20,100,70);
        tablename1.setBounds(130,45,100,20);
        TableName2.setBounds(20,50,200,70);
        tablename2.setBounds(130,75,100,20);
        OK1.setBounds(20,140,60,20);


        c.add(TableName1);
        c.add(tablename1);
        c.add(TableName2);
        c.add(tablename2);
        c.add(OK1);


        OK1.addActionListener(new ActionListener() {
	    Database db = new Database("srikar", "Srikar@1829", "EMP");
	    TemporalOperations temporal = new TemporalOperations(db);

            public void actionPerformed(ActionEvent e) {


                Map<String,String> cols1 = db.get_Columns(tablename1.getText());
                Map<String,String> cols2 = db.get_Columns(tablename2.getText());


                Vector<String> temp_col1=new Vector<String>();
                Vector<String> temp_col2=new Vector<String>();


                for (Map.Entry<String,String> e1: cols1.entrySet()) {	
                        temp_col1.add(e1.getKey());
                }

                for (Map.Entry<String,String> e1: cols2.entrySet()) {	
                        temp_col2.add(e1.getKey());
                }



                Columnlist1 = new JComboBox<>(temp_col1);
                Columnlist2 = new JComboBox<>(temp_col2);

                columnname.setBounds(20,170,100,20);
                Columnlist1.setBounds(20,200,100,20);
                Columnlist2.setBounds(20,230,100,20);
                OK2.setBounds(20,260,100,20);


                c.add(columnname);
                c.add(Columnlist1);
                c.add(Columnlist2);
                c.add(OK2);



                OK2.addActionListener(new ActionListener(){

                    public void actionPerformed(ActionEvent e){

                        String columnname1,columnname2;
                        columnname1 = Columnlist1.getSelectedItem().toString();
                        columnname2 = Columnlist2.getSelectedItem().toString();


                        ResultSet ans = temporal.non_temporal_natural_join(tablename1.getText(),tablename2.getText(),columnname1 ,columnname2);

                        ArrayList<String> colmns = new ArrayList<String>();

                        for(int i=0;i<temp_col1.size();i++){
                            colmns.add(temp_col1.get(i));
                        }

                        for(int i=0;i<temp_col2.size();i++){
                            colmns.add(temp_col2.get(i));
                        }

                        ViewTable view = new ViewTable(ans, colmns);


                    }

                });
            }
        });

    }

    public static void main(String args[]){
            Non_temporal_natural_join non_temporal_natural_join = new Non_temporal_natural_join();
    }

}