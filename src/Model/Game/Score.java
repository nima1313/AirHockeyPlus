package Model.Game;

import java.awt.*;

public class Score extends Rectangle{
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int playerLeft;
    int playerRight;
    int goals;
    boolean twoMargin;
    public Score(int gameWidth, int gameHeight,int goals, boolean twoMargin){
        Score.GAME_WIDTH = gameWidth;
        Score.GAME_HEIGHT = gameHeight;
        this.goals = goals;
        this.twoMargin = twoMargin;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 60));
        g.drawString(String.valueOf(playerLeft/10)+String.valueOf(playerLeft%10),GAME_WIDTH/2 - 120, 60);
        g.drawString(String.valueOf(playerRight/10)+String.valueOf(playerRight%10),GAME_WIDTH/2 + 55, 60);
    }
    public boolean checkIfEnded(){
        if (goals == 0) return false;
        if (twoMargin){
            if ((playerRight >= goals && playerRight - playerLeft >= 2) || (playerLeft >= goals && playerLeft - playerRight >= 2)){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (playerLeft==goals || playerRight == goals){
                return true;
            }
            else {
                return false;
            }
        }
    }
}
