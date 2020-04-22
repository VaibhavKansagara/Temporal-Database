package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class SelectPage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton Backbtn;

    public SelectPage() {
	frame = new JFrame("Select Operation");
	frame.setSize(400,400);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	headerLabel = new JLabel("", JLabel.CENTER);

	controlPanel = new JPanel();
	controlPanel.setLayout(new FlowLayout());

	frame.add(headerLabel);
	frame.add(controlPanel);


	controlPanel.add(Backbtn);

	frame.setVisible(true);
    }
}
