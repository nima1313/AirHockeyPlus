package Model.Game;

import java.awt.*;

public class Gate extends Rectangle {
    int id;
    public Gate(int x, int y, int width,int height,int id){
        super(x,y,width,height);
        this.id = id;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x,y,width,height);
    }

    public void doubleTheSize(){
        y = y - (height/2);
        height = height*2;
    }

    public void halfTheSize(){
        height = height/2;
        y = y + height/2;
    }
}
