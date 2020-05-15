package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Backend.Database;
import Backend.DML.DeleteOperation;


import java.util.*;


class DeletePage {
	static JFrame frame;
	static JLabel TableName, Conditions;
	static JTextField table_name;
	static JTextArea conditions_list;
	static JPanel controlPanel;
	static JButton DeleteButton, AddRows;
	static JScrollPane scrollPane;

	public DeletePage() {
		frame = new JFrame("Delete Operation");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controlPanel = new JPanel();
		controlPanel.setLayout(null);
		frame.add(controlPanel);

		TableName = new JLabel("Table Name");
		
		Dimension table_name_dimensions = TableName.getPreferredSize();
		TableName.setBounds(400-table_name_dimensions.width/2, 40, table_name_dimensions.width, table_name_dimensions.height);

		controlPanel.add(TableName);

		table_name = new JTextField();
		
		table_name.setPreferredSize(new Dimension(200, 24));
		table_name.setBounds(300, 60, 200, 24);

		Conditions = new JLabel("Enter Keys to Delete");
		
		Dimension condition_dimensions = Conditions.getPreferredSize();
		Conditions.setBounds(400-condition_dimensions.width/2, 90, condition_dimensions.width, condition_dimensions.height);

		controlPanel.add(Conditions);

		conditions_list = new JTextArea();
		
		scrollPane = new JScrollPane(conditions_list);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(150, 110, 500, 350);

		DeleteButton = new JButton("Delete Rows of Table");
		DeleteButton.setBounds(400, 500, 300, 24);

		controlPanel.add(table_name);
		controlPanel.add(scrollPane);
		controlPanel.add(DeleteButton);

		DeleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Database db = new Database("srikar","Srikar@1829","EMP");
				String tbl_nm = table_name.getText();
				
				ArrayList<String> pks=new ArrayList<String>(Arrays.asList(conditions_list.getText().split("\\r?\\n")));
				Map<String,Object> row_conditions=new HashMap<String,Object>();
				String pk_nm = db.get_primary_key(tbl_nm).entrySet().iterator().next().getKey();
				DeleteOperation del = new DeleteOperation(db);
				pks.forEach((pk) -> 
					{
						row_conditions.put(pk_nm,"'"+pk+"'");
						del.delete(row_conditions,tbl_nm);
					}
				);
			}
		});

		frame.setVisible(true);

	}
}
