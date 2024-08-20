package Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPage extends JFrame {

    public MenuPage() {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(820, 450);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("MenuBackground2.png");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        CustomButton newGameButton = new CustomButton("New Game",300,25,200,100);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGamePage newGamePage = new newGamePage();
                dispose();
            }
        });
        panel.add(newGameButton);

        CustomButton gameHistoryButton = new CustomButton("Game History", 20 , 275, 200, 100);
        gameHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GameHistoryPage();
                dispose();
            }
        });
        panel.add(gameHistoryButton);

        CustomButton exitButton = new CustomButton("Exit", 580 , 275 , 200, 100);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);

        setContentPane(panel);
        //setLayout(null);
        setVisible(true);
    }
}