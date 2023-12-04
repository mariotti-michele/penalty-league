package penaltyleague.view;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGUI extends JFrame {
    private View view;
    public MainGUI(View view) {
        super("Penalty League");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (view.getSound() != null)
                    view.getSound().close();
                System.exit(0);
            }
        });

        setMinimumSize(new Dimension(1024, 576));
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(screenDimension.width, screenDimension.width * 9 / 16));
        setResizable(false);
        setLocation(0, 0);

        this.view = view;

        pack();
    }

    protected void changeToGamePanel(){
        this.getContentPane().removeAll();
        view.getLogic().createMatch();
        this.getContentPane().add(view.getGamePanel(), BorderLayout.CENTER);
        view.getSound().playCheeringSound();
        validate();
        repaint();
    }

    protected void changeToTeamChoosePanel(){
        this.getContentPane().removeAll();
        this.getContentPane().add(view.getTeamChoosePanel(), BorderLayout.CENTER);
        validate();
        repaint();
    }

    protected void changeToTournamentBracketPanel(){
        this.getContentPane().removeAll();
        this.getContentPane().add(view.getTournamentBracketPanel(), BorderLayout.CENTER);
        if(view.getLogic().getTournamentPhase() == TournamentBracketPanel.WINNER_PHASE)
            view.getSound().playWinnerSound();
        else if(view.getLogic().getTournamentPhase() == TournamentBracketPanel.GAME_OVER_PHASE)
            view.getSound().playGameOverSound();
        validate();
        repaint();
    }

    protected void changeToStartMenuPanel(){
        this.getContentPane().removeAll();
        this.getContentPane().add(view.getStartMenuPanel(), BorderLayout.CENTER);
        validate();
        repaint();
    }
}
