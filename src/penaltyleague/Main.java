package penaltyleague;

        import penaltyleague.logic.ILogic;
        import penaltyleague.logic.Logic;
        import penaltyleague.view.IView;
        import penaltyleague.view.View;

public class Main {
    private static void createLogicViewAndStartGUI() {
        ILogic logic = new Logic();
        IView view = new View();
        logic.setView(view);
        view.setLogic(logic);

        view.startGUI();
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");    //fix the blurry image icons caused by windows scaling factor
        createLogicViewAndStartGUI();
    }
}