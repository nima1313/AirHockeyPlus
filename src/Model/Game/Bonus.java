package Model.Game;

import java.awt.*;

public class Bonus extends Rectangle {
    int diameter;
    int mode; //modes ::  1: fireBall (red) 2:Bigger gate (green)  3: mirrorWall (blue)
    Field field;
    int storedDiameter;
    public Bonus(int x, int y, int diameter, int mode, Field field){
        super(x,y,diameter,diameter);
        storedDiameter = diameter;
        this.mode = mode;
        this.field = field;
    }

    public void disAppear(){
        diameter = 0;
    }
    public void appear(){
        diameter = storedDiameter;
    }

    public void doubleSize(){
        diameter = diameter*2;
    }

    public void activate(int puckId){
        if (mode == 1){
            field.ball.bonusSpeed();
            if (puckId == 1){
                field.puckRight.active = false;
            }
            else {
                field.puckLeft.active = false;
            }
        }
        else if (mode == 2) {
            if (puckId == 1){
                field.gateRight.doubleTheSize();
            }
            else {
                field.gateLeft.doubleTheSize();
            }
        }
        else {
            field.mirrorWall = true;
        }
    }

    public void draw(Graphics g){
        if (mode == 1) g.setColor(Color.red);
        else if (mode == 2) g.setColor(Color.green);
        else g.setColor(Color.blue);
        g.fillOval(x,y,diameter,diameter);
    }

    public double getCenterX() {
        return x + diameter / 2.0;
    }
    public double getCenterY() {
        return y + diameter / 2.0;
    }
}
