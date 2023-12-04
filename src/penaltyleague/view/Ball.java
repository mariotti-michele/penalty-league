package penaltyleague.view;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Ball implements ActionListener {

    private GamePanel gamePanel;
    private BufferedImage[] ballFrames;
    private int indexFrame;
    private int xStartBall;
    private int yStartBall;
    private int xBall;  //of the center of the ball
    private int yBall;  ////of the center of the ball
    private int ballDimension;
    private int shotDirection;
    private int shotHeight;

    private Timer timer;
    private static final int TIMER_DELAY = 10;
    private int shotDuration;
    private boolean shotDone;
    private static final int BALL_DIMENSION_RATIO = 38; //ball height and width is 1:38 of the width of the panel
    private static final double Y_START_BALL_RATIO = 49.0/60;
    private static final int MIN_DURATION_SHOT = 50;
    private static final int MAX_DURATION_SHOT = 500;

    public Ball(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        loadBallImage();
        this.timer = new Timer(TIMER_DELAY, this);
        shotDone = false;
        indexFrame = 0;
    }

    private void loadBallImage(){
        this.ballFrames = new BufferedImage[2];
        try{
            this.ballFrames[0] = ResourceManager.loadImage("ball1.png");
            this.ballFrames[1] = ResourceManager.loadImage("ball2.png");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected int getXStartBall(){
        return xStartBall;
    }

    protected int getYStartBall(){
        return yStartBall;
    }

    protected int getBallDimension(){
        return ballDimension;
    }

    private BufferedImage getBallImage(){
        return ballFrames[indexFrame];
    }

    protected int getShotDuration(){
        return shotDuration;
    }

    protected void resetStartBall(){
        xBall = xStartBall;
        yBall = yStartBall;
        shotDone = false;
    }

    protected void initBall(){
        this.xStartBall = gamePanel.getWidth()/2;
        this.yStartBall = (int)(gamePanel.getHeight() * Y_START_BALL_RATIO);
        this.xBall = xStartBall;
        this.yBall = yStartBall;
        this.ballDimension = gamePanel.getWidth() / BALL_DIMENSION_RATIO;
    }

    protected void drawBall(Graphics2D g2d){
        g2d.drawImage(this.getBallImage(), xBall - ballDimension/2, yBall - ballDimension/2,
                ballDimension, ballDimension, null);
    }

    protected void moveTo(int x, int y, int power){
        shotDuration = (int) (MIN_DURATION_SHOT + ((MAX_DURATION_SHOT - MIN_DURATION_SHOT) * (100.0 - power)/100.0));
        shotDirection = x;
        shotHeight = y;
        this.timer.start();
    }

    protected boolean isShotDone(){
        return shotDone;
    }

    private void stop(){
        timer.stop();
    }

    private void checkHitPosts(){
        if(gamePanel.getView().getLogic().checkHitLeftPostAndGoal(shotDirection)){
            xBall = gamePanel.getLeftPostCenterCoord() + gamePanel.getPostsWidth()/2 + getBallDimension();
        }
        else if(gamePanel.getView().getLogic().checkHitRightPostAndGoal(shotDirection)){
            xBall = gamePanel.getRightPostCenterCoord() - gamePanel.getPostsWidth()/2 - getBallDimension();
        }

        if(gamePanel.getView().getLogic().checkHitCrossbarAndGoal(shotHeight)){
            yBall = gamePanel.getCrossbarCenterCoord() + gamePanel.getPostsWidth()/2 + getBallDimension();
        }
    }

    private void handleShotActionPerformed(int yShift){
        if(yBall - yShift <= shotHeight){
            yBall = shotHeight;
            xBall = shotDirection;

            if(gamePanel.getView().getLogic().isShotTowardGoal(shotDirection, shotHeight))
                checkHitPosts();

            shotDone = true;
            if(!gamePanel.getView().getLogic().isShotRound()){
                gamePanel.getSaveRoundView().startResetTimer();
            }
        }
        else{
            yBall -= yShift;
            double m = (double)(shotDirection - xStartBall) / (double)(shotHeight - yStartBall);
            xBall = (int) (xStartBall + m * (yBall - yStartBall));
        }
    }

    private void handleGravityActionPerformed(int yShift){
        if(gamePanel.getView().getLogic().isShotRound() && gamePanel.getShotRoundView().isGoal()
                || !gamePanel.getView().getLogic().isShotRound() && !gamePanel.getSaveRoundView().isShotSaved()){

            if(yBall + yShift >= gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - getBallDimension()){
                yBall = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - getBallDimension();
                this.stop();
            }
            else{
                yBall += yShift;
            }

        }
        else if(shotHeight >= gamePanel.getCrossbarCenterCoord() - gamePanel.getPostsWidth()/2 - getBallDimension()/2){

            if(yBall + yShift >= gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() + getBallDimension()){
                yBall = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() + getBallDimension();
                this.stop();
            }
            else{
                yBall += yShift;
            }
        }
        else{
            this.stop();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int yShift = (yStartBall - shotHeight) / (shotDuration/TIMER_DELAY);
        if(!shotDone){
            handleShotActionPerformed(yShift);
        }
        else {
            handleGravityActionPerformed(yShift);
        }
        indexFrame = (indexFrame + 1) % 2;

        this.gamePanel.repaint();
    }
}