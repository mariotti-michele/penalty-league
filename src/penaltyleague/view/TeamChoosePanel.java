package penaltyleague.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class TeamChoosePanel extends JPanel implements MouseListener {

    private View view;
    private BufferedImage backgroundImg;
    private JLabel[] teamsLabel;

    private static final int TEXT_SIZE = 20;

    public TeamChoosePanel(View view) {
        super();
        this.view = view;
        setBackground(Color.BLACK);
        setLayout(new GridLayout(4,4));
        setBorder(BorderFactory.createEmptyBorder(view.getWindowHeight()/12,
                view.getWindowHeight()/12, 0, view.getWindowHeight()/12));

        loadImages();
        createLabels();
    }

    private void loadImages(){
        this.backgroundImg = null;
        try {
            this.backgroundImg = ResourceManager.loadImage("chooseTeamBackground.png");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLabels(){
        ImageIcon[] teamsIcon = new ImageIcon[view.getTeams().getNumberOfTeams()];
        teamsLabel = new JLabel[view.getTeams().getNumberOfTeams()];
        for(int i = 0; i < teamsLabel.length; i++){
            teamsIcon[i] = new ImageIcon(view.getTeams().getTeamLogo(i).getScaledInstance((int) ((view.getWindowHeight() / 6)
                            * ((double) (view.getTeams().getTeamLogo(i).getWidth()) / view.getTeams().getTeamLogo(i).getHeight())),
                    view.getWindowHeight() / 6, java.awt.Image.SCALE_SMOOTH));
            teamsLabel[i] = new JLabel(teamsIcon[i]);
            teamsLabel[i].setForeground(Color.BLACK);
            teamsLabel[i].setFont(new Font("Arial Black", Font.PLAIN, (int) (TEXT_SIZE * view.getUnitOfMeasureScreen())));

            teamsLabel[i].setText(view.getTeams().getTeamName(i).replace("_", " ").toUpperCase());
            teamsLabel[i].setHorizontalTextPosition(JLabel.CENTER);
            teamsLabel[i].setVerticalTextPosition(JLabel.BOTTOM);
            teamsLabel[i].addMouseListener(this);
            this.add(teamsLabel[i]);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)(g);

        g2d.drawImage(this.backgroundImg, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for(int i = 0; i < teamsLabel.length; i++){
            if(e.getSource() == teamsLabel[i]){
                view.getLogic().createTournament(i);
            }
        }

        view.getMainGUI().changeToTournamentBracketPanel();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //DO NOTHING
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
}