package penaltyleague.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class StartMenuPanel extends JPanel {

    private View view;
    private BufferedImage startMenuImg;
    private BufferedImage tutorialSignImg;
    private ImageIcon playImageIcon;
    private ImageIcon soundImageIcon;
    private ImageIcon muteImageIcon;
    private ImageIcon tutorialImageIcon;
    private JLabel soundLabel;
    private boolean isMute;
    private boolean showTutorial;

    public StartMenuPanel(View view) {
        super();
        this.view = view;
        setBackground(Color.BLACK);
        setLayout(new GridLayout(1,0));
        setBorder(BorderFactory.createEmptyBorder(view.getWindowHeight()/7, view.getWindowWidth()/4,
                0, view.getWindowWidth()/4));

        isMute = false;
        showTutorial = false;

        loadImages();

        JLabel playLabel = new JLabel(playImageIcon);
        soundLabel = new JLabel(soundImageIcon);
        JLabel tutorialLabel = new JLabel(tutorialImageIcon);

        soundLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handleSoundLabel();
            }
        });
        playLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handlePlayLabel();
            }
        });
        tutorialLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handleTutorialLabel();
            }
        });

        this.add(tutorialLabel);
        this.add(playLabel);
        this.add(soundLabel);
    }

    private void loadImages(){
        this.startMenuImg = null;
        this.playImageIcon = null;
        this.soundImageIcon = null;
        this.tutorialImageIcon = null;
        this.tutorialSignImg = null;
        try{
            this.startMenuImg = ResourceManager.loadImage("startMenu.png");
            BufferedImage playImg = ResourceManager.loadImage("playButton.png");
            BufferedImage soundImg = ResourceManager.loadImage("soundButton.png");
            BufferedImage muteImg = ResourceManager.loadImage("muteButton.png");
            BufferedImage tutorialImg = ResourceManager.loadImage("tutorialButton.png");
            this.tutorialSignImg = ResourceManager.loadImage("tutorialSign.png");
            int width = view.getWindowHeight()/8;
            this.playImageIcon = new ImageIcon(playImg.getScaledInstance(2*width,
                    2*width * playImg.getHeight()/playImg.getWidth(), java.awt.Image.SCALE_SMOOTH));
            this.soundImageIcon = new ImageIcon(soundImg.getScaledInstance(width,
                    width * soundImg.getHeight()/soundImg.getWidth(), java.awt.Image.SCALE_SMOOTH));
            this.muteImageIcon = new ImageIcon(muteImg.getScaledInstance(width,
                    width * muteImg.getHeight()/muteImg.getWidth(), java.awt.Image.SCALE_SMOOTH));
            this.tutorialImageIcon = new ImageIcon(tutorialImg.getScaledInstance(width,
                    width * tutorialImg.getHeight()/tutorialImg.getWidth(), java.awt.Image.SCALE_SMOOTH));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handlePlayLabel(){
        view.getMainGUI().changeToTeamChoosePanel();
        view.getSound().playAnthemSound();
    }

    private void handleSoundLabel(){
        if(!isMute){
            soundLabel.setIcon(muteImageIcon);
            isMute = true;
        }

        else {
            soundLabel.setIcon(soundImageIcon);
            isMute = false;
        }

        view.getSound().changeMute();
    }

    private void handleTutorialLabel(){
        showTutorial = !showTutorial;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)(g);

        g2d.drawImage(this.startMenuImg, 0, 0, this.getWidth(), this.getHeight(), null);

        if(showTutorial)
            g2d.drawImage(tutorialSignImg, view.getWindowHeight()/30, view.getWindowHeight()/30,
                    view.getWindowHeight()/3 * tutorialSignImg.getWidth()/tutorialSignImg.getHeight(),
                    view.getWindowHeight()/3, null);
    }
}
