import acm.graphics.GImage;
import java.util.ArrayList;

public class Account {
    public String name;
    public String status;
    public GImage avatar;
    public ArrayList<Account> friends = new ArrayList<Account>();

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

    public ArrayList<Account> getFriends() {
        return friends;
    }
    public void addFriends(Account name2){
        friends.add(name2);
    }
    public void deleteFriend(String name2){
        for (int i = 0; i < friends.size(); i++) {
         if(friends.get(i).getName().equals(name2))  {
             friends.remove(i);
         }
        }
    }
}
