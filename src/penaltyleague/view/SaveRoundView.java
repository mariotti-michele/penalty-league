package penaltyleague.view;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SaveRoundView implements ActionListener {

    private GamePanel gamePanel;

    private BufferedImage indicatorImage;
    private int indicatorWidth;
    private int indicatorHeight;

    private int xCoordSaveAttempt;
    private int yCoordSaveAttempt;

    private int shotDirection;
    private int shotHeight;

    private boolean indicatorVisible;

    private Timer resetTimer;
    protected static final int TIMER_DELAY = 100;
    private static final int EVENTS_BEFORE_RESTART = 30;
    private int resetEventsCounter;

    private boolean shotSaved;

    private BufferedImage savedSign;
    private BufferedImage missedSign;
    private boolean signVisible;

    public SaveRoundView(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        loadImages();
        this.resetTimer = new Timer(TIMER_DELAY, this);
        resetEventsCounter = 0;
        shotSaved = false;
        indicatorVisible = false;
        signVisible = false;
    }

    private void loadImages(){
        this.indicatorImage = null;
        this.savedSign = null;
        this.missedSign = null;
        try{
            this.indicatorImage = ResourceManager.loadImage("gloves.png");
            this.savedSign = ResourceManager.loadImage("savedSign.png");
            this.missedSign = ResourceManager.loadImage("missedSign.png");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void initIndicator(){
        indicatorWidth = gamePanel.getGoalWidth()/12;
        indicatorHeight = indicatorWidth * indicatorImage.getHeight() / indicatorImage.getWidth();

        xCoordSaveAttempt = gamePanel.getLeftPostCenterCoord() + gamePanel.getGoalWidth()/2;
        yCoordSaveAttempt = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight()/2;
    }

    protected void drawIndicator(Graphics2D g2d){
        g2d.drawImage(indicatorImage, shotDirection - indicatorWidth/2, shotHeight - indicatorHeight/2,
                indicatorWidth, indicatorHeight, null);
    }

    protected void setCoordSaveAttempt(int xCoordSaveAttempt, int yCoordSaveAttempt){
        this.xCoordSaveAttempt = xCoordSaveAttempt;
        this.yCoordSaveAttempt = yCoordSaveAttempt;
    }

    protected int getXCoordSaveAttempt(){
        return xCoordSaveAttempt;
    }

    protected int getYCoordSaveAttempt(){
        return yCoordSaveAttempt;
    }

    protected int getShotDirection(){
        return shotDirection;
    }

    protected int getShotHeight(){
        return shotHeight;
    }

    protected int getTopBorderIndicator(){
        return shotHeight - indicatorHeight/2;
    }

    protected int getDownBorderIndicator(){
        return shotHeight + indicatorHeight/2;
    }

    protected int getLeftBorderIndicator(){
        return shotDirection - indicatorWidth/2;
    }

    protected int getRightBorderIndicator(){
        return shotDirection + indicatorWidth/2;
    }

    protected void setShotSaved(boolean saved){
        shotSaved = saved;
    }

    protected boolean isShotSaved(){
        return shotSaved;
    }

    protected boolean isIndicatorVisible(){
        return indicatorVisible;
    }

    protected void setIndicatorVisible(boolean visible){
        indicatorVisible = visible;
    }

    protected boolean isSignVisible(){
        return signVisible;
    }


    protected void drawSign(Graphics2D g2d){
        int signWidth = gamePanel.getWidth()/3;
        int signHeight = signWidth * savedSign.getHeight()/savedSign.getWidth();
        if(shotSaved){
            g2d.drawImage(savedSign, gamePanel.getWidth()/2 - signWidth/2, gamePanel.getHeight()/2 - signHeight/2,
                    signWidth, signHeight, null);
        }
        else {
            g2d.drawImage(missedSign, gamePanel.getWidth()/2 - signWidth/2, gamePanel.getHeight()/2 - signHeight/2,
                    signWidth, signHeight, null);
        }
    }

    protected void generateShotCoordinates(){
        int outsideMarginBorder = (int) (40 * gamePanel.getView().getUnitOfMeasureScreen());
        shotDirection = gamePanel.getView().getLogic().generateShotDirection(
                gamePanel.getLeftPostCenterCoord() - outsideMarginBorder,
                gamePanel.getLeftPostCenterCoord() + gamePanel.getGoalWidth() + outsideMarginBorder);
        shotHeight = gamePanel.getView().getLogic().generateShotHeight(
                gamePanel.getCrossbarCenterCoord() - outsideMarginBorder,
                gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight());
    }

    protected void startResetTimer(){
        this.resetTimer.start();
    }

    protected void stop(){
        this.resetTimer.stop();
        resetEventsCounter = 0;
        xCoordSaveAttempt = gamePanel.getLeftPostCenterCoord() + gamePanel.getGoalWidth()/2;
        yCoordSaveAttempt = gamePanel.getCrossbarCenterCoord() + gamePanel.getGoalHeight()/2;
        shotSaved = false;
        signVisible = false;
    }

    private void endOfRound(){
        this.gamePanel.getShooterAnimation().resetStartFrame();
        gamePanel.getBall().resetStartBall();
        this.stop();
        this.gamePanel.getGoalkeeperAnimation().stop();
        gamePanel.resetNet();
        if(gamePanel.getView().getLogic().checkUserWin()){
            gamePanel.getView().getLogic().increaseTournamentPhase();
            gamePanel.getView().getMainGUI().changeToTournamentBracketPanel();
            gamePanel.reset();
        }
        else if(gamePanel.getView().getLogic().checkOpponentWin()){
            gamePanel.getView().getLogic().setGameOverPhase();
            gamePanel.getView().getMainGUI().changeToTournamentBracketPanel();
            gamePanel.reset();
        }
        else{
            this.gamePanel.getView().getLogic().changeRound();
            this.gamePanel.getShotRoundView().start();
        }
        gamePanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (resetEventsCounter < EVENTS_BEFORE_RESTART) {

            if(resetEventsCounter == 0){
                if(isShotSaved()){
                    gamePanel.getView().getSound().playCelebrationSound();
                }
                else{
                    gamePanel.getView().getSound().playDelusionSound();
                    gamePanel.getView().getSound().playNetSound();
                }
                gamePanel.getView().getLogic().updateOpponentScoreboard(!shotSaved);

                signVisible = true;

                gamePanel.repaint();
            }

            if(!isShotSaved())
                gamePanel.moveNet();

            resetEventsCounter++;

        }
        else {
            endOfRound();
        }
    }
}