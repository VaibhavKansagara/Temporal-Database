package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import Backend.Database;
import Backend.DML.UpdateOperation;


import java.util.*;


class UpdatePage {
	static JFrame frame;
	static JLabel TableName, ColumnCount;
	static JTextField table_name, column_count;
	static JPanel controlPanel;
	static JButton OKButton, UpdateRows;
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

		ColumnCount = new JLabel("Enter Number of Columns to Update");
		
		Dimension column_count_dimensions = ColumnCount.getPreferredSize();
		ColumnCount.setBounds(400-column_count_dimensions.width/2, 90, column_count_dimensions.width, column_count_dimensions.height);

		controlPanel.add(ColumnCount);

		column_count = new JTextField();
		
		column_count.setPreferredSize(new Dimension(100, 24));
		column_count.setBounds(350, 110, 100, 24);

		OKButton = new JButton("OK");
		OKButton.setBounds(500, 110, 55, 24);

		controlPanel.add(table_name);
		controlPanel.add(column_count);
		controlPanel.add(OKButton);

		OKButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Database db = new Database("srikar","Srikar@1829","EMP");
				String tbl_nm = table_name.getText();
				
				JLabel pkLabel = new JLabel(db.get_primary_key(tbl_nm).entrySet().iterator().next().getKey());
				
				String[] col_nms = db.get_Columns(tbl_nm).keySet().toArray(new String[0]);
				// ArrayList<String> cols = new ArrayList<String>(Arrays.asList(col_nms));
				ArrayList<JComboBox> combBoxes = new ArrayList<JComboBox>();
				int clmn_cnt = Integer.parseInt(column_count.getText());
				for(int i=0;i<clmn_cnt;i++)
				{
					JComboBox cb = new JComboBox(col_nms);
					// cb.setPreferredSize(new Dimension(150,24));
					combBoxes.add(cb);
				}
				int maxdim=pkLabel.getPreferredSize().width;
				for(JComboBox combBox : combBoxes)
				{
					maxdim=java.lang.Math.max(maxdim,combBox.getPreferredSize().width);
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
				for(int i=0;i<combBoxes.size();i++)
				{
					Dimension col_format_dimensions = combBoxes.get(i).getPreferredSize();
					combBoxes.get(i).setBounds(50, depth, col_format_dimensions.width, col_format_dimensions.height);
					controlPanel.add(combBoxes.get(i));
					JTextField attr = new JTextField();
					attr.setPreferredSize(new Dimension(500, 24));
					attr.setBounds(100+maxdim, depth, 500, 24);
					colAttr.add(attr);
					controlPanel.add(attr);
					depth+=col_format_dimensions.height+20;
				}
				
				UpdateRows = new JButton("Update Row of Table");
				UpdateRows.setBounds(175, 520, 200, 30);

				controlPanel.add(UpdateRows);
				UpdateRows.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Map<String,Object> key=new HashMap<String,Object>(), row=new HashMap<String,Object>();
						key.put(pkLabel.getText(),"'"+pkVal.getText()+"'");
						for(int i=0;i<clmn_cnt;i++)
						{
							row.put(String.valueOf(combBoxes.get(i).getSelectedItem()),"'"+colAttr.get(i).getText()+"'");
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
