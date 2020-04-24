package GUI.DDL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.*;
import Backend.Database;
import Backend.DDL.CreateOperation;

class CreatePage {
    static JFrame frame;
    static JLabel TableName;
    static JTextField tablename;
    static JLabel AttributeCount;
    static JTextField Attr_no;
    static JLabel Name;
    static JLabel Type;
    static JLabel Length;
    static JLabel Not_Null;
    static JLabel PrimaryKey;
    static JLabel Referencing;
    static JLabel Temporal;
    static JButton OK;
    static JButton GenerateQuery;
    static JButton ExecuteQuery;
    static int i;
    static ArrayList<JTextField> name_list= new ArrayList<JTextField>();
    static ArrayList<JComboBox<String>> type_list = new ArrayList<JComboBox<String>>();
    static ArrayList<JTextField> length_list= new ArrayList<JTextField>();
    static ArrayList<JCheckBox> not_null_list= new ArrayList<JCheckBox>();
    static ArrayList<JRadioButton> primarykey_list= new ArrayList<JRadioButton>();
    static ArrayList<JComboBox<String>> referencing_list = new ArrayList<JComboBox<String>>();
    static ArrayList<JCheckBox> temporal_list= new ArrayList<JCheckBox>();
    static ArrayList<String> temporal_columns=new ArrayList<String>();
    static ArrayList<String> length_string_list=new ArrayList<String>();
    static ArrayList<String> temporal_columns2=new ArrayList<String>();
    String main_table="";
    public CreatePage() {
        frame= new JFrame("New Table");
        frame.setBounds(400,400,900,800);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);
        TableName= new JLabel("Table Name");
        tablename = new JTextField();
        AttributeCount= new JLabel("Number of Attributes");
        Attr_no= new JTextField();
        Name= new JLabel("Name");
        Type= new JLabel("Type");
        Length= new JLabel("Length");
        Not_Null= new JLabel("NOT NULL");
        PrimaryKey= new JLabel("Primary Key");
        Referencing= new JLabel("Referencing");
        Temporal= new JLabel("Temporal");
        OK= new JButton("OK");
        
        TableName.setBounds(20,20,100,70);
        tablename.setBounds(120,45,100,20);
        AttributeCount.setBounds(20,70,200,70);
        Attr_no.setBounds(180,95,30,20);
        Name.setBounds(50,135,170,20);
        Type.setBounds(170,135,100,20);
        Length.setBounds(260,135,50,20);
        Not_Null.setBounds(360,135,100,20);
        PrimaryKey.setBounds(470,135,100,20);
        Referencing.setBounds(580,135,100,20);
        Temporal.setBounds(700,135,100,20);
        OK.setBounds(220,95,70,20);

        OK.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               ButtonGroup grp=new ButtonGroup();
                if(Attr_no.getText().trim().length()>0){
                    int num=Integer.parseInt(Attr_no.getText());
                    for(i=0;i<num;i++){
                        JTextField name= new JTextField();
                        name.setBounds(30,175+(40*i),100,20);
                        name_list.add(name);
                        
                        String[] arr={"CHAR","VARCHAR","INTEGER","SMALLINT","BIGINT","REAL","DOUBLE","DECIMAL","DATE","TIME","TIMESTAMP"};
                        JComboBox<String> type= new JComboBox<>(arr);
                        type.setEditable(true);
                        type.setBounds(150,175+(40*i),90,20);
                        type_list.add(type);

                        JTextField length= new JTextField();
                        length.setBounds(260,175+(40*i),50,20);
                        length_list.add(length);
                        
                        JCheckBox not_null=new JCheckBox();
                        not_null.setBounds(380,175+(40*i),50,20);
                        not_null_list.add(not_null);
                        
                        JRadioButton primarykey=new JRadioButton();
                        primarykey.setBounds(500,175+(40*i),50,20);
                        primarykey_list.add(primarykey);
                        grp.add(primarykey);
                        
                        String[] arr2={"NONE"};
                        JComboBox<String> referencing= new JComboBox<>(arr2);
                        referencing.setEditable(true);
                        referencing.setBounds(580,175+(40*i),90,20);
                        referencing_list.add(referencing);
                        
                        JCheckBox temporal =new JCheckBox();
                        temporal.setBounds(725,175+(40*i),50,20);
                        temporal_list.add(temporal);
                        
                        
                        c.add(type);
                        c.add(name);
                        c.add(length);
                        c.add(not_null);
                        c.add(primarykey);
                        c.add(referencing);
                        c.add(temporal);
                    }
                }
		GenerateQuery= new JButton("Generate Query");
		GenerateQuery.setBounds(30,215+(40*i),150,20);
		GenerateQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {     
                // execute sql query.
                
				JTextArea query= new JTextArea();
				//query.setBackground(Color.RED);
				query.setBounds(785,30,400,3000);
                String first_line = "CREATE "+"TABLE " + tablename.getText() +"\n"+"(\n";
                main_table=main_table+first_line;
				//query.append(first_line);
				//query.append("(\n");
                String keys="(";
               for(int i=0;i<name_list.size();i++){
					String nnull="";
                    String PK="";
                    String length_string=" ";
					if(primarykey_list.get(i).isSelected()){
						PK="NOT NULL "+ "UNIQUE ";
						keys=keys+name_list.get(i).getText()+", ";
					}
					if(PK=="" && not_null_list.get(i).isSelected()){
					nnull="NOT NULL";
                    }
                    if(type_list.get(i).getSelectedItem()=="CHAR" || type_list.get(i).getSelectedItem()=="VARCHAR"){
                        length_string="("+length_list.get(i).getText()+") ";
                    }
					String s=name_list.get(i).getText()+" "+type_list.get(i).getSelectedItem()+length_string+ nnull+ PK+","+"\n";
                    main_table=main_table+s;
                    length_string_list.add(length_string);
                }
				
				int ind = keys.lastIndexOf(',');
				keys = keys.substring(0,ind);
				keys=keys+") ";
				
                String last_line="CONSTRAINT "+ "pk_"+tablename.getText()+" PRIMARY KEY "+keys+"\n"+");"+"\n\n";
                main_table=main_table+last_line;
                query.append(main_table);
				//query.append(last_line);
                //query.append(");");
                //query.append("\n\n");
				//for(int i=0;i<temporal_attributes.size();i++){

				//}
				c.add(query);     
			}
        	});
        
        ExecuteQuery= new JButton("Execute Query");
        ExecuteQuery.setBounds(200,215+(40*i),150,20);
        ExecuteQuery.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {     
            Database db= new Database("srikar","Srikar@1829","EMP");
            CreateOperation op= new CreateOperation(db);
            op.create_table(main_table);
            Map<String,String> cols = new HashMap<String,String>();
            for(int i=0;i<name_list.size();i++){
                if(temporal_list.get(i).isSelected()){
                    cols.put(name_list.get(i).getText(),String.valueOf(type_list.get(i).getSelectedItem()));
                }
            }
            String hist_table= tablename.getText()+"_hist";
            for(int i=0;i<temporal_list.size();i++){
                if(temporal_list.get(i).isSelected()){
                    temporal_columns.add(name_list.get(i).getText()+" "+type_list.get(i).getSelectedItem()+length_string_list.get(i));
                    temporal_columns2.add(name_list.get(i).getText());
                }
            }
            if(temporal_columns.size()>0 && op.create_hist_table(tablename.getText(),hist_table,temporal_columns,temporal_columns2)){
                System.out.println("history table created");
            }  
        }
        });

        	c.add(GenerateQuery);
        	c.add(ExecuteQuery);
            }
        });
        

	c.add(TableName);
	c.add(tablename);
	c.add(AttributeCount);
	c.add(Attr_no);
	c.add(Name);
	c.add(Type);
	c.add(Length);
	c.add(Not_Null);
	c.add(PrimaryKey);
	c.add(Referencing);
	c.add(Temporal);
	c.add(OK);
    }
    
}
