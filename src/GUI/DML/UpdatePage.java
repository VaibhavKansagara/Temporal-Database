package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Backend.Database;
import Backend.DML.UpdateOperation;


import java.util.*;


class UpdatePage {
	static JFrame frame;
	static JLabel TableName, ColumnNames;
	static JTextField table_name, column_names;
	static JPanel controlPanel;
	static JButton OKButton, AddRows;
	static JScrollPane scrollPane;

	public UpdatePage() {
		frame = new JFrame("Update Operation");
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

		ColumnNames = new JLabel("Enter Columns to Update (comma-separated)");
		
		Dimension column_names_dimensions = ColumnNames.getPreferredSize();
		ColumnNames.setBounds(400-column_names_dimensions.width/2, 90, column_names_dimensions.width, column_names_dimensions.height);

		controlPanel.add(ColumnNames);

		column_names = new JTextField();
		
		column_names.setPreferredSize(new Dimension(200, 24));
		column_names.setBounds(150, 110, 500, 24);

		JButton OKButton = new JButton("OK");
		OKButton.setBounds(700, 110, 55, 24);

		controlPanel.add(table_name);
		controlPanel.add(column_names);
		controlPanel.add(OKButton);

		OKButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Database db = new Database("srikar","Srikar@1829","EMP");
				String tbl_nm = table_name.getText();
				
				JLabel pkLabel = new JLabel(db.get_primary_key(tbl_nm).entrySet().iterator().next().getKey());
				
				ArrayList<String> cols = new ArrayList<String>(Arrays.asList(column_names.getText().split(", ")));
				ArrayList<JLabel> colLabels = new ArrayList<JLabel>();
				cols.forEach((col) -> colLabels.add(new JLabel(col)));
				int maxdim=pkLabel.getPreferredSize().width;
				for(JLabel colLabel : colLabels)
				{
					maxdim=java.lang.Math.max(maxdim,colLabel.getPreferredSize().width);
				}
				
				Dimension pk_format_dimensions = pkLabel.getPreferredSize();
				pkLabel.setBounds(50, 180, pk_format_dimensions.width, pk_format_dimensions.height);
				controlPanel.add(pkLabel);
				JTextField pkVal = new JTextField();
				pkVal.setPreferredSize(new Dimension(500, 24));
				pkVal.setBounds(100+maxdim, 192-pk_format_dimensions.height, 500, 24);
				controlPanel.add(pkVal);
				
				ArrayList<JTextField> colAttr=new ArrayList<JTextField>();
				int depth=pk_format_dimensions.height+200;
				for(int i=0;i<colLabels.size();i++)
				{
					Dimension col_format_dimensions = colLabels.get(i).getPreferredSize();
					colLabels.get(i).setBounds(50, depth, col_format_dimensions.width, col_format_dimensions.height);
					controlPanel.add(colLabels.get(i));
					JTextField attr = new JTextField();
					attr.setPreferredSize(new Dimension(500, 24));
					attr.setBounds(100+maxdim, depth+12-col_format_dimensions.height, 500, 24);
					colAttr.add(attr);
					controlPanel.add(attr);
					depth+=col_format_dimensions.height+20;
				}
				
				AddRows = new JButton("Update Row of Table");
				AddRows.setBounds(175, 520, 200, 30);

				controlPanel.add(AddRows);
				AddRows.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Map<String,Object> key=new HashMap<String,Object>(), row=new HashMap<String,Object>();
						key.put(pkLabel.getText(),"'"+pkVal.getText()+"'");
						for(int i=0;i<cols.size();i++)
						{
							row.put(colLabels.get(i).getText(),"'"+colAttr.get(i).getText()+"'");
						}
						UpdateOperation upd=new UpdateOperation(db);
						upd.update(key,row,tbl_nm);
					}
				});
			}
		});

		frame.setVisible(true);

	}
}
