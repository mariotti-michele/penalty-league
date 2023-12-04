package penaltyleague.view;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements MouseInputListener {

    private View view;

    private BufferedImage pitchImg;
    private BufferedImage netImg;
    private BufferedImage goalMarkImg;
    private BufferedImage missedMarkImg;

    private Ball ball;
    private ShotRoundView shotRoundView;
    private SaveRoundView saveRoundView;
    private ShooterAnimation shooterAnimation;
    private GoalkeeperAnimation goalkeeperAnimation;

    private int goalWidth; //from-to the centers of the posts
    private int goalHeight; // = 1/3 goalWidth
    private boolean firstDraw;
    private boolean firstRoundOfMatch;

    private int xCoordInnerNetRelativeToLeftPost;
    private int yCoordInnerNetRelativeToCrossbar;
    private int xShiftNet;
    private int yShiftNet;
    private boolean netShrinking;
    private int netMoveCounter;
    private static final int NUMBER_MOVE_NET = 2;

    private static final int TEXT_SIZE = 70;

    public GamePanel(View view) {
        super();
        this.view = view;

        loadImages();

        ball = new Ball(this);
        shotRoundView = new ShotRoundView(this);
        saveRoundView = new SaveRoundView(this);
        shooterAnimation = new ShooterAnimation(this);
        goalkeeperAnimation = new GoalkeeperAnimation(this);

        firstDraw = true;
        firstRoundOfMatch = true;

        xShiftNet = 0;
        yShiftNet = 0;
        xCoordInnerNetRelativeToLeftPost = (int) (83 * view.getUnitOfMeasureScreen());
        yCoordInnerNetRelativeToCrossbar = (int) (61 * view.getUnitOfMeasureScreen());
        netShrinking = false;
        netMoveCounter = 0;

        addMouseListener(this);
    }

    private void loadImages(){
        this.pitchImg = null;
        this.goalMarkImg = null;
        this.missedMarkImg = null;
        this.netImg = null;
        try{
            this.pitchImg = ResourceManager.loadImage("pitch.png");
            this.goalMarkImg = ResourceManager.loadImage("goalMark.png");
            this.missedMarkImg = ResourceManager.loadImage("missedMark.png");
            this.netImg = ResourceManager.loadImage("net.png");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected int getGoalHeight(){
        return goalHeight;
    }

    protected int getGoalWidth(){
        return goalWidth;
    }

    protected int getLeftPostCenterCoord(){    //goal post = palo porta; ottengo il centro del palo
        return this.getWidth()/4;
    }

    protected int getRightPostCenterCoord(){    //post = palo; ottengo il centro del palo
        return this.getWidth()*3/4;
    }

    protected int getCrossbarCenterCoord(){      //get the center of the crossbar
        return this.getHeight()/4 - this.getGoalHeight()/2;
    }

    protected int getPostsWidth(){
        return (int) (20 * view.getUnitOfMeasureScreen());
    }

    protected Ball getBall(){
        return this.ball;
    }

    protected View getView(){
        return this.view;
    }

    protected ShotRoundView getShotRoundView(){
        return this.shotRoundView;
    }

    protected SaveRoundView getSaveRoundView(){
        return this.saveRoundView;
    }

    protected ShooterAnimation getShooterAnimation(){
        return this.shooterAnimation;
    }

    protected GoalkeeperAnimation getGoalkeeperAnimation(){
        return this.goalkeeperAnimation;
    }

    protected void reset(){
        firstRoundOfMatch = true;
        view.getSound().stopCheeringSound();
    }

    protected void moveNet(){
        if(xShiftNet == 0 && !netShrinking){
            xShiftNet = this.getWidth()/400;
            yShiftNet = this.getWidth()/800;
        }
        else if(xShiftNet == this.getWidth()/400 && !netShrinking){
            xShiftNet = this.getWidth()/200;
            yShiftNet = this.getWidth()/400;
        }
        else if(xShiftNet == this.getWidth()/200 && !netShrinking){
            xShiftNet = this.getWidth()/100;
            yShiftNet = this.getWidth()/200;
            netShrinking = true;
        }
        else if(xShiftNet == this.getWidth()/100 && netShrinking){
            xShiftNet = this.getWidth()/200;
            yShiftNet = this.getWidth()/400;
        }
        else if(xShiftNet == this.getWidth()/200 && netShrinking){
            xShiftNet = this.getWidth()/400;
            yShiftNet = this.getWidth()/800;
        }
        else if(netMoveCounter < NUMBER_MOVE_NET){
            netMoveCounter ++;
            xShiftNet = 0;
            yShiftNet = 0;
            netShrinking = false;
        }

        this.repaint();
    }

    protected void resetNet(){
        xShiftNet = 0;
        yShiftNet = 0;
        netShrinking = false;
        netMoveCounter = 0;
    }

    private void drawUserScoreboard(Graphics2D g2d){
        BufferedImage userTeamLogo = view.getTeams().getTeamLogo(view.getLogic().getUserTeamIndex());
        int logoHeight = this.getWidth()/15;
        int logoWidth = logoHeight * userTeamLogo.getWidth()/userTeamLogo.getHeight();
        g2d.drawImage(userTeamLogo, this.getWidth()/26 - logoWidth/2, this.getHeight()/60,
                logoWidth, logoHeight, null);

        String userScore = String.valueOf(view.getLogic().getUserScore());
        g2d.drawString(userScore, (int)(this.getWidth()/9.5), this.getHeight()/10);

        for(int i = 0; i < view.getLogic().getUserShotsCounter(); i++){
            int dimension = this.getWidth() / 50;
            int yCoord = (int) (this.getHeight()/6.3) + (dimension + this.getWidth()/500) * (i / 6);
            if(view.getLogic().getUserScoreboard().get(i)) {
                g2d.drawImage(goalMarkImg,
                        this.getWidth()/250 + this.getWidth()/500 + (this.getWidth()/500 + dimension) * (i % 6),
                        yCoord, dimension, dimension, null);
            }
            else{
                g2d.drawImage(missedMarkImg,
                        this.getWidth()/250 + this.getWidth()/500 + (this.getWidth()/500 + dimension) * (i % 6),
                        yCoord, dimension, dimension, null);
            }
        }
    }

    private void drawOpponentScoreboard(Graphics2D g2d){
        BufferedImage opponentTeamLogo = view.getTeams().getTeamLogo(view.getLogic().getOpponentTeamIndex());
        int logoHeight = this.getWidth()/15;
        int logoWidth = logoHeight * opponentTeamLogo.getWidth()/opponentTeamLogo.getHeight();
        g2d.drawImage(opponentTeamLogo, this.getWidth() - (this.getWidth()/26 - logoWidth/2) - logoWidth,
                this.getHeight()/60, logoWidth, logoHeight, null);

        String opponentScore = String.valueOf(view.getLogic().getOpponentScore());
        g2d.drawString(opponentScore, (int)(this.getWidth()*8.75/10), this.getHeight()/10);

        for(int i = 0; i < view.getLogic().getOpponentShotsCounter(); i++){
            int dimension = this.getWidth() / 50;
            int yCoord = (int) (this.getHeight()/6.3) + (dimension + this.getWidth()/500) * (i / 6);
            if(view.getLogic().getOpponentScoreboard().get(i))
                g2d.drawImage(goalMarkImg,
                        (int)(this.getWidth()*8.52/10) + this.getWidth()/500 + (this.getWidth()/500 + dimension) * (i % 6),
                        yCoord, dimension, dimension, null);
            else{
                g2d.drawImage(missedMarkImg,
                        (int)(this.getWidth()*8.52/10) + this.getWidth()/500 + (this.getWidth()/500 + dimension) * (i % 6),
                        yCoord, dimension, dimension, null);
            }
        }
    }

    private void drawScoreboards(Graphics2D g2d){
        drawUserScoreboard(g2d);
        drawOpponentScoreboard(g2d);
    }

    private void drawShotRound(Graphics2D g2d){
        if(shotRoundView.isSignVisible())  //the ball will be above the sign
            shotRoundView.drawSign(g2d);

        if(shotRoundView.isGoal()){
            ball.drawBall(g2d);
            goalkeeperAnimation.drawCurrentFrame(g2d);
        }
        else {
            goalkeeperAnimation.drawCurrentFrame(g2d);
            ball.drawBall(g2d);
        }

        shotRoundView.drawSelectors(g2d);
    }

    private void drawSaveRound(Graphics2D g2d){
        if(saveRoundView.isSignVisible())   //the ball will be above the sign
            saveRoundView.drawSign(g2d);

        if(saveRoundView.isShotSaved()){
            goalkeeperAnimation.drawCurrentFrame(g2d);
            ball.drawBall(g2d);
        }
        else {
            ball.drawBall(g2d);
            goalkeeperAnimation.drawCurrentFrame(g2d);
        }

        if(saveRoundView.isIndicatorVisible())
            saveRoundView.drawIndicator(g2d);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)(g);

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2d.setFont(new Font("Arial", Font.PLAIN, (int) (TEXT_SIZE * view.getUnitOfMeasureScreen())));

        g2d.drawImage(pitchImg, 0, 0, this.getWidth(), this.getHeight(), null);

        int netWidth = getGoalWidth() - 2*xCoordInnerNetRelativeToLeftPost - 2* xShiftNet;
        g2d.drawImage(netImg, getLeftPostCenterCoord() + xCoordInnerNetRelativeToLeftPost + xShiftNet,
                getCrossbarCenterCoord() + yCoordInnerNetRelativeToCrossbar + yShiftNet,
                netWidth, netWidth * netImg.getHeight() / netImg.getWidth(), null);

        if(firstDraw){ //in the constructor the width and height of the panel is 0
            goalWidth = this.getWidth()/2;
            goalHeight = goalWidth/3 - getPostsWidth()/2;
            ball.initBall();
            shotRoundView.initSelectors(ball);
            saveRoundView.initIndicator();

            this.repaint();

            firstDraw = false;
        }
        if(firstRoundOfMatch){
            if(view.getLogic().isShotRound()){
                shotRoundView.start();
            }
            else {
                shooterAnimation.start();
            }
            firstRoundOfMatch = false;
        }

        if(view.getLogic().isShotRound()){
            drawShotRound(g2d);
        }
        else{
            drawSaveRound(g2d);
        }
        shooterAnimation.drawCurrentFrame(g2d);
        drawScoreboards(g2d);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //DO NOTHING
    }

    private void handleMousePressedShotRound(){
        if(shotRoundView.getSelectionPhase() == ShotRoundView.DIRECTION_SELECTION_PHASE){
            shotRoundView.setDirection();
            shotRoundView.increaseSelectionPhase();
        }
        else if(shotRoundView.getSelectionPhase() == ShotRoundView.HEIGHT_SELECTION_PHASE){
            shotRoundView.setHeight();
            shotRoundView.increaseSelectionPhase();
        }
        else if(shotRoundView.getSelectionPhase() == ShotRoundView.POWER_SELECTION_PHASE){
            shotRoundView.setPower();
            shotRoundView.increaseSelectionPhase();
            shooterAnimation.start();
        }
    }

    private void handleMousePressedSaveRound(MouseEvent e){
        if(saveRoundView.isIndicatorVisible()){
            saveRoundView.setCoordSaveAttempt(e.getX(), e.getY());
            boolean saved = view.getLogic().isShotSaved(e.getX(), e.getY());
            saveRoundView.setShotSaved(saved);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.view.getLogic().isShotRound()){
            handleMousePressedShotRound();
        }
        else {
            handleMousePressedSaveRound(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //DO NOTHING
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //DO NOTHING
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //DO NOTHING
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //DO NOTHING
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //DO NOTHING
    }

}