package Model.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Puck extends Rectangle{
    int id;
    boolean active;
    double velocityX;
    double velocityY;
    boolean goingUp = false;
    boolean goingDown = false;
    boolean goingRight = false;
    boolean goingLeft = false;
    int speed = 7;

    Field field;
    public Puck(int x, int y, int PUCK_WIDTH, int PUCK_HEIGHT, int id, Field field){
        super(x,y,PUCK_WIDTH,PUCK_HEIGHT);
        this.id = id;
        this.active = true;
        this.field = field;
    }

    public void keyPressed(KeyEvent e){
        if (id == 1){
            if (e.getKeyCode() == KeyEvent.VK_W){
                goingUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S){
                goingDown = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D){
                goingRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_A){
                goingLeft = true;
            }
        }
        else if(id == 2){
            if (e.getKeyCode() == KeyEvent.VK_UP){
                goingUp = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                goingDown = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                goingRight = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                goingLeft = true;
            }
        }
    }
    public void keyReleased(KeyEvent e){
        if (id == 1){
            if (e.getKeyCode() == KeyEvent.VK_W){
                goingUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S){
                goingDown = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D){
                goingRight = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_A){
                goingLeft = false;
            }
        }
        else if(id == 2){
            if (e.getKeyCode() == KeyEvent.VK_UP){
                goingUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN){
                goingDown = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                goingRight = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT){
                goingLeft = false;
            }
        }

    }
    public void setDirectionY(int DirectionY){
        velocityY = DirectionY;
    }
    public void setDirectionX(int DirectionX){
        velocityX = DirectionX;
    }
    public void move(){
        y += velocityY;
        x += velocityX;
    }
    public void draw(Graphics g){
        if (id == 1){
            g.setColor(Color.blue);
        }
        else
            g.setColor(Color.red);
        g.fillOval((int)Math.floor(x),(int)Math.floor(y),width,height);
    }

    public boolean isGoingUp() {
        return goingUp;
    }

    public boolean isGoingDown() {
        return goingDown;
    }

    public boolean isGoingRight() {
        return goingRight;
    }

    public boolean isGoingLeft() {
        return goingLeft;
    }
}
