package ModalsController;

import javafx.scene.control.MenuItem;

public class CustomMenuItem extends MenuItem {
    private final Object myObject;

    public CustomMenuItem(String text, Object myObject) {
        super(text);
        this.myObject = myObject;
    }

    public Object getMyObject() {
        return myObject;
    }


}
