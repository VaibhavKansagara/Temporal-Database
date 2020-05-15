package GUI;

import java.sql.*;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewTable {
	private JFrame frame;
	JTable tb;
	JButton btnBack;

	public ViewTable(ResultSet rs, ArrayList<String> colmnames) {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		tb = new JTable();
		DefaultTableModel dtm = new DefaultTableModel(0,0);
		
		String header[] = new String[colmnames.size()];
		int i = 0;
		for(String entry: colmnames){
			header[i] = entry;
			i++;
		}
		
		dtm.setColumnIdentifiers(header);
		tb.setModel(dtm);
		
		try {
			while(rs.next()) {
				Object[] data = new Object[header.length];
				for(int j=0;j<header.length;j++) {
					data[j] = rs.getString(header[j]);
				}
				dtm.addRow(data);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		frame.getContentPane().add(new JScrollPane(tb));
		
		btnBack = new JButton("Back");
		frame.getContentPane().add(btnBack, BorderLayout.SOUTH);
		
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				//Get selected tables
				// TemporalPage temp = new TemporalPage();
			}
		});
	}
}
