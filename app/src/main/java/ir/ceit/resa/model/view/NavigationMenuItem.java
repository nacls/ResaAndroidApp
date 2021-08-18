package ir.ceit.resa.model.view;

public class NavigationMenuItem {
    private int icon;
    private String title;
    private int id;

    // Constructor.
    public NavigationMenuItem(int icon, String title, int id) {
        this.icon = icon;
        this.title = title;
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
