package penaltyleague.logic;

import penaltyleague.view.IView;

public abstract class RoundLogic {

    private static final int TOP_LEFT_GOAL_REGION = 1;
    private static final int TOP_CENTER_GOAL_REGION = 2;
    private static final int TOP_RIGHT_GOAL_REGION = 3;
    private static final int DOWN_LEFT_GOAL_REGION = 4;
    private static final int DOWN_CENTER_GOAL_REGION = 5;
    private static final int DOWN_RIGHT_GOAL_REGION = 6;

    protected boolean isShotTowardGoal(int shotDirection, int shotHeight, IView view){
        boolean inside = false;
        if(shotDirection > view.getLeftPostCenterCoord() && shotDirection < view.getRightPostCenterCoord()
                && shotHeight > view.getCrossbarCenterCoord()
                && shotHeight <= view.getCrossbarCenterCoord() + view.getGoalHeight()){

            inside = true;

        }
        return inside;
    }

    protected int getRegion(int x, int y, IView view){
        /* the goal is divided into 6 region, 1 = top left, 2 = top center, 3 = top right, 4 = down left,
        5 = down center, 6 = down right; return although is outside the goal*/
        int region = 0;
        if(y < (view.getCrossbarCenterCoord() + view.getGoalHeight()/2)){   //top
            if(x < view.getLeftPostCenterCoord() + view.getGoalWidth()/3){
                region = TOP_LEFT_GOAL_REGION;
            }
            else if (x < view.getLeftPostCenterCoord() + view.getGoalWidth()*2/3){
                region = TOP_CENTER_GOAL_REGION;
            }
            else {
                region = TOP_RIGHT_GOAL_REGION;
            }
        }
        else {  //down
            if(x < view.getLeftPostCenterCoord() + view.getGoalWidth()/3){
                region = DOWN_LEFT_GOAL_REGION;
            }
            else if (x < view.getLeftPostCenterCoord() + view.getGoalWidth()*2/3){
                region = DOWN_CENTER_GOAL_REGION;
            }
            else {
                region = DOWN_RIGHT_GOAL_REGION;
            }
        }

        return region;
    }

    protected boolean checkHitLeftPostAndGoal(int shotDirection, IView view){
        boolean hitPostAndGoal = false;
        if(shotDirection > view.getLeftPostCenterCoord()
                && shotDirection < view.getLeftPostCenterCoord() + view.getPostsWidth()/2 + view.getBallDimension()/2){

            hitPostAndGoal = true;

        }
        return hitPostAndGoal;
    }

    protected boolean checkHitRightPostAndGoal(int shotDirection, IView view){
        boolean hitPostAndGoal = false;
        if(shotDirection < view.getRightPostCenterCoord()
                && shotDirection > view.getRightPostCenterCoord() - view.getPostsWidth()/2 - view.getBallDimension()/2){

            hitPostAndGoal = true;

        }
        return hitPostAndGoal;
    }

    protected boolean checkHitCrossbarAndGoal(int shotHeight, IView view){
        boolean hitCrossbarAndGoal = false;
        if(shotHeight > view.getCrossbarCenterCoord()
                && shotHeight < view.getCrossbarCenterCoord() + view.getPostsWidth()/2 + view.getBallDimension()/2){

            hitCrossbarAndGoal = true;

        }
        return hitCrossbarAndGoal;
    }

}
