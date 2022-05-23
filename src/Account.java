import acm.graphics.GImage;

import java.util.LinkedHashSet;

public class Account {
    public String name;
    public String status;
    public GImage avatat;

    public LinkedHashSet friends;

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public GImage getAvatat() {
        return avatat;
    }
}
