package view;

public abstract class View implements IView {

    protected String input;
    protected ViewManager manager;

    public View(ViewManager managerView) {
        input = new String();
        manager = managerView;
    }

    public ViewManager getManager() {
        return manager;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public abstract View run();

}