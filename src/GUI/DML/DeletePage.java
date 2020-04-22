package GUI.DML;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DeletePage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton Backbtn;

    public DeletePage() {
	frame = new JFrame("Delete Operation");
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