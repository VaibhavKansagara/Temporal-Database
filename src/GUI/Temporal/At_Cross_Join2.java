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


public class At_Cross_Join2 {
    Database db= new Database("srikar", "Srikar@1829","EMP");
    static JFrame frame;
    static JLabel TableName1;
    static JTextField tablename1;
    static JLabel TableName2;
    static JTextField tablename2;
    static JLabel Date;
    static JTextField date;
    static ArrayList<JTextField> tables=new ArrayList<JTextField>();
    static JButton Run;
    public At_Cross_Join2(){
        frame = new JFrame("History Cross Join");
        frame.setBounds(400,400,900,800);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);

        TableName1= new JLabel("Temporal Table");
        tablename1 = new JTextField();
        TableName2= new JLabel("Non Temporal Table");
        tablename2 = new JTextField();
        Date= new JLabel("Date");
        date= new JTextField();
        Run = new JButton("Run");

        TableName1.setBounds(20,20,170,70);
        tablename1.setBounds(200,45,100,20);
        TableName2.setBounds(20,50,200,70);
        tablename2.setBounds(230,75,100,20);
        Date.setBounds(20,120,150,20);
        date.setBounds(140,120,150,20);
        Run.setBounds(20,160,70,20);
        tables.add(tablename1);
        tables.add(tablename2);

        c.add(TableName1);
        c.add((tablename1));
        c.add(TableName2);
        c.add(tablename2);
        c.add(Date);
        c.add(date);


        Run.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                TemporalOperations temp_op= new TemporalOperations(db);
                ResultSet rs=null;
                rs=temp_op.At_cross_join2(tablename1.getText(),tablename2.getText(),date.getText());
                ArrayList<String> s1=new ArrayList<String>();
                Map<String,String> s2=new HashMap<String,String>();
                s1=temp_op.get_Temporal_Columns(tablename1.getText()+"_hist");
            s2=db.get_Columns(tablename2.getText());
            for (Map.Entry<String,String> ed: s2.entrySet()) {
        		s1.add(ed.getKey());
		    }
		    
            s1.add("valid_start_time");
            s1.add("valid_end_time");
            //System.out.println("hello");
            
                ViewTable view = new ViewTable(rs,s1);
            }  
         });
         c.add(Run);
    }
    public static void main(String args[]){
        At_Cross_Join2 h1= new At_Cross_Join2();
    }
}