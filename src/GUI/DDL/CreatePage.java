import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CreatePage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;

    public CreatePage() {
        frame = new JFrame("Create Operation");
        frame.setSize(400,400);
        frame.setLayout(new GridLayout(2, 1));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        frame.add(headerLabel);
        frame.add(controlPanel);

        headerLabel.setText("Write the sql for create operation"); 

        final JTextArea commentTextArea = 
                new JTextArea("Write SQL queries for creating " 
                            + "tables",5,20);

        JScrollPane scrollPane = new JScrollPane(commentTextArea);
        JButton ExecuteButton = new JButton("ExecuteQuery");

        controlPanel.add(scrollPane);
        controlPanel.add(ExecuteButton);
        frame.setVisible(true);

        ExecuteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {     
               // execute sql query.        
            }
        });
    }
}
