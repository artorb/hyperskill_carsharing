package carsharing.menus;

public class MenuOption implements OptionEntity {
    private String title;
    private long option;
    private Runnable action;

    public long getOption() {
        return option;
    }

    public String getTitle() {
        return title;
    }

    public boolean isRunning() {
        return action != null;
    }

    public MenuOption() {
    }

    public MenuOption(String title, long optNumber, Runnable action) {
        this.title = title;
        this.option = optNumber;
        this.action = action;
    }

    @Override
    public void run() {
        try {
            action.run();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
