package main.java;
import acm.program.ConsoleProgram;
import adalab.core.net.Request;
import adalab.core.net.SimpleClient;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acm.util.HAWTools;
public class WeChatServer {
    static SimpleServer server = new SimpleServer(new SimpleServerListener() {
        private static final String SUCCESS_MSG = "success";
        private static final String FAILURE_PREFIX = "Error: ";
        private HashMap<String,Account> accounts = new HashMap<>();
        private ArrayList<String> friendLong = new ArrayList<String>();
        private ArrayList<String> friendLongl = new ArrayList<String>();

        @Override
        public Object requestMade(Request request) {
            String cmd = request.getCommand();

            if (cmd.equals("ping")) {return "pong";}
            if (cmd.equals("addAccount")) {
                if(accounts.containsKey((request.getParam("name")))){
                    return FAILURE_PREFIX + "账户已经存在";
                } else {
                    Account account = new Account(request.getParam("name"));
                    accounts.put(request.getParam("name"),account);
                    return SUCCESS_MSG;
                }
            }
            if (cmd.equals("deleteAccount")) {
                if(accounts.containsKey((request.getParam("name")))){
                    for (Map.Entry<String, Account> stringAccountEntry : accounts.entrySet()) {
                        stringAccountEntry.getValue().deleteFriend(request.getParam("name"));
                    }
                    accounts.remove(request.getParam("name"));
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("haveAccount")) {
                if(accounts.containsKey((request.getParam("name")))){
                    return "true";
                } else {
                    return "false";
                }
            }
            if (cmd.equals("getAvatar")) {
                if(accounts.containsKey((request.getParam("name")))){
                    if (accounts.get(request.getParam("name")).getAvatat() == null){
                        return "";
                    }else {
                        return HAWTools.imageToString(accounts.get(request.getParam("name")).getAvatat());
                    }
                }else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("getStatus")) {
                if(accounts.containsKey((request.getParam("name")))){
                    if (accounts.get(request.getParam("name")).getStatus() == null){
                        return " ";
                    }else {
                        return accounts.get(request.getParam("name")).getStatus();
                    }
                }else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("getAge")) {
                if(accounts.containsKey((request.getParam("name")))){
                    if (accounts.get(request.getParam("name")).getAge() == null){
                        return " ";
                    }else {
                        return accounts.get(request.getParam("name")).getAge();
                    }
                }else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("setAvatar")) {
                if(accounts.containsKey((request.getParam("name")))){
                    accounts.get(request.getParam("name")).setAvatar(HAWTools.stringToImage(request.getParam("imageString")));
                    return SUCCESS_MSG;}else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("setStatus")) {
                if (accounts.containsKey((request.getParam("name")))) {
                    accounts.get(request.getParam("name")).setStatus(request.getParam("status"));
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("setAge")) {
                if (accounts.containsKey((request.getParam("name")))) {
                    accounts.get(request.getParam("name")).setAge(request.getParam("age"));
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
                }
            }
            if (cmd.equals("getFriends")) {
                if(accounts.get(request.getParam("name")).getFriends().isEmpty() ){
                    return "[]";
                }else{
                    String a = "";
                    for (int i = 0; i < accounts.get(request.getParam("name")).getFriends().size(); i++) {
                        a += accounts.get(request.getParam("name")).getFriends().get(i) + ", ";
                    }
                    StringBuilder str = new StringBuilder(a);
                    str.delete(str.length()-1,str.length());
                    String b = String.valueOf(str);
                    String c = b.substring(0,b.length() - 1);
                    return "["+c+"]";
                }
            }
            if (cmd.equals("addFriend")) {
                if(accounts.containsKey((request.getParam("name1")))& accounts.containsKey((request.getParam("name2")))){
                    if(request.getParam("name1").equals(request.getParam("name2"))){
                        return FAILURE_PREFIX + "无法将自己添加为好友";
                    }
                    boolean ifa = false;
                    for(int i = 0; i < accounts.get(request.getParam("name1")).getFriends().size(); i++) {
                        if(accounts.get(request.getParam("name1")).getFriends().get(i).equals(request.getParam("name2"))){
                            ifa = true;
                        }
                    }
                    if(ifa){
                        return FAILURE_PREFIX + request.getParam("name1")+"和"+request.getParam("name2")+"已经是朋友了";
                    }else {
                        accounts.get(request.getParam("name1")).addFriends(request.getParam("name2"));
                        accounts.get(request.getParam("name2")).addFriends(request.getParam("name1"));
                        return SUCCESS_MSG;
                    }
                }else {
                    return FAILURE_PREFIX + "找不到账户【" + request.getParam("name2") + "】";
                }
            }
            if (cmd.equals("findLong")) {
                friendLong= new ArrayList<String>();
                friendLongl= new ArrayList<String>();
                StringBuilder sbb = new StringBuilder(request.getParam("name1")+"->");
                if(accounts.containsKey((request.getParam("name1")))& accounts.containsKey((request.getParam("name2")))){
//                    int OOO =0;
//                    for (Map.Entry<String, Account> stringAccountEntry : accounts.entrySet()) {
//                      if(stringAccountEntry.getValue().getFriends().contains(request.getParam("name2")))  {
//                          OOO++;
//                      }
//                    }
                       accounts.get(request.getParam("name1")).findB(request.getParam("name2"),sbb,friendLong,friendLongl,accounts);
                  return friendLong.get(0);
                }
            }

            return FAILURE_PREFIX + "未知命令【" + cmd + "】";
        }
    }, 3200);
  public static void main(String[] args) {
      server.start();
  }
}
