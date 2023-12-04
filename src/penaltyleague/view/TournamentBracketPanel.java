package penaltyleague.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TournamentBracketPanel extends JPanel {

    private View view;
    private BufferedImage backgroundImg;
    private BufferedImage starImg;
    private BufferedImage trophyImg;
    private BufferedImage winnerSign;
    private BufferedImage gameOverSign;
    private ImageIcon playImageIcon;

    private Rectangle2D.Double rect;

    private static final int QUARTER_FINALS_PHASE = 2;
    private static final int SEMI_FINALS_PHASE = 3;
    private static final int FINAL_PHASE = 4;
    protected static final int WINNER_PHASE = 5;
    protected static final int GAME_OVER_PHASE = 0;

    private static final Color LIGHT_RED_COLOR = new Color(220, 0, 0);
    private static final Color DARK_RED_COLOR = new Color(150, 0, 0);

    private static final int TEXT_SIZE = 20;

    public TournamentBracketPanel (View view) {
        super();
        this.view = view;
        setBackground(Color.BLACK);
        setLayout(null);

        loadImages();

        JLabel playLabel = new JLabel(playImageIcon);
        playLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handlePlayLabel();
            }
        });
        Dimension size = playLabel.getPreferredSize();
        playLabel.setBounds(view.getWindowWidth()/2 - size.width/2,
                view.getWindowHeight() - size.height * 2, size.width, size.height);
        this.add(playLabel);

        this.rect = new Rectangle2D.Double(0, 0, view.getWindowWidth()/7.5, view.getWindowHeight()/11);
    }

    private void loadImages(){
        this.backgroundImg = null;
        this.starImg = null;
        this.trophyImg = null;
        this.playImageIcon = null;
        this.winnerSign = null;
        this.gameOverSign = null;
        try {
            this.backgroundImg = ResourceManager.loadImage("menu.png");
            this.starImg = ResourceManager.loadImage("star.png");
            this.trophyImg = ResourceManager.loadImage("trophy.png");
            this.winnerSign = ResourceManager.loadImage("winnerSign.png");
            this.gameOverSign = ResourceManager.loadImage("gameOverSign.png");

            this.playImageIcon = new ImageIcon(ResourceManager.loadImage("playButton.png").getScaledInstance(
                    view.getWindowHeight()/10, view.getWindowHeight()/10, java.awt.Image.SCALE_SMOOTH));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePlayLabel(){
        if(view.getLogic().getTournamentPhase() == WINNER_PHASE || view.getLogic().getTournamentPhase() == GAME_OVER_PHASE){
            view.getMainGUI().changeToStartMenuPanel();
        }
        else {
            view.getMainGUI().changeToGamePanel();
        }
    }

    private void drawUserTeamText(Graphics2D g2d, int boundRect, int xCoord, int yCoord, int logoWidth) {
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.WHITE);
        FontMetrics metrics = g2d.getFontMetrics();

        g2d.drawString("YOUR TEAM", xCoord + logoWidth + boundRect + boundRect/6,
                (int) (yCoord + rect.height/2 + metrics.getHeight()/4));

        g2d.setColor(oldColor);
    }

    private void drawRoundOf16LeftSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = boundRect;
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(1, i/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(1, i/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(1, i/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawRoundOf16RightSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = (int) (this.getWidth() - boundRect - rect.width);
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(1, (i+8)/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(1, (i+8)/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(1, (i+8)/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawRoundOf16(Graphics2D g2d, int logoHeight, int boundRect, int starDimension){
        for(int i = 0; i < 8; i++){
            //set rectangle color
            if(i < 2){
                g2d.setColor(LIGHT_RED_COLOR);
            }
            else if(i < 4){
                g2d.setColor(DARK_RED_COLOR);
            }
            else if(i < 6){
                g2d.setColor(LIGHT_RED_COLOR);
            }
            else {
                g2d.setColor(DARK_RED_COLOR);
            }

            int yCoord = (int)(boundRect + boundRect * i + rect.height * i);

            //left side
            drawRoundOf16LeftSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);

            //right side
            drawRoundOf16RightSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);
        }
    }

    private void drawQuarterFinalsLeftSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = (int) (boundRect*2 + rect.width);
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(2, i/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(2, i/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(2, i/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawQuarterFinalsRightSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = (int) (this.getWidth() - 2*(boundRect + rect.width));
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(2, (i+4)/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(2, (i+4)/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(2, (i+4)/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawQuarterFinals(Graphics2D g2d, int logoHeight, int boundRect, int starDimension){
        for(int i = 0; i < 4; i++){
            //set rectangle color
            if(i < 2){
                g2d.setColor(LIGHT_RED_COLOR);
            }
            else {
                g2d.setColor(DARK_RED_COLOR);
            }

            int yCoord = (int) (rect.height/2 + boundRect*3/2 + 2*(rect.height + boundRect) * i);

            //leftSide
            drawQuarterFinalsLeftSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);

            //rightSide
            drawQuarterFinalsRightSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);
        }
    }

    private void drawSemiFinalsLeftSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //set rectangle color
        g2d.setColor(LIGHT_RED_COLOR);

        //draw rectangle
        int xCoord = (int) (boundRect*3 + rect.width*2);
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(3, i/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(3, i/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(3, i/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawSemiFinalsRightSide(Graphics2D g2d, int i, int boundRect, int logoHeight, int yCoord, int starDimension){
        //set rectangle color
        g2d.setColor(DARK_RED_COLOR);

        //draw rectangle
        int xCoord = (int) (this.getWidth() - 3*(boundRect + rect.width));
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(3, (i+2)/2, i%2));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(3, (i+2)/2, i%2) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(3, (i+2)/2, i%2)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawSemiFinals(Graphics2D g2d, int logoHeight, int boundRect, int starDimension){
        for(int i = 0; i < 2; i++){
            int yCoord = (int) (rect.height*3/2 + boundRect*5/2 + 4*(rect.height + boundRect) * i);

            //left side
            drawSemiFinalsLeftSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);

            //right side
            drawSemiFinalsRightSide(g2d, i, boundRect, logoHeight, yCoord, starDimension);
        }
    }

    private void drawFinalLeftSide(Graphics2D g2d, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = (int) (boundRect*3 + rect.width*2 + rect.width/3);
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(4, 0, 0));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team text
        if(view.getLogic().getTeamIndex(4, 0, 0) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic()
                    .getTeamIndex(4, 0, 0)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawFinalRightSide(Graphics2D g2d, int boundRect, int logoHeight, int yCoord, int starDimension){
        //draw rectangle
        int xCoord = (int) (this.getWidth() - 3*(boundRect + rect.width) - rect.width/3);
        rect.setRect(xCoord, yCoord, rect.width, rect.height);
        g2d.fill(rect);
        g2d.draw(rect);

        //draw team logo
        BufferedImage teamLogo = view.getTeams().getTeamLogo(view.getLogic().getTeamIndex(4, 0, 1));
        int logoWidth = logoHeight * teamLogo.getWidth()/teamLogo.getHeight();
        g2d.drawImage(teamLogo, xCoord + boundRect/2, yCoord + boundRect/2, logoWidth, logoHeight, null);

        //draw stars or user team logo
        if(view.getLogic().getTeamIndex(4, 0, 1) != view.getLogic().getUserTeamIndex()){

            for(int j = 0; j < view.getTeams().getTeamRating(view.getLogic().getTeamIndex(4, 0, 1)); j++){
                g2d.drawImage(starImg, xCoord + logoWidth + boundRect + boundRect/6*(j+1) + j*starDimension,
                        (int) (yCoord + rect.height/2 - starDimension/2), starDimension, starDimension, null);
            }

        }
        else {
            drawUserTeamText(g2d, boundRect, xCoord, yCoord, logoWidth);
        }
    }

    private void drawFinal(Graphics2D g2d, int logoHeight, int boundRect, int starDimension){
        //set rectangles color
        g2d.setColor(LIGHT_RED_COLOR);

        int yCoord = (int) (rect.height*7/2 + boundRect*9/2);

        //left side
        drawFinalLeftSide(g2d, boundRect, logoHeight, yCoord, starDimension);

        //right side
        drawFinalRightSide(g2d, boundRect, logoHeight, yCoord, starDimension);
    }

    private void drawGameOver(Graphics2D g2d){
        int signWidth = this.getWidth()/2;
        int signHeight = signWidth * gameOverSign.getHeight()/gameOverSign.getWidth();
        g2d.drawImage(gameOverSign, this.getWidth()/2 - signWidth/2, this.getHeight()/3 - signHeight/2,
                signWidth, signHeight, null);
    }

    private void drawWinner(Graphics2D g2d){
        int signWidth = this.getWidth()/2;
        int signHeight = signWidth * winnerSign.getHeight()/winnerSign.getWidth();
        g2d.drawImage(winnerSign, this.getWidth()/2 - signWidth/2, this.getHeight()/3 - signHeight/2,
                signWidth, signHeight, null);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)(g);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial Black", Font.PLAIN, (int) (TEXT_SIZE * view.getUnitOfMeasureScreen())));

        g2d.drawImage(this.backgroundImg, 0, 0, this.getWidth(), this.getHeight(), null);

        if(view.getLogic().getTournamentPhase() > GAME_OVER_PHASE && view.getLogic().getTournamentPhase() < WINNER_PHASE) {
            int trophyImgWidth = this.getWidth()/20;
            int trophyImgHeight = trophyImgWidth * trophyImg.getHeight()/trophyImg.getWidth();
            g2d.drawImage(this.trophyImg, this.getWidth()/2 - trophyImgWidth/2,
                    this.getHeight()/2 - trophyImgHeight * 3/2, trophyImgWidth,  trophyImgHeight, null);

            int boundRect = this.getHeight()/50;
            int logoHeight = (int) (rect.height - boundRect);
            int starDimension = (int) (rect.height - 3.5*boundRect);

            drawRoundOf16(g2d, logoHeight, boundRect, starDimension);
            if(view.getLogic().getTournamentPhase() >= QUARTER_FINALS_PHASE){
                drawQuarterFinals(g2d, logoHeight, boundRect, starDimension);
            }
            if(view.getLogic().getTournamentPhase() >= SEMI_FINALS_PHASE){
                drawSemiFinals(g2d, logoHeight, boundRect, starDimension);
            }
            if(view.getLogic().getTournamentPhase() >= FINAL_PHASE){
                drawFinal(g2d, logoHeight, boundRect, starDimension);
            }
        }
        else if(view.getLogic().getTournamentPhase() == GAME_OVER_PHASE){
            drawGameOver(g2d);
        }
        else {
            drawWinner(g2d);
        }
    }
}