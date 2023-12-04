package penaltyleague.logic;

import penaltyleague.view.IView;

public class SaveRoundLogic extends RoundLogic{

    protected int generateShotDirection(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    protected int generateShotHeight(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    protected boolean isShotSaved(int xCoordSaveAttempt, int yCoordSaveAttempt, IView view){
        //only if is saved by the goalkeeper, not if it isn't toward the goal
        boolean saved = false;
        if(xCoordSaveAttempt >= view.getLeftBorderSaveIndicator()
                && xCoordSaveAttempt <= view.getRightBorderSaveIndicator()
                && yCoordSaveAttempt >= view.getTopBorderSaveIndicator()
                && yCoordSaveAttempt <= view.getDownBorderSaveIndicator()){
            saved = true;
        }
        return saved;
    }

    protected int getRegionSaveAttempt(int xCoordSaveAttempt, int yCoordSaveAttempt, IView view){
        return getRegion(xCoordSaveAttempt, yCoordSaveAttempt, view);
    }

}