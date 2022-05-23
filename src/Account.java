import acm.graphics.GImage;

import java.util.LinkedHashSet;

public class Account {
    public String name;
    public String status;
    public GImage avatar;
    public HashMap<String,Account> friends = new HashMap<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvatar(GImage avatat) {
        this.avatar = avatat;
    }


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

    public HashMap<String,Account> getFriends() {
        return friends;
    }
    public void addFriends(Account name2){
        friends.put(name2.getName(),name2);
    }
}
