package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Backend.Database;
import Backend.DML.InsertOperation;


import java.util.*;


class InsertPage {
    static JFrame frame;
    static JLabel TableName;
	static JTextField table_name;
	static JLabel RowsList;
	static JTextArea rows_list;
    static JPanel controlPanel;
    static JButton AddRows, Backbtn;
    static JScrollPane scrollPane;

    public InsertPage() {
		frame = new JFrame("Insert Operation");
		frame.setSize(800,600);
		// frame.setLayout(new GridLayout(2, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		controlPanel = new JPanel();
		// controlPanel.setLayout(new FlowLayout());
		controlPanel.setLayout(null);
		frame.add(controlPanel);

		TableName = new JLabel("Table Name");
		
		Dimension table_name_dimensions = TableName.getPreferredSize();
		TableName.setBounds(400-table_name_dimensions.width/2, 40, table_name_dimensions.width, table_name_dimensions.height);

		controlPanel.add(TableName);

		table_name = new JTextField();
		
		table_name.setPreferredSize(new Dimension(200, 24));
		table_name.setBounds(300, 60, 200, 24);

		JButton OKButton = new JButton("OK");
		OKButton.setBounds(520, 60, 55, 24);

		controlPanel.add(table_name);
		controlPanel.add(OKButton);

		OKButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JLabel RowsList = new JLabel("Rows to be added (add in the format below)");
				Dimension rows_list_dimensions = RowsList.getPreferredSize();
				RowsList.setBounds(400-rows_list_dimensions.width/2, 100, rows_list_dimensions.width, rows_list_dimensions.height);
				controlPanel.add(RowsList);

				Database db = new Database("srikar","Srikar@1829","EMP");
				String tbl_nm = table_name.getText();
				Map<String,String> columns = db.get_Columns(tbl_nm);

				JLabel RowFormat = new JLabel(String.join(" ; ", columns.keySet()));
				Dimension row_format_dimensions = RowFormat.getPreferredSize();
				RowFormat.setBounds(400-rows_list_dimensions.width/2, 120, row_format_dimensions.width, row_format_dimensions.height);
				controlPanel.add(RowFormat);

				rows_list = new JTextArea();
				scrollPane = new JScrollPane(rows_list);
				scrollPane.setBounds(50, 150, 700, 350);
				controlPanel.add(scrollPane);
				
				AddRows = new JButton("Add Rows to Table");
				AddRows.setBounds(175, 520, 200, 30);

				controlPanel.add(AddRows);
				AddRows.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						rows_list.getText();
					}
				});
			}
		});

		frame.setVisible(true);

    }
}
