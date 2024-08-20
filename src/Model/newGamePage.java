package Model;

import Model.Game.GamePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class newGamePage extends JFrame {
    CustomButton newGameButton;
    JTextField player1TextField;
    JTextField player2TextField;
    JComboBox<String> timeComoBox;
    JComboBox<String> goalComoBox;
    CustomCheckBox timeCheckBox;
    CustomCheckBox goalCheckBox;
    CustomCheckBox twoMarginCheckBox;
    newGamePage() {
        super("New Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setSize(500, 500);
        setLayout(null);

        CustomButton newGameButton = new CustomButton("New game", 150, 350, 200, 100);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int time=0,goals=0;
                boolean isTwoMargin=false;
                if (timeCheckBox.isSelected()){
                    int mode = timeComoBox.getSelectedIndex();
                    if (mode == 0){
                        time = 1;
                    } else if (mode == 1) {
                        time = 2;
                    } else if (mode == 2) {
                        time = 3;
                    } else if (mode == 3) {
                        time = 5;
                    } else if (mode == 4) {
                        time = 10;
                    }
                }
                if (goalCheckBox.isSelected()){
                    if (twoMarginCheckBox.isSelected()) isTwoMargin = true;
                    String mode = (String) goalComoBox.getSelectedItem();
                    goals = Integer.parseInt(mode);
                }
                String playerLeft = "Player 1";
                String playerRight = "Player 2";
                if (!player1TextField.getText().equals("")) playerLeft = player1TextField.getText();
                if (!player2TextField.getText().equals("")) playerRight = player2TextField.getText();
                //System.out.println(playerLeft);
                new GamePage(playerLeft,playerRight,goals,time,isTwoMargin);
                dispose();
            }
        });
        newGameButton.setEnabled(false);
        add(newGameButton);

        String[] timeOptions = {"1 minute", "2 minutes" , "3 minutes", "5 minutes" , "10 minutes"};
        timeComoBox = new JComboBox<>(timeOptions);
        timeComoBox.setBounds(200, 70, 100, 20);
        timeComoBox.setVisible(false);
        timeComoBox.setFocusable(false);
        add(timeComoBox);


        String[] goalOptions = {"1","2","3","4","5","6","7","8","9","10"};
        goalComoBox = new JComboBox<>(goalOptions);
        goalComoBox.setBounds(200,180, 100, 20);
        goalComoBox.setVisible(false);
        goalComoBox.setFocusable(false);
        add(goalComoBox);


        timeCheckBox = new CustomCheckBox("Time limited", 30, 30, 100, 20);
        timeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeCheckBox.isSelected()) {
                    timeComoBox.setVisible(true);
                    newGameButton.setEnabled(true);
                } else {
                    if (!goalCheckBox.isSelected()) newGameButton.setEnabled(false);
                    timeComoBox.setVisible(false);
                }
            }
        });
        add(timeCheckBox);

        goalCheckBox = new CustomCheckBox("Goal limited", 30, 140, 100, 20);
        goalCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (goalCheckBox.isSelected()) {
                    twoMarginCheckBox.setSelected(false);
                    twoMarginCheckBox.setVisible(true);
                    goalComoBox.setVisible(true);
                    newGameButton.setEnabled(true);
                } else {
                    if (!timeCheckBox.isSelected()) newGameButton.setEnabled(false);
                    twoMarginCheckBox.setVisible(false);
                    goalComoBox.setVisible(false);
                }
            }
        });
        add(goalCheckBox);

        twoMarginCheckBox = new CustomCheckBox("2 margin", 30, 250, 100, 20);
        twoMarginCheckBox.setVisible(false);
        add(twoMarginCheckBox);


        //TextFields
        player1TextField = new JTextField(20);
        player1TextField.setText("Player 1");
        player1TextField.setBounds(70, 310,100,20);
        add(player1TextField);

        player2TextField = new JTextField(20);
        player2TextField.setText("Player 2");
        player2TextField.setBounds(330, 310,100,20);
        add(player2TextField);


        JLabel label1 = new JLabel("enter the names (empty ==> default names)");
        label1.setBounds(120,290,300,20);
        add(label1);


        //BACK BUTTON
        CustomButton backButton = new CustomButton("Back", 350, 10,100,50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuPage();
                dispose();
            }
        });
        add(backButton);

        setVisible(true);
    }
}
