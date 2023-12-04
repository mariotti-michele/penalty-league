package penaltyleague.view;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class GoalkeeperAnimation implements ActionListener {

    private GamePanel gamePanel;

    private BufferedImage[][] yellowDiveFrames;
    private BufferedImage[][] greenDiveFrames;

    private int indexFrame;
    private int regionDive;

    private int xCoordFrame;
    private int yCoordFrame;
    private int frameWidth;
    private int frameHeight;

    private Timer timer;


    private static final int NUMBER_OF_REGIONS = 6;
    private static final int START_GOALKEEPER_POSITION = 0;
    private static final int TOP_LEFT_GOAL_REGION = 1;
    private static final int TOP_CENTER_GOAL_REGION = 2;
    private static final int TOP_RIGHT_GOAL_REGION = 3;
    private static final int DOWN_LEFT_GOAL_REGION = 4;
    private static final int DOWN_CENTER_GOAL_REGION = 5;
    private static final int DOWN_RIGHT_GOAL_REGION = 6;

    private static final int NUMBER_OF_FRAMES_TOP_LEFT_DIVE = 4;
    private static final int NUMBER_OF_FRAMES_TOP_CENTER_DIVE = 2;
    private static final int NUMBER_OF_FRAMES_TOP_RIGHT_DIVE = 4;
    private static final int NUMBER_OF_FRAMES_DOWN_LEFT_DIVE = 4;
    private static final int NUMBER_OF_FRAMES_DOWN_CENTER_DIVE = 2;
    private static final int NUMBER_OF_FRAMES_DOWN_RIGHT_DIVE = 4;

    public GoalkeeperAnimation(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        this.timer = new Timer(250, this);
        this.indexFrame = 0;
        this.regionDive = START_GOALKEEPER_POSITION;

        yellowDiveFrames = new BufferedImage[NUMBER_OF_REGIONS + 1][];
        yellowDiveFrames[START_GOALKEEPER_POSITION] = new BufferedImage[1];
        yellowDiveFrames[TOP_LEFT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_LEFT_DIVE];
        yellowDiveFrames[TOP_CENTER_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_CENTER_DIVE];
        yellowDiveFrames[TOP_RIGHT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_RIGHT_DIVE];
        yellowDiveFrames[DOWN_LEFT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_LEFT_DIVE];
        yellowDiveFrames[DOWN_CENTER_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_CENTER_DIVE];
        yellowDiveFrames[DOWN_RIGHT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_RIGHT_DIVE];

        greenDiveFrames = new BufferedImage[7][];
        greenDiveFrames[START_GOALKEEPER_POSITION] = new BufferedImage[1];
        greenDiveFrames[TOP_LEFT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_LEFT_DIVE];
        greenDiveFrames[TOP_CENTER_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_CENTER_DIVE];
        greenDiveFrames[TOP_RIGHT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_TOP_RIGHT_DIVE];
        greenDiveFrames[DOWN_LEFT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_LEFT_DIVE];
        greenDiveFrames[DOWN_CENTER_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_CENTER_DIVE];
        greenDiveFrames[DOWN_RIGHT_GOAL_REGION] = new BufferedImage[NUMBER_OF_FRAMES_DOWN_RIGHT_DIVE];

        loadImages();
    }

    private void loadImages(){
        for(int i = 0; i < yellowDiveFrames.length; i++){
            for(int j = 0; j < yellowDiveFrames[i].length; j++){
                yellowDiveFrames[i][j] = null;
                greenDiveFrames[i][j] = null;
            }
        }
        try{
            for(int i = 0; i < yellowDiveFrames.length; i++){
                yellowDiveFrames[i][0] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowStart.png");
                greenDiveFrames[i][0] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenStart.png");
            }
            yellowDiveFrames[TOP_LEFT_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowMovingToLeft.png");
            greenDiveFrames[TOP_LEFT_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenMovingToLeft.png");
            yellowDiveFrames[TOP_LEFT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowTopLeft.png");
            greenDiveFrames[TOP_LEFT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenTopLeft.png");

            yellowDiveFrames[TOP_CENTER_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowTopCenter.png");
            greenDiveFrames[TOP_CENTER_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenTopCenter.png");

            yellowDiveFrames[TOP_RIGHT_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowMovingToRight.png");
            greenDiveFrames[TOP_RIGHT_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenMovingToRight.png");
            yellowDiveFrames[TOP_RIGHT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowTopRight.png");
            greenDiveFrames[TOP_RIGHT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenTopRight.png");

            yellowDiveFrames[DOWN_LEFT_GOAL_REGION][1] = yellowDiveFrames[1][1];
            greenDiveFrames[DOWN_LEFT_GOAL_REGION][1] = greenDiveFrames[1][1];
            yellowDiveFrames[DOWN_LEFT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowDownLeft.png");
            greenDiveFrames[DOWN_LEFT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenDownLeft.png");

            yellowDiveFrames[DOWN_CENTER_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowDownCenter.png");
            greenDiveFrames[DOWN_CENTER_GOAL_REGION][1] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenDownCenter.png");

            yellowDiveFrames[DOWN_RIGHT_GOAL_REGION][1] = yellowDiveFrames[3][1];
            greenDiveFrames[DOWN_RIGHT_GOAL_REGION][1] = greenDiveFrames[3][1];
            yellowDiveFrames[DOWN_RIGHT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowDownRight.png");
            greenDiveFrames[DOWN_RIGHT_GOAL_REGION][2] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenDownRight.png");

            yellowDiveFrames[DOWN_LEFT_GOAL_REGION][3] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowFinalLeft.png");
            yellowDiveFrames[DOWN_RIGHT_GOAL_REGION][3] = ResourceManager.loadImage("goalkeeper" + File.separator + "yellowFinalRight.png");
            greenDiveFrames[DOWN_LEFT_GOAL_REGION][3] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenFinalLeft.png");
            greenDiveFrames[DOWN_RIGHT_GOAL_REGION][3] = ResourceManager.loadImage("goalkeeper" + File.separator + "greenFinalRight.png");
            yellowDiveFrames[TOP_LEFT_GOAL_REGION][3] = yellowDiveFrames[DOWN_LEFT_GOAL_REGION][3];
            yellowDiveFrames[TOP_RIGHT_GOAL_REGION][3] = yellowDiveFrames[DOWN_RIGHT_GOAL_REGION][3];
            greenDiveFrames[TOP_LEFT_GOAL_REGION][3] = greenDiveFrames[DOWN_LEFT_GOAL_REGION][3];
            greenDiveFrames[TOP_RIGHT_GOAL_REGION][3] = greenDiveFrames[DOWN_RIGHT_GOAL_REGION][3];
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setFrameDimension(){
        if(regionDive == TOP_CENTER_GOAL_REGION && indexFrame == 1){
            frameWidth = (int)(frameWidth * 0.65);
            frameHeight = (int)(frameHeight * 1.43);
        }
        else if(regionDive == DOWN_CENTER_GOAL_REGION && indexFrame == 1){
            frameWidth = (int)(frameWidth * 1.28);
            frameHeight = (int)(frameHeight * 0.79);
        }
        else if((regionDive == TOP_LEFT_GOAL_REGION || regionDive == TOP_RIGHT_GOAL_REGION
                || regionDive == DOWN_LEFT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION) && indexFrame == 1){
            frameWidth = (int)(frameWidth * 1.83);
            frameHeight = (int)(frameHeight * 0.96);
        }
        else if((regionDive == TOP_LEFT_GOAL_REGION || regionDive == TOP_RIGHT_GOAL_REGION) && indexFrame == 2){
            frameWidth = (int)(frameWidth * 2.23);
            frameHeight = (int)(frameHeight * 0.73);
        }
        else if((regionDive == DOWN_LEFT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION) && indexFrame == 2){
            frameWidth = (int)(frameWidth * 2.37);
            frameHeight = (int)(frameHeight * 0.50);
        }
        else if((regionDive == DOWN_LEFT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION
                || regionDive == TOP_LEFT_GOAL_REGION || regionDive == TOP_RIGHT_GOAL_REGION) && indexFrame == 3){
            frameWidth = (int)(frameWidth * 2.47);
            frameHeight = (int)(frameHeight * 0.47);
        }
    }

    private void setLeftDivePosition(){
        if(indexFrame == 1){
            xCoordFrame = gamePanel.getWidth()/2 - frameWidth;
            yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight;
        }
        else if(regionDive == TOP_LEFT_GOAL_REGION && indexFrame == 2){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = (int) (gamePanel.getLeftPostCenterCoord() + 20 * gamePanel.getView().getUnitOfMeasureScreen());
                yCoordFrame = (int) (gamePanel.getCrossbarCenterCoord() + 20 * gamePanel.getView().getUnitOfMeasureScreen());

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - gamePanel.getBall().getBallDimension()/2;
                    yCoordFrame = gamePanel.getShotRoundView().getHeight() - gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - gamePanel.getBall().getBallDimension()/2;
                yCoordFrame = gamePanel.getSaveRoundView().getYCoordSaveAttempt() - gamePanel.getBall().getBallDimension()/2;
            }
        }
        else if(regionDive == DOWN_LEFT_GOAL_REGION && indexFrame == 2){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = gamePanel.getLeftPostCenterCoord();
                yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight;

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - gamePanel.getBall().getBallDimension()/2;
                    yCoordFrame = gamePanel.getShotRoundView().getHeight() - frameHeight + gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - gamePanel.getBall().getBallDimension()/2;
                yCoordFrame = gamePanel.getSaveRoundView().getYCoordSaveAttempt() - frameHeight + gamePanel.getBall().getBallDimension()/2;
            }
        }
        else if((regionDive == DOWN_LEFT_GOAL_REGION || regionDive == TOP_LEFT_GOAL_REGION) && indexFrame == 3){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = gamePanel.getLeftPostCenterCoord();

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - gamePanel.getBall().getBallDimension()/2;
            }
            yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight*3/4;
        }
    }

    private void setCenterDivePosition(){
        if(indexFrame == 1){
            if(gamePanel.getView().getLogic().isShotRound()){
                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - frameWidth/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - frameWidth/2;
            }

            yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight;
        }
    }

    private void setRightDivePosition(){
        if(indexFrame == 1){
            xCoordFrame = gamePanel.getWidth()/2;
            yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight;
        }
        else if(regionDive == TOP_RIGHT_GOAL_REGION && indexFrame == 2){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = (int) (gamePanel.getRightPostCenterCoord() - frameWidth - 20
                                        * gamePanel.getView().getUnitOfMeasureScreen());
                yCoordFrame = (int) (gamePanel.getCrossbarCenterCoord() + 20 * gamePanel.getView().getUnitOfMeasureScreen());

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - frameWidth + gamePanel.getBall().getBallDimension()/2;
                    yCoordFrame = gamePanel.getShotRoundView().getHeight() - gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - frameWidth + gamePanel.getBall().getBallDimension()/2;
                yCoordFrame = gamePanel.getSaveRoundView().getYCoordSaveAttempt() - gamePanel.getBall().getBallDimension()/2;
            }
        }
        else if(regionDive == DOWN_RIGHT_GOAL_REGION && indexFrame == 2){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = gamePanel.getRightPostCenterCoord() - frameWidth;
                yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight;

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - frameWidth + gamePanel.getBall().getBallDimension()/2;
                    yCoordFrame = gamePanel.getShotRoundView().getHeight() - frameHeight + gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - frameWidth + gamePanel.getBall().getBallDimension()/2;
                yCoordFrame = gamePanel.getSaveRoundView().getYCoordSaveAttempt() - frameHeight + gamePanel.getBall().getBallDimension()/2;
            }
        }
        else if((regionDive == DOWN_RIGHT_GOAL_REGION || regionDive == TOP_RIGHT_GOAL_REGION) && indexFrame == 3){
            if(gamePanel.getView().getLogic().isShotRound()){

                xCoordFrame = gamePanel.getRightPostCenterCoord() - frameWidth;

                if(!gamePanel.getShotRoundView().isGoal() && gamePanel.getView().getLogic().isShotTowardGoal(
                        gamePanel.getShotRoundView().getDirection(), gamePanel.getShotRoundView().getHeight())){

                    xCoordFrame = gamePanel.getShotRoundView().getDirection() - frameWidth + gamePanel.getBall().getBallDimension()/2;
                }
            }
            else {
                xCoordFrame = gamePanel.getSaveRoundView().getXCoordSaveAttempt() - frameWidth + gamePanel.getBall().getBallDimension()/2;
            }
            yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight() - frameHeight*3/4;
        }
    }

    protected void drawCurrentFrame(Graphics2D g2d){
        frameHeight = gamePanel.getGoalHeight() * 3/4;
        frameWidth = (int)(frameHeight * 0.58);
        setFrameDimension();

        xCoordFrame = gamePanel.getWidth()/2 - frameWidth/2;
        yCoordFrame = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight()/4;
        if(regionDive == TOP_LEFT_GOAL_REGION || regionDive == DOWN_LEFT_GOAL_REGION){
            setLeftDivePosition();
        }
        else if(regionDive == TOP_CENTER_GOAL_REGION || regionDive == DOWN_CENTER_GOAL_REGION){
            setCenterDivePosition();
        }
        else if(regionDive == TOP_RIGHT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION){
            setRightDivePosition();
        }

        if(gamePanel.getView().getLogic().isShotRound()){
            g2d.drawImage(greenDiveFrames[regionDive][indexFrame], xCoordFrame, yCoordFrame,
                    frameWidth, frameHeight, null);
        }
        else{
            g2d.drawImage(yellowDiveFrames[regionDive][indexFrame], xCoordFrame, yCoordFrame,
                    frameWidth, frameHeight, null);
        }
    }

    protected void start(int regionDive, int shotDuration){
        this.regionDive = regionDive;
        int frames = yellowDiveFrames[regionDive].length;
        if(regionDive == TOP_LEFT_GOAL_REGION || regionDive == TOP_RIGHT_GOAL_REGION
                || regionDive == DOWN_LEFT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION)
            frames --;
        timer.setInitialDelay(shotDuration / frames);
        timer.setDelay(shotDuration / frames);
        timer.start();
    }

    protected void stop(){
        timer.stop();
        indexFrame = 0;
        regionDive = START_GOALKEEPER_POSITION;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(indexFrame + 1 < yellowDiveFrames[regionDive].length){
            if((regionDive == TOP_LEFT_GOAL_REGION || regionDive == DOWN_LEFT_GOAL_REGION
                    || regionDive == TOP_RIGHT_GOAL_REGION || regionDive == DOWN_RIGHT_GOAL_REGION) && indexFrame == 2){

                if(gamePanel.getBall().isShotDone()){
                    indexFrame++;
                    gamePanel.repaint();
                }

            }
            else {
                indexFrame++;
                gamePanel.repaint();
            }
        }
    }
}