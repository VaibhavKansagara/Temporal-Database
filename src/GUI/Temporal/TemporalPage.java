package GUI.Temporal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TemporalPage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton First_btn;
    private JButton Last_btn;
    private JButton Previous_btn;
    private JButton Next_btn;
    private JButton Evol_btn;
    private JButton FirstEvol_btn;
    private JButton LastEvol_btn;
    private JButton Back_btn;

    public TemporalPage() {
        frame = new JFrame("Temporal Operations");
        frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        frame.add(headerLabel);
        frame.add(controlPanel);

        headerLabel.setText("Select the Temporal operations you want to perform"); 
        First_btn = new JButton("First");        
        Last_btn = new JButton("Last");
        Previous_btn = new JButton("Previous");
        Next_btn = new JButton("Next");
        Evol_btn = new JButton("Evol");
        FirstEvol_btn = new JButton("FirstEvolution");
        LastEvol_btn = new JButton("LastEvolution");
        Back_btn = new JButton("Back");

        First_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               First first = new First();
            }          
        });

        Last_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Last last = new Last();
            }          
        });

        Previous_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Previous previous = new Previous();
            }          
        });

        Next_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Next nxt = new Next();
            }          
        });

        Evol_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Evol evl = new Evol();
            }          
        });

        FirstEvol_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FirstEvol fstevl = new FirstEvol();
            }          
        });

        LastEvol_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LastEvol lstevl = new LastEvol();
            }          
        });

        Back_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
            }          
        });

        controlPanel.add(First_btn);
        controlPanel.add(Last_btn);
        controlPanel.add(Previous_btn);
        controlPanel.add(Next_btn);
	controlPanel.add(Evol_btn);
	controlPanel.add(FirstEvol_btn);
	controlPanel.add(LastEvol_btn);
        controlPanel.add(Backbtn);

        frame.setVisible(true);
    }
    
}
