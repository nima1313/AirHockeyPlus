package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameHistoryPage extends JFrame {
    static int lastId = 0;
    String columns[] = {"Game Id", "Player 1", "Player 2", "Time", "Goal limit","Player 1 goals","Player 2 goasl", "Two Margin","Game Ended"};
    static Object data[][] = new Object[1000][9];
    public GameHistoryPage() {
        super("Game History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(1200, 600);
        setLayout(null);
        Object lastData[][] = new Object[lastId][9];
        for (int i = 0 ; i < lastId; i++){
            for (int j = 0 ; j < 9; j++) lastData[i][j] = data[i][j];
        }
        JTable table = new JTable(lastData,columns);
        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(20,100,1160,500);
        add(jScrollPane);

        CustomButton backButton = new CustomButton("Back", 600 - 150, 10,300,80);
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

    public static void setNewGame(String playerLeft, String playerRight, int time, int goals,int player1Goals, int player2Goals, boolean twoMargin, boolean gameEnded){
        data[lastId][0] = lastId;
        data[lastId][1] = playerLeft;
        data[lastId][2] = playerRight;
        if (time == 0) data[lastId][3] = "unlimited time";
        else data[lastId][3] = Integer.toString(time) + " minutes";
        if (goals == 0) data[lastId][4] = "unlimited goals";
        else data[lastId][4] = goals;
        data[lastId][5] = player1Goals;
        data[lastId][6] = player2Goals;
        if (twoMargin )data[lastId][7] = "Yes";
        else data[lastId][7] = "No";
        if (gameEnded )data[lastId][8] = "Yes";
        else data[lastId][8] = "No";
        lastId++;
    }
}
