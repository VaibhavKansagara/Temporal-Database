package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MenuPage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton Insertbtn;
    private JButton Deletebtn;
    private JButton Selectbtn;
    private JButton Updatebtn;
    private JButton Backbtn;

    public MenuPage() {
        frame = new JFrame("DML Operations");
        frame.setSize(400,400);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        frame.add(headerLabel);
        frame.add(controlPanel);

        headerLabel.setText("Select the DDL operations you want to perform"); 
        Insertbtn = new JButton("Insert");        
        Deletebtn = new JButton("Delete");
        Selectbtn = new JButton("Select");
        Updatebtn = new JButton("Update");
        Backbtn = new JButton("Back");

        Insertbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               InsertPage c = new InsertPage();
            }          
        });

        Deletebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               DeletePage d = new DeletePage();
            }          
        });

        Selectbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               SelectPage alt = new SelectPage();
            }          
        });

        Updatebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               UpdatePage tr = new UpdatePage();
            }          
        });

        Backbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
            }          
        });

        controlPanel.add(Insertbtn);
        controlPanel.add(Deletebtn);
        controlPanel.add(Selectbtn);
        controlPanel.add(Updatebtn);
        controlPanel.add(Backbtn);

        frame.setVisible(true);
    }
    
}
