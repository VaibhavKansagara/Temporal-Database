import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DDLPage {
    private JFrame frame;
    private JLabel headerLabel;
    private JPanel controlPanel;
    private JButton Createbtn;
    private JButton Dropbtn;
    private JButton Alterbtn;
    private JButton Truncatebtn;
    private JButton Renamebtn;
    private JButton Commentbtn;
    private JButton Backbtn;

    public DDLPage() {
        frame = new JFrame("DDL Operations");
        frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        headerLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        frame.add(headerLabel);
        frame.add(controlPanel);

        headerLabel.setText("Select the DDL operations you want to perform"); 
        Createbtn = new JButton("Create");        
        Dropbtn = new JButton("Drop");
        Alterbtn = new JButton("Alter");
        Truncatebtn = new JButton("Truncate");
        Renamebtn = new JButton("Rename");
        Commentbtn = new JButton("Comment");
        Backbtn = new JButton("Back");

        Createbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               CreatePage c = new CreatePage();
            }          
        });

        Dropbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               DropPage d = new DropPage();
            }          
        });

        Alterbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               AlterPage alt = new AlterPage();
            }          
        });

        Truncatebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               TruncatePage tr = new TruncatePage();
            }          
        });

        Renamebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               RenamePage rnm = new RenamePage();
            }          
        });

        Commentbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CommentPage cmt = new CommentPage();
            }          
        });

        Backbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
            }          
        });

        controlPanel.add(Createbtn);
        controlPanel.add(Dropbtn);
        controlPanel.add(Alterbtn);
        controlPanel.add(Truncatebtn);
        controlPanel.add(Renamebtn);
        controlPanel.add(Commentbtn);
        controlPanel.add(Backbtn);

        frame.setVisible(true);
    }
}
