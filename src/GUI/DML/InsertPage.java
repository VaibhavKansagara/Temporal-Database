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
	static JPanel controlPanel;
	static JButton AddRows;
	static JScrollPane scrollPane;

	public InsertPage() {
		frame = new JFrame("Insert Operation");
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		JButton OKButton = new JButton("OK");
		OKButton.setBounds(520, 60, 55, 24);

		controlPanel.add(table_name);
		controlPanel.add(OKButton);

		OKButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Database db = new Database("srikar","Srikar@1829","EMP");
				String tbl_nm = table_name.getText();
				Map<String,String> columns = db.get_Columns(tbl_nm);
				
				ArrayList<String> cols=new ArrayList<String>(columns.keySet());
				ArrayList<JLabel> colLabels=new ArrayList<JLabel>();
				cols.forEach((col) -> colLabels.add(new JLabel(col)));
				int maxdim=0;
				for(JLabel colLabel : colLabels)
				{
					maxdim=java.lang.Math.max(maxdim,colLabel.getPreferredSize().width);
				}
				ArrayList<JTextField> colAttr=new ArrayList<JTextField>();
				int depth=150;
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
				
				AddRows = new JButton("Add Rows to Table");
				AddRows.setBounds(175, 520, 200, 30);

				controlPanel.add(AddRows);
				AddRows.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Map<String,Object> row=new HashMap<String,Object>();
						for(int i=0;i<cols.size();i++)
						{
							row.put(colLabels.get(i).getText(),"'"+colAttr.get(i).getText()+"'");
						}
						InsertOperation ins=new InsertOperation(db);
						ins.insert(row,tbl_nm);
					}
				});
			}
		});

		frame.setVisible(true);

	}
}
