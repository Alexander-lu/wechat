import acm.graphics.GImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Account {
  private String name;
  private String status;
  private String age;
  private GImage avatar;
  private ArrayList<String> friends = new ArrayList<String>();

  public void setStatus(String status) {
    this.status = status;
  }

  public void setAge(String age) {
    this.age = age;
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

  public String getAge() {
    return age;
  }

  public String getStatus() {
    return status;
  }

  public GImage getAvatat() {
    return avatar;
  }

  public ArrayList<String> getFriends() {
    return friends;
  }

  public void addFriends(String name2) {
    friends.add(name2);
  }

  public void deleteFriend(String name2) {
    for (int i = 0; i < friends.size(); i++) {
      if (friends.get(i).equals(name2)) {
        friends.remove(i);
      }
    }
  }

  public void findB(String name2, StringBuilder sbb, ArrayList<String> friendLong, ArrayList<String> friendLongl, HashMap<String, Account> accounts) {friendLongl.add(this.getName());
    for (int i = 0; i < this.friends.size(); i++) {
      if (this.friends.get(i).equals(name2)) {
        continue;
      } else if (friendLongl.contains(this.friends.get(i))) {
        continue;
      } else {
        sbb.append(this.friends.get(i) + "->");
        accounts.get(this.friends.get(i)).findB(name2, sbb, friendLong, friendLongl, accounts);
      }
    }
    sbb.append(name2);
    friendLong.add(sbb.toString());
  }
}
