import acm.graphics.GImage;

import java.util.LinkedHashSet;

public class Account {
    public String name;
    public String status;
    public GImage avatar;

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvatar(GImage avatat) {
        this.avatar = avatat;
    }

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
        return avatar;
    }

    public LinkedHashSet<Account> getFriends() {
        return friends;
    }
}
