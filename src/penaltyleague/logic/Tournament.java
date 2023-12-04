package penaltyleague.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Tournament {
    private int userTeamIndex;
    private int tournamentPhase;
    private static final int ROUND_OF_16_PHASE = 1;
    private static final int QUARTER_FINALS_PHASE = 2;
    private static final int SEMI_FINALS_PHASE = 3;

    private int[][] roundOf16;
    private int[][] quarterFinals;
    private int[][] semiFinals;
    private int[] finalMatch;

    public Tournament(int userTeamIndex){
        this.userTeamIndex = userTeamIndex;
        this.tournamentPhase = ROUND_OF_16_PHASE;
        this.roundOf16 = new int[8][2];
        this.quarterFinals = new int[4][2];
        this.semiFinals = new int[2][2];
        this.finalMatch = new int[2];

        ArrayList<Integer> idMatches = new ArrayList<Integer>();
        for (int i = 0; i <= 7; i++){
            idMatches.add(i);
        }
        Collections.shuffle(idMatches);
        for(int i = 0; i < roundOf16.length * roundOf16[0].length; i++){
            roundOf16[idMatches.get(i / 2)][i % 2] = i;
        }

        extractRoundOf16Winners();
        extractQuarterFinalsWinners();
        extractSemiFinalsWinners();
    }

    protected int getUserTeamIndex(){
        return userTeamIndex;
    }

    private int getRoundOf16OpponentTeamIndex(){
        int idMatch = 0;
        int idTeamInMatch = 0;
        for(int i = 0; i < roundOf16.length; i++){
            for(int j = 0; j < roundOf16[i].length; j++){
                if(roundOf16[i][j] == userTeamIndex){
                    idMatch = i;
                    idTeamInMatch = (j+1) % 2;
                }
            }
        }
        return roundOf16[idMatch][idTeamInMatch];
    }

    private int getQuarterFinalsOpponentTeamIndex(){
        int idMatch = 0;
        int idTeamInMatch = 0;
        for(int i = 0; i < quarterFinals.length; i++){
            for(int j = 0; j < quarterFinals[i].length; j++){
                if(quarterFinals[i][j] == userTeamIndex){
                    idMatch = i;
                    idTeamInMatch = (j+1) % 2;
                }
            }
        }
        return quarterFinals[idMatch][idTeamInMatch];
    }

    private int getSemiFinalsOpponentTeamIndex(){
        int idMatch = 0;
        int idTeamInMatch = 0;
        for(int i = 0; i < semiFinals.length; i++){
            for(int j = 0; j < semiFinals[i].length; j++){
                if(semiFinals[i][j] == userTeamIndex){
                    idMatch = i;
                    idTeamInMatch = (j+1) % 2;
                }
            }
        }
        return semiFinals[idMatch][idTeamInMatch];
    }

    private int getFinalOpponentTeamIndex(){
        int idTeamInMatch = 0;
        for(int i = 0; i < finalMatch.length; i++){
            if(finalMatch[i] == userTeamIndex){
                idTeamInMatch = (i+1) % 2;
            }
        }
        return finalMatch[idTeamInMatch];
    }

    protected int getOpponentTeamIndex(){
        int opponentTeamIndex;
        if(tournamentPhase == ROUND_OF_16_PHASE){
            opponentTeamIndex = getRoundOf16OpponentTeamIndex();
        }
        else if(tournamentPhase == QUARTER_FINALS_PHASE){
            opponentTeamIndex = getQuarterFinalsOpponentTeamIndex();
        }
        else if(tournamentPhase == SEMI_FINALS_PHASE){
            opponentTeamIndex = getSemiFinalsOpponentTeamIndex();
        }
        else {
            opponentTeamIndex = getFinalOpponentTeamIndex();
        }
        return opponentTeamIndex;
    }

    protected int getTeamIndex(int tournamentPhase, int idMatch, int idTeamInMatch){
        int teamIndex;
        if(tournamentPhase == ROUND_OF_16_PHASE){
            teamIndex = roundOf16[idMatch][idTeamInMatch];
        }
        else if(tournamentPhase == QUARTER_FINALS_PHASE){
            teamIndex = quarterFinals[idMatch][idTeamInMatch];
        }
        else if(tournamentPhase == SEMI_FINALS_PHASE){
            teamIndex = semiFinals[idMatch][idTeamInMatch];
        }
        else {
            teamIndex = finalMatch[idTeamInMatch];
        }
        return teamIndex;
    }

    protected int getTournamentPhase(){
        return tournamentPhase;
    }

    protected void increaseTournamentPhase(){
        tournamentPhase++;
    }

    protected void setGameOverPhase(){
        tournamentPhase = 0;
    }

    private void extractRoundOf16Winners(){
        int[] winners = new int[roundOf16.length];
        Random random = new Random();
        for (int i = 0; i < winners.length; i++){
            if(random.nextBoolean())
                winners[i] = 1;
            else
                winners[i] = 0;
        }

        for(int i = 0; i < roundOf16.length; i++){
            if(roundOf16[i][0] == userTeamIndex || roundOf16[i][1] == userTeamIndex){
                quarterFinals[i/2][i%2] = userTeamIndex;
            }
            else {
                quarterFinals[i/2][i%2] = roundOf16[i][winners[i]];
            }
        }
    }

    private void extractQuarterFinalsWinners(){
        int[] winners = new int[quarterFinals.length];
        Random random = new Random();
        for (int i = 0; i < winners.length; i++){
            if(random.nextBoolean())
                winners[i] = 1;
            else
                winners[i] = 0;
        }

        for(int i = 0; i < quarterFinals.length; i++){
            if(quarterFinals[i][0] == userTeamIndex || quarterFinals[i][1] == userTeamIndex){
                semiFinals[i/2][i%2] = userTeamIndex;
            }
            else {
                semiFinals[i/2][i%2] = quarterFinals[i][winners[i]];
            }
        }
    }

    private void extractSemiFinalsWinners(){
        int winner = 0;
        if(new Random().nextBoolean())
            winner = 1;

        for(int i = 0; i < semiFinals.length; i++){
            if(semiFinals[i][0] == userTeamIndex || semiFinals[i][1] == userTeamIndex){
                finalMatch[i%2] = userTeamIndex;
            }
            else {
                finalMatch[i%2] = semiFinals[i][winner];
            }
        }
    }
}