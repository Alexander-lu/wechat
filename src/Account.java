import acm.graphics.GImage;

import java.util.LinkedHashSet;

public class Account {
    public String name;
    public String status;
    public GImage avatat;

    public LinkedHashSet<Account> friends;
  public Account(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public GImage getAvatat() {
        return avatat;
    }

    public LinkedHashSet<Account> getFriends() {
        return friends;
    }
}
