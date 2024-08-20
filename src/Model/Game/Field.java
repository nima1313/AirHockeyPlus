package Model.Game;
import Model.CustomButton;
import Model.GameHistoryPage;
import Model.MenuPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Field extends JPanel implements Runnable {
    Random random;
    int lastCollidePuck = 0;

    double totalPassedTime;

    boolean mirrorWall = false;
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 600;
    double lastCollide1;
    double lastCollide2;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 30;
    static final int BONUS_FIRST_DIAMETER = 10;
    static final int PUCK_WIDTH = 100;
    static final int PUCK_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Puck puckLeft;
    Puck puckRight;
    Ball ball;

    double lastBonusTime;
    int bonusPhase = 0; // phase 0 : 10 second not appearing, phase1 : apear phase 2 : 2x phase 3 : 2x

    TimerDisplay timerDisplay;
    Gate gateLeft;
    Gate gateRight;


    int rightScore;
    int leftScore;


    GamePage gamePage;
    int goals;
    int time;
    boolean twoMargin;
    Bonus bonus;
    public Field(int goals, int time, boolean twoMargin, GamePage gamePage){
        this.gamePage = gamePage;
        this.goals = goals;
        this.time = time;
        this.twoMargin = twoMargin;
        random = new Random();
        //buttons
        setLayout(null);



        //
        newBall(1);
        newPucks();
        newGate();
        newBonus();
        gamePage.paused = false;
        timerDisplay = new TimerDisplay(GAME_WIDTH,GAME_HEIGHT,time);
        setFocusable(true);
        addKeyListener(new ActionListener());
        setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();


    }

    public void draw(Graphics g){
        puckLeft.draw(g);
        puckRight.draw(g);
        gateLeft.draw(g);
        gateRight.draw(g);
        ball.draw(g);
        gamePage.score.draw(g);
        timerDisplay.draw(g,totalPassedTime);
        bonus.draw(g);

    }

    public void newBonus(){
        bonus = new Bonus(300 - 40 + random.nextInt(300 + 40), 50 + random.nextInt(500),15,1 + random.nextInt(3),this );
        lastBonusTime = 0;
        bonusPhase = 0;
        bonus.disAppear();
    }

    public void newBall(int mode){
        // modes ::  1:start of the game 2:left 3:right
        if (mode == 1){
            ball = new Ball(GAME_WIDTH/2 - BALL_DIAMETER/2 , GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER,BALL_DIAMETER,false);
        } else if (mode == 2) {
            ball = new Ball(GAME_WIDTH/2 - (BALL_DIAMETER/2 + 80) , GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER,BALL_DIAMETER,true);

        }
        else {
            ball = new Ball(GAME_WIDTH/2 - (BALL_DIAMETER/2 - 80) , GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER,BALL_DIAMETER,true);

        }
    }

    public void newGate(){
        gateLeft = new Gate(0, GAME_HEIGHT/2 - 80,15,160,1);
        gateRight = new Gate(GAME_WIDTH-15, GAME_HEIGHT/2 - 80,15,160,2);
    }

    public void newPucks(){
        puckLeft = new Puck(50,(GAME_HEIGHT/2) - (PUCK_HEIGHT/2), PUCK_WIDTH, PUCK_HEIGHT,1,this);
        puckRight = new Puck(GAME_WIDTH-(50 + PUCK_WIDTH) ,(GAME_HEIGHT/2) - (PUCK_HEIGHT/2), PUCK_WIDTH, PUCK_HEIGHT,2,this);
    }

    public void paint(Graphics g){

        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();

        draw(graphics);
        g.drawImage(image,0,0,this);

        Graphics2D g2D = (Graphics2D) g;

        g2D.setColor(Color.white);
        g2D.setStroke(new BasicStroke(10));
        g2D.drawLine(500 - 40,0,500 - 40,600);
        g2D.drawLine(500 + 40,0,500 + 40,600);

        g2D.setColor(Color.gray);
        g2D.setStroke(new BasicStroke(2));
        g2D.drawLine(15+1,0,15+1,600);
        g2D.drawLine(GAME_WIDTH - (1 + 15),0,GAME_WIDTH - (1 + 15),600);



    }

    public void move(){
        if(lastCollide1 >= 10) puckLeft.move();
        if(lastCollide2 >= 10) puckRight.move();
        ball.move();
    }

    public boolean CollidePuckBall(Puck puck){
        if (!puck.active) return false;

        double x_diff = (ball.getCenterX() - puck.getCenterX())*(ball.getCenterX() - puck.getCenterX());
        double y_diff = (ball.getCenterY() - puck.getCenterY())*(ball.getCenterY() - puck.getCenterY());
        double least_diff = (BALL_DIAMETER+PUCK_WIDTH)/2;
        if (x_diff+y_diff <= least_diff*least_diff ) return true;
        else return false;
    }

    public boolean CollideBonusBall(Bonus bonus){
        if (bonus.diameter == 0) return false;
        double x_diff = (ball.getCenterX() - bonus.getCenterX())*(ball.getCenterX() - bonus.getCenterX());
        double y_diff = (ball.getCenterY() - bonus.getCenterY())*(ball.getCenterY() - bonus.getCenterY());
        double least_diff = (bonus.diameter+BALL_DIAMETER)/2;
        if (x_diff+y_diff <= least_diff*least_diff ) return true;
        else return false;
    }

    public void checkCollision(){
        //Sweet mother of God. here we go...


        //ball and bonus//
        if (CollideBonusBall(bonus) && lastCollidePuck != 0){
            bonus.activate(lastCollidePuck);
            newBonus();
        }

        //ball and pucks//
        if (CollidePuckBall(puckLeft) && lastCollide1 >= 10){
            lastCollidePuck = 1;
//            double angel = Math.atan2(ball.getCenterY() - puckLeft.getCenterY(), ball.getCenterX() - puckLeft.getCenterX());
//            double newSpeedX = -Math.cos(angel) * ball.xVelocity + Math.sin(angel)*ball.yVelocity;
//            double newSpeedY = Math.sin(angel) * ball.xVelocity + Math.cos(angel)*ball.yVelocity;
//            ball.xVelocity = newSpeedX;
//            ball.yVelocity = newSpeedY;
            double angel = Math.atan2(ball.getCenterY() - puckLeft.getCenterY(), ball.getCenterX() - puckLeft.getCenterX());
            double newSpeedXBall = Math.cos(angel) * ball.xVelocity + Math.sin(angel)*ball.yVelocity;
            double newSpeedYBall = -Math.sin(angel) * ball.xVelocity + Math.cos(angel)*ball.yVelocity;
            double newSpeedXPuck = Math.cos(angel) * puckLeft.velocityX + Math.sin(angel)*puckLeft.velocityY;
            double newSpeedYPuck = -Math.sin(angel) * puckLeft.velocityX + Math.cos(angel)*puckLeft.velocityY;

            double finalNewSpeedXBall = ((2.0 - 4.0) * newSpeedXBall + 2 * 4.0 *newSpeedXPuck) / (2.0+4.0);
            double finalNewSpeedXPuck = ((4.0 - 2.0) * newSpeedXPuck + 2 * 2.0 *newSpeedXBall) / (2.0+4.0);

            ball.xVelocity = Math.cos(angel) * finalNewSpeedXBall - Math.sin(angel) * newSpeedYBall;
            ball.yVelocity = Math.sin(angel) * finalNewSpeedXBall + Math.cos(angel) * newSpeedYBall;
            puckLeft.velocityX = Math.cos(angel) * finalNewSpeedXPuck - Math.sin(angel) * newSpeedYPuck;
            puckLeft.velocityY = Math.sin(angel) * finalNewSpeedXPuck + Math.cos(angel) * newSpeedYPuck;
            move();
            lastCollide1 = 0;

        }
        if (CollidePuckBall(puckRight) && lastCollide2 >= 10){
            lastCollidePuck = 2;
//            double angel = Math.atan2(ball.getCenterY() - puckRight.getCenterY(), ball.getCenterX() - puckRight.getCenterX());
//            double newSpeedX = Math.cos(angel) * ball.xVelocity + Math.sin(angel)*ball.yVelocity;
//            double newSpeedY = -Math.sin(angel) * ball.xVelocity + Math.cos(angel)*ball.yVelocity;
//            ball.xVelocity = newSpeedX;
//            ball.yVelocity = newSpeedY;
//            puckRight.setDirectionY(-(int)newSpeedY);
//            puckRight.setDirectionX(-(int)newSpeedX);
//            move();
            double angel = Math.atan2(ball.getCenterY() - puckRight.getCenterY(), ball.getCenterX() - puckRight.getCenterX());
            double newSpeedXBall = Math.cos(angel) * ball.xVelocity + Math.sin(angel)*ball.yVelocity;
            double newSpeedYBall = -Math.sin(angel) * ball.xVelocity + Math.cos(angel)*ball.yVelocity;
            double newSpeedXPuck = Math.cos(angel) * puckRight.velocityX + Math.sin(angel)*puckRight.velocityY;
            double newSpeedYPuck = -Math.sin(angel) * puckRight.velocityX + Math.cos(angel)*puckRight.velocityY;

            double finalNewSpeedXBall = ((2.0 - 4.0) * newSpeedXBall + 2 * 4.0 *newSpeedXPuck) / (2.0+4.0);
            double finalNewSpeedXPuck = ((4.0 - 2.0) * newSpeedXPuck + 2 * 2.0 *newSpeedXBall) / (2.0+4.0);

            ball.xVelocity = Math.cos(angel) * finalNewSpeedXBall - Math.sin(angel) * newSpeedYBall;
            ball.yVelocity = Math.sin(angel) * finalNewSpeedXBall + Math.cos(angel) * newSpeedYBall;
            puckRight.velocityX = Math.cos(angel) * finalNewSpeedXPuck - Math.sin(angel) * newSpeedYPuck;
            puckRight.velocityY = Math.sin(angel) * finalNewSpeedXPuck + Math.cos(angel) * newSpeedYPuck;

            move();
            lastCollide2 = 0;
        }

        // pucks and walls//

        if (puckLeft.y <= 0) {
            puckLeft.y = 0;
        }
        if (puckRight.y <= 0) {
            puckRight.y = 0;
        }
        if (puckRight.y >= GAME_HEIGHT - PUCK_HEIGHT){
            puckRight.y =  GAME_HEIGHT - PUCK_HEIGHT;
        }
        if (puckLeft.y >= GAME_HEIGHT - PUCK_HEIGHT){
            puckLeft.y =  GAME_HEIGHT - PUCK_HEIGHT;
        }

        if (puckLeft.x <= 15+1){
            puckLeft.x = 15+1;
        }

        if (puckRight.x >= GAME_WIDTH - (PUCK_HEIGHT + 15+1)){
            puckRight.x = GAME_WIDTH - (PUCK_HEIGHT + 15+1);
        }

        if (puckLeft.x >= 500 - 146) puckLeft.x = 500 - 146;
        if (puckRight.x <= 500 + 45) puckRight.x = 500 + 45;

        //ball and top and bottom edges//
        if (ball.y <= 0){
            if (mirrorWall){
                ball.y = GAME_HEIGHT - BALL_DIAMETER;
            }
            else {
                ball.y = 0;
                ball.setDirectionY(-ball.yVelocity);
            }

        }
        else if (ball.y >= GAME_HEIGHT - BALL_DIAMETER){
            if (mirrorWall){
                ball.y = 0;
            }
            else {
                ball.y = GAME_HEIGHT - BALL_DIAMETER;
                ball.setDirectionY(-ball.yVelocity);
            }
        }

        if (ball.x <= 15+1){
            ball.x = 15+1;
            ball.setDirectionX( -ball.xVelocity);
        }

        if (ball.x >= GAME_WIDTH - (BALL_DIAMETER + 15+1)){
            ball.x = GAME_WIDTH - (BALL_DIAMETER + 15+1);
            ball.setDirectionX( -ball.xVelocity);
        }


        //A GOAAAAAAAAL
        if (ball.x <= 15 + 1 && ball.y <= gateLeft.y + gateLeft.height && ball.y >= gateLeft.y){
            gamePage.score.playerRight++;
            newGate();
            newPucks();
            mirrorWall = false;
            newBall(2);
            newBonus();
        }

        if (ball.x >= GAME_WIDTH - (BALL_DIAMETER + 15+1) && ball.y <= gateLeft.y + gateLeft.height && ball.y >= gateLeft.y){
            gamePage.score.playerLeft++;
            newGate();
            newPucks();
            mirrorWall = false;
            newBall(3);
            newBonus();
        }

    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        lastCollide1 = 0;
        lastCollide2 = 0;
        int x= 1;
        totalPassedTime = 0;
        while (true){
            if (gamePage.paused) {
                lastTime = System.nanoTime();
                continue;
            }

            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            totalPassedTime += (now-lastTime) / 1000000000.0;
            lastBonusTime += (now-lastTime) / 1000000000.0;
            lastCollide1 += (now - lastTime)/ns;
            lastCollide2 += (now - lastTime)/ns;
            lastTime = now;

            if (delta >= 1){
                puckRight.setDirectionX(0);
                puckRight.setDirectionY(0);
                if (puckRight.isGoingRight()) puckRight.setDirectionX(puckRight.speed);
                if (puckRight.isGoingLeft()) puckRight.setDirectionX(-puckRight.speed);
                if (puckRight.isGoingUp()) puckRight.setDirectionY(-puckRight.speed);
                if (puckRight.isGoingDown()) puckRight.setDirectionY(puckRight.speed);

                puckLeft.setDirectionX(0);
                puckLeft.setDirectionY(0);
                if (puckLeft.isGoingRight()) puckLeft.setDirectionX(puckLeft.speed);
                if (puckLeft.isGoingLeft()) puckLeft.setDirectionX(-puckLeft.speed);
                if (puckLeft.isGoingUp()) puckLeft.setDirectionY(-puckLeft.speed);
                if (puckLeft.isGoingDown()) puckLeft.setDirectionY(puckLeft.speed);

                if (bonusPhase == 0 && lastBonusTime >= 10){
                    bonus.appear();
                    bonusPhase++;
                }
                if (bonusPhase == 1 && lastBonusTime >= 15){
                    bonus.doubleSize();
                    bonusPhase++;
                }
                if (bonusPhase == 2 && lastBonusTime >= 20){
                    bonus.doubleSize();
                    bonusPhase++;
                }
                if (bonusPhase == 3 && lastBonusTime >= 25){
                    newBonus();
                }

                move();
                checkCollision();
                repaint();
                delta--;


                if (gamePage.score.checkIfEnded() || timerDisplay.checkIfEnded(totalPassedTime)){
                    GameHistoryPage.setNewGame(gamePage.playerLeft, gamePage.playerRight,time,goals,gamePage.score.playerLeft,gamePage.score.playerRight,twoMargin,true);
                    gamePage.paused = true;
                    gamePage.finished = true;
                    gamePage.pauseButton.setEnabled(false);
                    gamePage.pauseButton.setVisible(false);
                    gamePage.resumeButton.setVisible(false);
                    gamePage.resumeButton.setEnabled(false);
                    gamePage.backButton.setEnabled(true);
                    gamePage.backButton.setVisible(true);
                }



            }

        }

    }

    public class ActionListener extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            puckLeft.keyPressed(e);
            puckRight.keyPressed(e);

        }
        public void keyReleased(KeyEvent e){
            puckLeft.keyReleased(e);
            puckRight.keyReleased(e);
        }
    }
}
