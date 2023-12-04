package penaltyleague.view;

import penaltyleague.logic.ILogic;

public interface IView {

    public void setLogic (ILogic logic);

    public ILogic getLogic();

    public void startGUI();


    public int getLeftPostCenterCoord();

    public int getRightPostCenterCoord();

    public int getCrossbarCenterCoord();

    public int getPostsWidth();

    public int getGoalHeight();

    public int getGoalWidth();

    public int getBallDimension();

    public int getLeftBorderSaveIndicator();

    public int getRightBorderSaveIndicator();

    public int getTopBorderSaveIndicator();

    public int getDownBorderSaveIndicator();

}