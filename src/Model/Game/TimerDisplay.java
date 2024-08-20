package Model.Game;

import java.awt.*;

public class TimerDisplay extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int time; //is by seconds
    public TimerDisplay(int gameWidth, int gameHeight,int time){
        this.GAME_WIDTH = gameWidth;
        this.GAME_HEIGHT = gameHeight;
        this.time = time * 60;
    }

    public void draw(Graphics g, double passedTime){
        if (time!=0) {
            int displayTime = time - (int)Math.floor(passedTime);
            //System.out.println(passedTime);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            int minutes = displayTime/60;
            int second = displayTime%60;
            g.drawString(String.valueOf(minutes/10)+String.valueOf(minutes%10),GAME_WIDTH/2 - 120,  GAME_HEIGHT- 50);
            g.drawString(":",GAME_WIDTH/2 - 10, GAME_HEIGHT - 50);
            g.drawString(String.valueOf(second/10)+String.valueOf(second%10),GAME_WIDTH/2 + 55, GAME_HEIGHT- 50);
        }
    }
    public boolean checkIfEnded(double passedTime){
        if (time == 0) return false;

        if (passedTime >= time) return true;
        else return false;
    }
}
