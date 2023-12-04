package penaltyleague.logic;

import penaltyleague.view.IView;

public class ShotRoundLogic extends RoundLogic{

    private int regionSaveAttempt;
    private static final int PERFECT_SHOT_THRESHOLD = 75;
    private static final int WEAK_SHOT_THRESHOLD = 25;

    protected int generateRegionSaveAttempt(int shotDirection, int shotHeight, int power, IView view){
        //the goal is divided into 6 region
        if(isWeakShot(power)){
            regionSaveAttempt = getRegionShot(shotDirection, shotHeight, view);
        }
        else{
            regionSaveAttempt = (int) ((Math.random() * (6 - 1)) + 1);
        }
        return regionSaveAttempt;
    }

    protected int getRegionSaveAttempt(){
        return regionSaveAttempt;
    }

    private int getRegionShot(int shotDirection, int shotHeight, IView view){
        return getRegion(shotDirection, shotHeight, view);
    }

    private boolean isPerfectShot(int shotDirection, int shotHeight, int power, IView view){
        //must check before if shot is toward goal
        boolean perfectShot = false;
        if((shotDirection < view.getLeftPostCenterCoord() + view.getGoalWidth()/8
                || shotDirection > view.getLeftPostCenterCoord() + view.getGoalWidth()*7/8)
                && ((shotHeight < view.getCrossbarCenterCoord() + view.getGoalHeight()/4)
                || (shotHeight > view.getCrossbarCenterCoord() + view.getGoalHeight()*3/4))
                && power > PERFECT_SHOT_THRESHOLD){
            perfectShot = true;
        }
        return  perfectShot;
    }

    private boolean isWeakShot(int power){
        boolean weakShot = false;
        if(power < WEAK_SHOT_THRESHOLD)
            weakShot = true;
        return weakShot;
    }

    private boolean isShotSaved(int regionShot, int regionSaveAttempt, boolean perfectShot, boolean weakShot){
        boolean shotSaved = false;
        if((regionShot == regionSaveAttempt && !perfectShot) || weakShot){
            shotSaved = true;
        }
        return shotSaved;
    }


    protected boolean isGoal(int shotDirection, int shotHeight, int power, int regionSaveAttempt, IView view){
        boolean goal = false;
        if(isShotTowardGoal(shotDirection, shotHeight, view)){
            boolean perfectShot = isPerfectShot(shotDirection, shotHeight, power, view);
            boolean shotSaved = isShotSaved(getRegionShot(shotDirection, shotHeight, view),
                    regionSaveAttempt, perfectShot, isWeakShot(power));
            if(!shotSaved)
                goal = true;
        }
        return goal;
    }
}
