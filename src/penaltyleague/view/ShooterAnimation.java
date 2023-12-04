package penaltyleague.view;

import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShooterAnimation implements ActionListener {

    private GamePanel gamePanel;

    private Timer timer;
    private static final int TIMER_DELAY = 150;
    private int eventsCounter;

    private int indexFrame;

    public ShooterAnimation(GamePanel gamePanel){
        timer = new Timer(TIMER_DELAY, this);
        eventsCounter = 0;
        this.gamePanel = gamePanel;
    }

    protected void start(){
        indexFrame = 0;
        timer.start();
    }

    protected void stop(){
        timer.stop();
        gamePanel.getSaveRoundView().setIndicatorVisible(false);
        if(!gamePanel.getView().getLogic().isShotRound() && !gamePanel.getView().getLogic().isShotTowardGoal(
                gamePanel.getSaveRoundView().getShotDirection(), gamePanel.getSaveRoundView().getShotHeight())){

            gamePanel.getSaveRoundView().setShotSaved(true);

        }
        eventsCounter = 0;
    }

    protected void resetStartFrame(){
        indexFrame = 0;
    }

    protected void drawCurrentFrame(Graphics2D g2d){
        int teamIndex = gamePanel.getView().getLogic().getOpponentTeamIndex();
        if(gamePanel.getView().getLogic().isShotRound())
            teamIndex = gamePanel.getView().getLogic().getUserTeamIndex();

        int frameHeight = (int)(gamePanel.getGoalHeight() * 3/4 * 1.18);
        int frameWidth = frameHeight * gamePanel.getView().getTeams().getShooterFrame(teamIndex, indexFrame).getWidth()
                /gamePanel.getView().getTeams().getShooterFrame(teamIndex, indexFrame).getHeight();
        int yCoordFrame = gamePanel.getBall().getYStartBall() - frameHeight + gamePanel.getBall().getBallDimension();
        int xCoordFrame = gamePanel.getBall().getXStartBall() - frameWidth;

        g2d.drawImage(gamePanel.getView().getTeams().getShooterFrame(teamIndex, indexFrame),
                xCoordFrame, yCoordFrame, frameWidth, frameHeight, null);
    }

    private void handleActionPerformedShotRound(){
        if (indexFrame + 1 < gamePanel.getView().getTeams().getNumberOfFrames()) {
            if(indexFrame == 0)
                gamePanel.getView().getSound().playWhistleSound();

            indexFrame++;
        }
        else {
            this.stop();

            gamePanel.getView().getSound().playKickSound();
            gamePanel.getBall().moveTo(gamePanel.getShotRoundView().getDirection(),
                    gamePanel.getShotRoundView().getHeight(), gamePanel.getShotRoundView().getPower());

            int region = gamePanel.getView().getLogic().getRegionSaveAttempt();

            gamePanel.getGoalkeeperAnimation().start(region, gamePanel.getBall().getShotDuration());
        }
        gamePanel.repaint();
    }

    private void handleActionPerformedSaveRound(){
        if(eventsCounter == 0){
            gamePanel.getSaveRoundView().generateShotCoordinates();
        }

        eventsCounter++;

        int opponentTeamIndex = gamePanel.getView().getLogic().getOpponentTeamIndex();
        if(eventsCounter == gamePanel.getView().getTeams().getTeamRating(opponentTeamIndex) + 9){
            gamePanel.getSaveRoundView().setIndicatorVisible(true);
            gamePanel.repaint();
        }

        if (eventsCounter >= 15) {
            if (indexFrame + 1 < gamePanel.getView().getTeams().getNumberOfFrames()) {
                if(indexFrame == 0)
                    gamePanel.getView().getSound().playWhistleSound();

                indexFrame++;
            }
            else {
                this.stop();

                gamePanel.getView().getSound().playKickSound();
                gamePanel.getBall().moveTo(gamePanel.getSaveRoundView().getShotDirection(),
                        gamePanel.getSaveRoundView().getShotHeight(),
                        95 - 7*(5 - gamePanel.getView().getTeams().getTeamRating(opponentTeamIndex)));

                int region = gamePanel.getView().getLogic().getRegionSaveAttempt(
                        gamePanel.getSaveRoundView().getXCoordSaveAttempt(),
                        gamePanel.getSaveRoundView().getYCoordSaveAttempt());
                gamePanel.getGoalkeeperAnimation().start(region, gamePanel.getBall().getShotDuration());
            }
            gamePanel.repaint();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gamePanel.getView().getLogic().isShotRound()) {
            handleActionPerformedShotRound();
        }
        else {
            handleActionPerformedSaveRound();
        }
    }
}
