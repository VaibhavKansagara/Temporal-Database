package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class ColumnPage {
    private JFrame frame;
    private JLabel Name;
    private JLabel Temporal;
    private ArrayList<JLabel> name_list= new ArrayList<JLabel>();
    private ArrayList<JCheckBox> temporal_list= new ArrayList<JCheckBox>();

    public ColumnPage(ArrayList<String> tblnames) {
        frame= new JFrame("Temporal Columns");
        frame.setBounds(400,400,900,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Container c=frame.getContentPane();
        c.setLayout(null);
        Name= new JLabel("ColumnNames");
        Temporal= new JLabel("Temporal");

        Name.setBounds(50,135,170,20);
        Temporal.setBounds(200,135,100,20);
        int i;
        for(i=0;i<10;i++){
		JLabel name= new JLabel("column_name");
		name.setBounds(30,175+(40*i),100,20);
		name_list.add(name);

		JCheckBox temporal =new JCheckBox();
		temporal.setBounds(200,175+(40*i),90,20);
		temporal_list.add(temporal);

		c.add(name);
		c.add(temporal);
        }
        JButton GenerateQuery= new JButton("Generate Query");
        GenerateQuery.setBounds(30,215+(40*i),150,20);
        c.add(GenerateQuery);

        c.add(Name);
        c.add(Temporal);
    }
}
