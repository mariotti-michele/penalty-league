package penaltyleague.logic;

import penaltyleague.view.IView;

import java.util.ArrayList;

public class Logic implements ILogic{
    private IView view;

    private Tournament tournament;
    private Match match;
    private SaveRoundLogic saveRoundLogic;
    private ShotRoundLogic shotRoundLogic;

    public Logic(){
        this.view = null;
        this.tournament = null;
        this.match = null;
        this.saveRoundLogic = new SaveRoundLogic();
        this.shotRoundLogic = new ShotRoundLogic();
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public IView getView() {
        return this.view;
    }

    @Override
    public void createTournament(int userTeamIndex) {
        this.tournament = new Tournament(userTeamIndex);
    }

    @Override
    public void createMatch() {
        this.match = new Match(tournament.getOpponentTeamIndex());
    }

    @Override
    public int getUserTeamIndex() {
        return tournament.getUserTeamIndex();
    }

    @Override
    public int getOpponentTeamIndex() {
        return match.getOpponentTeamIndex();
    }

    @Override
    public int getUserScore() {
        return match.getUserScore();
    }

    @Override
    public int getOpponentScore() {
        return match.getOpponentScore();
    }

    @Override
    public int getUserShotsCounter() {
        return match.getUserShotsCounter();
    }

    @Override
    public int getOpponentShotsCounter() {
        return match.getOpponentShotsCounter();
    }

    @Override
    public void updateUserScoreboard(boolean goal) {
        match.updateUserScoreboard(goal);
    }

    @Override
    public void updateOpponentScoreboard(boolean goal) {
        match.updateOpponentScoreboard(goal);
    }

    @Override
    public ArrayList<Boolean> getUserScoreboard() {
        return match.getUserScoreboard();
    }

    @Override
    public ArrayList<Boolean> getOpponentScoreboard() {
        return match.getOpponentScoreboard();
    }

    @Override
    public boolean isShotRound() {
        return match.isShotRound();
    }

    @Override
    public void changeRound() {
        match.changeRound();
    }

    @Override
    public boolean checkUserWin() {
        return match.checkUserWin();
    }

    @Override
    public boolean checkOpponentWin() {
        return match.checkOpponentWin();
    }

    @Override
    public int getTournamentPhase() {
        return tournament.getTournamentPhase();
    }

    @Override
    public void increaseTournamentPhase() {
        tournament.increaseTournamentPhase();
    }

    @Override
    public void setGameOverPhase() {
        tournament.setGameOverPhase();
    }

    @Override
    public int getTeamIndex(int tournamentPhase, int idMatch, int idTeamInMatch) {
        return tournament.getTeamIndex(tournamentPhase, idMatch, idTeamInMatch);
    }

    @Override
    public boolean isShotSaved(int xCoordSaveAttempt, int yCoordSaveAttempt) {
        return saveRoundLogic.isShotSaved(xCoordSaveAttempt, yCoordSaveAttempt, view);
    }

    @Override
    public int generateShotDirection(int min, int max) {
        return saveRoundLogic.generateShotDirection(min, max);
    }

    @Override
    public int generateShotHeight(int min, int max) {
        return saveRoundLogic.generateShotHeight(min, max);
    }

    @Override
    public int getRegionSaveAttempt(int xCoordSaveAttempt, int yCoordSaveAttempt) {
        return saveRoundLogic.getRegionSaveAttempt(xCoordSaveAttempt, yCoordSaveAttempt, view);
    }

    @Override
    public boolean isShotTowardGoal(int shotDirection, int shotHeight) {
        boolean inside;
        if(isShotRound()){
            inside = shotRoundLogic.isShotTowardGoal(shotDirection, shotHeight, view);
        }
        else {
            inside = saveRoundLogic.isShotTowardGoal(shotDirection, shotHeight, view);
        }
        return inside;
    }

    @Override
    public boolean isGoal(int shotDirection, int shotHeight, int power, int regionSaveAttempt) {
        return shotRoundLogic.isGoal(shotDirection, shotHeight, power, regionSaveAttempt, view);
    }

    @Override
    public int generateRegionSaveAttempt(int shotDirection, int shotHeight, int power) {
        return shotRoundLogic.generateRegionSaveAttempt(shotDirection, shotHeight, power, view);
    }

    @Override
    public int getRegionSaveAttempt() {
        return shotRoundLogic.getRegionSaveAttempt();
    }

    @Override
    public boolean checkHitLeftPostAndGoal(int shotDirection) {
        boolean hitPostAndGoal;
        if(isShotRound()){
            hitPostAndGoal = shotRoundLogic.checkHitLeftPostAndGoal(shotDirection, view);
        }
        else {
            hitPostAndGoal = saveRoundLogic.checkHitLeftPostAndGoal(shotDirection, view);
        }
        return hitPostAndGoal;
    }

    @Override
    public boolean checkHitRightPostAndGoal(int shotDirection) {
        boolean hitPostAndGoal;
        if(isShotRound()){
            hitPostAndGoal = shotRoundLogic.checkHitRightPostAndGoal(shotDirection, view);
        }
        else {
            hitPostAndGoal = saveRoundLogic.checkHitRightPostAndGoal(shotDirection, view);
        }
        return hitPostAndGoal;
    }

    @Override
    public boolean checkHitCrossbarAndGoal(int shotHeight) {
        boolean hitCrossbarAndGoal;
        if(isShotRound()){
            hitCrossbarAndGoal = shotRoundLogic.checkHitCrossbarAndGoal(shotHeight, view);
        }
        else {
            hitCrossbarAndGoal = saveRoundLogic.checkHitCrossbarAndGoal(shotHeight, view);
        }
        return hitCrossbarAndGoal;
    }

}
