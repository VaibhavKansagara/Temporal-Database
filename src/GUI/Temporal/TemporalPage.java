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
    private JButton History_Natural_Join_btn;
    private JButton At_Natural_Join_btn;
    private JButton Bet_Natural_Join_btn;
    private JButton History_Cross_Join_btn;
    private JButton History_Cross_Join2_btn;
    private JButton At_Cross_Join_btn;
    private JButton Bet_Cross_Join_btn;
    private JButton At_Cross_Join2_btn;
    private JButton Bet_Cross_Join2_btn;
    private JButton Non_Temporal_Cross_Join_btn;
    private JButton History_btn;
    private JButton At_btn;
    private JButton Between_And_btn;
    private JButton When_Cross_Join_btn;
    private JButton When_Cross_Join2_btn;
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
        History_btn= new JButton("History");
        At_btn= new JButton("At");
        Between_And_btn= new JButton("BetweenAnd");
        Non_Temporal_Cross_Join_btn= new JButton("NonTemporalCrossJoin");
        History_Natural_Join_btn= new JButton("HistoryNaturalJoin");
        At_Natural_Join_btn=new JButton("AtNaturalJoin");
        Bet_Natural_Join_btn= new JButton("BetweenNaturalJoin");
        History_Cross_Join_btn = new JButton("HistoryCrossJoin");
        History_Cross_Join2_btn = new JButton("HistoryCrossJoin2");
        At_Cross_Join_btn= new JButton("AtCrossJoin");
        Bet_Cross_Join_btn = new JButton("BetweenCrossJoin");
        At_Cross_Join2_btn= new JButton("AtCrossJoin2");
        Bet_Cross_Join2_btn = new JButton("BetweenCrossJoin2");
        When_Cross_Join_btn = new JButton("WhenCrossJoin");
        When_Cross_Join2_btn = new JButton("WhenCrossJoin2");
       
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

         History_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               History hist= new History();
            }          
        });
        At_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               At non_cross= new At();
            }          
        });
        Between_And_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               Between_And non_cross= new Between_And();
            }          
        });
        Non_Temporal_Cross_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               Non_temporal_cross_join non_cross= new Non_temporal_cross_join();
            }          
        });
        History_Natural_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               History_Natural_Join hst_nat= new History_Natural_Join();
            }          
        });
        At_Natural_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               At_Natural_Join at_nat= new At_Natural_Join();
            }          
        });
        Bet_Natural_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               Bet_Natural_Join bet_nat= new Bet_Natural_Join();
            }          
        });
        History_Cross_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               History_Cross_Join his_cross= new History_Cross_Join();
            }          
        });
        History_Cross_Join2_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               History_Cross_Join2 his_cross2= new History_Cross_Join2();
            }          
        });
        At_Cross_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               At_Cross_Join at_cross= new At_Cross_Join();
            }          
        });
        Bet_Cross_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               Between_And_Cross_Join bet_cross= new Between_And_Cross_Join();
            }          
        });
        At_Cross_Join2_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               At_Cross_Join at_cross2= new At_Cross_Join();
            }          
        });
        Bet_Cross_Join2_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               Between_And_Cross_Join bet_cross2= new Between_And_Cross_Join();
            }          
        });
        When_Cross_Join_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               When_Cross_Join when_cross= new When_Cross_Join();
            }          
        });
        When_Cross_Join2_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // yet to be done.
               When_Cross_Join2 when_cross2= new When_Cross_Join2();
            }          
        });
       
        controlPanel.add(First_btn);
        controlPanel.add(Last_btn);
        controlPanel.add(Previous_btn);
        controlPanel.add(Next_btn);
        controlPanel.add(Evol_btn);
        controlPanel.add(FirstEvol_btn);
        controlPanel.add(LastEvol_btn);
        controlPanel.add(History_btn);
        controlPanel.add(At_btn);
        controlPanel.add(Between_And_btn);
        controlPanel.add(Non_Temporal_Cross_Join_btn);
        controlPanel.add(History_Natural_Join_btn);
        controlPanel.add(At_Natural_Join_btn);
        controlPanel.add(Bet_Natural_Join_btn);
        controlPanel.add(History_Cross_Join_btn);
        controlPanel.add(History_Cross_Join2_btn);
        controlPanel.add(At_Cross_Join_btn);
        controlPanel.add(Bet_Cross_Join_btn);
        controlPanel.add(At_Cross_Join2_btn);
        controlPanel.add(Bet_Cross_Join2_btn);
        controlPanel.add(When_Cross_Join_btn);
        controlPanel.add(When_Cross_Join2_btn);

        frame.setVisible(true);
    }
    public static void main(String args[]){
        TemporalPage tp= new TemporalPage();
    }
}
