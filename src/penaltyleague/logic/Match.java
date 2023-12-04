package penaltyleague.logic;

import java.util.ArrayList;
import java.util.Random;

public class Match {
    private int opponentTeamIndex;

    private boolean shotRound;
    private ArrayList<Boolean> userScoreboard;
    private ArrayList<Boolean> opponentScoreboard;

    public Match(int opponentTeamIndex){
        this.opponentTeamIndex = opponentTeamIndex;
        this.userScoreboard = new ArrayList<Boolean>();
        this.opponentScoreboard = new ArrayList<Boolean>();

        //extract the first to shot
        this.shotRound = new Random().nextBoolean();
    }

    protected int getOpponentTeamIndex(){
        return opponentTeamIndex;
    }

    protected void changeRound(){
        if(shotRound)
            shotRound = false;
        else
            shotRound = true;
    }

    protected boolean isShotRound(){
        return shotRound;
    }

    protected void updateUserScoreboard(boolean goal){
        if(goal)
            userScoreboard.add(true);
        else
            userScoreboard.add(false);
    }

    protected void updateOpponentScoreboard(boolean goal){
        if(goal)
            opponentScoreboard.add(true);
        else
            opponentScoreboard.add(false);
    }

    protected boolean checkUserWin(){
        boolean win = false;
        if(getUserShotsCounter() <= 5 && getOpponentShotsCounter() <= 5
                && getUserScore() > getOpponentScore() + 5 - getOpponentShotsCounter())
            win = true;
        else if(getUserShotsCounter() > 5 && getUserShotsCounter() == getOpponentShotsCounter()
                && getUserScore() > getOpponentScore())
            win = true;
        return win;
    }

    protected boolean checkOpponentWin(){
        boolean win = false;
        if(getUserShotsCounter() <= 5 && getOpponentShotsCounter() <= 5
                && getOpponentScore() > getUserScore() + 5 - getUserShotsCounter())
            win = true;
        else if(getOpponentShotsCounter() > 5 && getOpponentShotsCounter() == getUserShotsCounter()
                && getOpponentScore() > getUserScore())
            win = true;
        return win;
    }

    protected ArrayList<Boolean> getUserScoreboard(){
        return userScoreboard;
    }

    protected ArrayList<Boolean> getOpponentScoreboard(){
        return opponentScoreboard;
    }

    protected int getUserScore(){
        int score = 0;
        for(int i = 0; i < userScoreboard.size(); i++){
            if(userScoreboard.get(i))
                score++;
        }
        return score;
    }

    protected int getOpponentScore(){
        int score = 0;
        for(int i = 0; i < opponentScoreboard.size(); i++){
            if(opponentScoreboard.get(i))
                score++;
        }
        return score;
    }

    protected int getUserShotsCounter(){
        return userScoreboard.size();
    }

    protected int getOpponentShotsCounter(){
        return opponentScoreboard.size();
    }
}