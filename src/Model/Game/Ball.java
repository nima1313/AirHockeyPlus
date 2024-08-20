package Model.Game;

import java.awt.*;
import java.util.Random;

public class Ball extends Rectangle{
    Random random;
    int velocity = 12;
    double xVelocity=0;
    double yVelocity=0;
    double x;
    double y;
    boolean isStill;
    double DirectionX;
    double DirectionY;
    public Ball(int x, int y, int width, int height, boolean isStill){
        super(x,y,width,height);
        this.x = x;
        this.y = y;
        this.isStill = isStill;
        if (!isStill) setRandomSpeed();
    }

    public void setRandomSpeed(){
        random = new Random();
        xVelocity = 3 + random.nextDouble(velocity-3);
        yVelocity = Math.sqrt(velocity*velocity - xVelocity*xVelocity);
    }
    public void setDirectionY(double DirectionY){
        yVelocity = DirectionY;
    }
    public void setDirectionX(double DirectionX){
        xVelocity = DirectionX;
    }

    public void move(){
        x+= xVelocity;
        y+= yVelocity;
    }

    public void bonusSpeed(){
        yVelocity = yVelocity * 2;
        xVelocity = xVelocity * 2;
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval((int)Math.floor(x),(int)Math.floor(y),width,height);
    }

    public double getCenterX() {
        return x + getWidth() / 2.0;
    }
    public double getCenterY() {
        return y + getWidth() / 2.0;
    }
}
