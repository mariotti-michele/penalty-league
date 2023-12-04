package penaltyleague.logic;

import penaltyleague.view.IView;

import java.util.ArrayList;

public interface ILogic {

    public void setView(IView view);

    public IView getView();

    public void createTournament(int userTeamIndex);

    public void createMatch();

    public int getOpponentTeamIndex();

    public int getUserTeamIndex();

    public int getUserScore();

    public int getOpponentScore();

    public int getUserShotsCounter();

    public int getOpponentShotsCounter();

    public void updateUserScoreboard(boolean goal);

    public void updateOpponentScoreboard(boolean goal);

    public ArrayList<Boolean> getUserScoreboard();

    public ArrayList<Boolean> getOpponentScoreboard();

    public boolean isShotRound();

    public void changeRound();

    public boolean checkUserWin();

    public boolean checkOpponentWin();


    public int getTournamentPhase();

    public void increaseTournamentPhase();

    public void setGameOverPhase();

    public int getTeamIndex(int phase, int idMatch, int idTeamInMatch);


    public boolean isShotSaved(int xCoordSaveAttempt, int yCoordSaveAttempt);

    public int generateShotDirection(int min, int max);

    public int generateShotHeight(int min, int max);

    public int getRegionSaveAttempt(int xCoordSaveAttempt, int yCoordSaveAttempt);

    public boolean isShotTowardGoal(int shotDirection, int shotHeight);

    public boolean isGoal(int shotDirection, int shotHeight, int power, int regionSaveAttempt);

    public int generateRegionSaveAttempt(int shotDirection, int shotHeight, int power);

    public int getRegionSaveAttempt();

    public boolean checkHitLeftPostAndGoal(int shotDirection);

    public boolean checkHitRightPostAndGoal(int shotDirection);

    public boolean checkHitCrossbarAndGoal(int shotHeight);

}