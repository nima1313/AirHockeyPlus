package Model.Game;


import Model.CustomButton;
import Model.GameHistoryPage;
import Model.MenuPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.security.Key;

import static Model.Game.TimerDisplay.GAME_HEIGHT;
import static Model.Game.TimerDisplay.GAME_WIDTH;

public class GamePage extends JFrame {
    Score score;

    CustomButton pauseButton;
    CustomButton backButton;
    CustomButton resumeButton;
    boolean finished = false;
    boolean paused = false;
    Field field;
    String playerLeft;
    String playerRight;
    public GamePage(String playerLeft, String playerRight,int goals, int time, boolean twoMargin){
        field = new Field(goals,time,twoMargin,this);
        score = new Score(GAME_WIDTH,GAME_HEIGHT,goals,twoMargin);
        add(field);
        this.playerLeft = playerLeft;
        this.playerRight = playerRight;
        setTitle("AirHockey!");
        setResizable(false);
        setBackground(Color.black);
        setLayout(null);

        setSize(1010,800);
        field.setBounds(0,50,1000,600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //BUTTONS//
        pauseButton = new CustomButton("Pause",15 + 1 + 10, 675,200,70);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paused = true;
                pauseButton.setVisible(false);
                pauseButton.setEnabled(false);
                resumeButton.setEnabled(true);
                resumeButton.setVisible(true);
                backButton.setEnabled(true);
                backButton.setVisible(true);
            }
        });
        this.add(pauseButton);

        resumeButton = new CustomButton("Resume",15 + 1 + 10, 675,200,70);
        resumeButton.setEnabled(false);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paused = false;
                pauseButton.setVisible(true);
                pauseButton.setEnabled(true);
                resumeButton.setEnabled(false);
                resumeButton.setVisible(false);
                backButton.setEnabled(false);
                backButton.setVisible(false);


            }
        });
        this.add(resumeButton);

        backButton = new CustomButton("Return to menu",  GAME_WIDTH - (1 + 10 + 15 + 200), 675,200,70);
        backButton.setVisible(false);
        backButton.setVisible(false);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!finished) GameHistoryPage.setNewGame(playerLeft, playerRight,time,goals,score.playerLeft,score.playerRight,twoMargin,false);
                new MenuPage();
                dispose();
            }
        });
        this.add(backButton);

        //Names//

        JLabel player1Name = new JLabel();
        player1Name.setText(playerLeft);
        player1Name.setBounds(160,12,200,30);
        player1Name.setFont(new Font("Arial", Font.PLAIN, 25));
        add(player1Name);

        JLabel player2Name = new JLabel();
        player2Name.setText(playerRight);
        player2Name.setBounds(1010 - (300+60),12,200,30);
        player2Name.setFont(new Font("Arial", Font.PLAIN, 25));
        add(player2Name);

        setVisible(true);

//        field = new Field(goals,time,twoMargin,this);
//        add(field);
//        this.playerLeft = playerLeft;
//        this.playerRight = playerRight;
//        setTitle("AirHockey!");
//        setResizable(false);
//        setBackground(Color.black);
//        //setLayout(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();
//        setVisible(true);

    }

}
