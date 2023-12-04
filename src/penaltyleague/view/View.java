package penaltyleague.view;

import penaltyleague.logic.ILogic;

public class View implements IView{
    private ILogic logic;

    private MainGUI mainGUI;
    private StartMenuPanel startMenuPanel;
    private TeamChoosePanel teamChoosePanel;
    private TournamentBracketPanel tournamentBracketPanel;
    private GamePanel gamePanel;

    private Teams teams;

    private Sound sound;

    private final static int SCREEN_WIDTH_REFERENCE_MEASURE = 1920;

    public View(){
        this.logic = null;
        this.mainGUI = null;
        this.startMenuPanel = null;
        this.teamChoosePanel = null;
        this.tournamentBracketPanel = null;
        this.gamePanel = null;
        this.teams = null;
        this.sound = null;
    }


    protected MainGUI getMainGUI() {
        return this.mainGUI;
    }

    protected int getWindowWidth(){
        return mainGUI.getWidth();
    }

    protected int getWindowHeight(){
        return mainGUI.getHeight();
    }

    private void createMainGUI() {
        this.mainGUI = new MainGUI(this);
        this.createTeams();
        this.createSound();
        this.createPanels();
    }


    private void createPanels() {
        this.startMenuPanel = new StartMenuPanel(this);
        this.teamChoosePanel = new TeamChoosePanel(this);
        this.tournamentBracketPanel = new TournamentBracketPanel(this);
        this.gamePanel = new GamePanel(this);
    }

    protected StartMenuPanel getStartMenuPanel() {
        return startMenuPanel;
    }

    protected TeamChoosePanel getTeamChoosePanel() {
        return teamChoosePanel;
    }

    protected TournamentBracketPanel getTournamentBracketPanel() {
        return tournamentBracketPanel;
    }

    protected GamePanel getGamePanel() {
        return gamePanel;
    }


    private void createTeams() {
        this.teams = new Teams();
    }

    protected Teams getTeams() {
        return teams;
    }

    private void createSound(){
        try{
            this.sound = new Sound();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    protected Sound getSound(){
        return sound;
    }

    protected double getUnitOfMeasureScreen(){
        return (double)(mainGUI.getWidth()) / SCREEN_WIDTH_REFERENCE_MEASURE;
    }


    @Override
    public void setLogic(ILogic logic) {
        this.logic = logic;
    }

    @Override
    public ILogic getLogic() {
        return this.logic;
    }

    @Override
    public void startGUI() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            createMainGUI();
            mainGUI.setVisible(true);
            mainGUI.changeToStartMenuPanel();
        });
    }

    @Override
    public int getLeftPostCenterCoord() {
        return gamePanel.getLeftPostCenterCoord();
    }

    @Override
    public int getRightPostCenterCoord() {
        return gamePanel.getRightPostCenterCoord();
    }

    @Override
    public int getCrossbarCenterCoord() {
        return gamePanel.getCrossbarCenterCoord();
    }

    @Override
    public int getPostsWidth() {
        return gamePanel.getPostsWidth();
    }

    @Override
    public int getGoalHeight() {
        return gamePanel.getGoalHeight();
    }

    @Override
    public int getGoalWidth() {
        return gamePanel.getGoalWidth();
    }

    @Override
    public int getBallDimension() {
        return gamePanel.getBall().getBallDimension();
    }

    @Override
    public int getLeftBorderSaveIndicator() {
        return gamePanel.getSaveRoundView().getLeftBorderIndicator();
    }

    @Override
    public int getRightBorderSaveIndicator() {
        return gamePanel.getSaveRoundView().getRightBorderIndicator();
    }

    @Override
    public int getTopBorderSaveIndicator() {
        return gamePanel.getSaveRoundView().getTopBorderIndicator();
    }

    @Override
    public int getDownBorderSaveIndicator() {
        return gamePanel.getSaveRoundView().getDownBorderIndicator();
    }

}
