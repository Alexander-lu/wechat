package main.java;
import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.HAWTools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeChatClient extends GraphicsProgram {
  JPanel centerPanel;
  JPanel northPanel;
  JPanel westPanel;
  JPanel eastPanel;
  JLabel yourNameLabel;

  JLabel bottomLabel;
    JLabel nameJLabel;
  JLabel statusJLabel;
  ImageIcon pictureL;
  JLabel picc;
  JLabel friend;
  JLabel ageJLabel;

  JButton clearButton;
  JButton queryButton;
  JButton nameJButton;
  JButton ageJButton;
  JButton statusJButton;
  JButton pictureJButton;
  JButton friendsJButton;
    JButton findLongJButton;

  JTextField nameTextField;
  JTextField updateStatus;
  JTextField updatePicture;
  JTextField updateFriends;
  JTextField updateage;

  private Map<String, String> map = null; // 客户端请求参数
  public static final String SUCCESS_MSG = "success";
  private JPanel southPanel; // 账户管理
  Font font = new Font("宋体", Font.PLAIN, 25);
  Font font1 = new Font("宋体", Font.PLAIN + Font.BOLD, 25);

  public void init() {
    setTitle("朋友O客户端");
    northPanel = getRegionPanel(NORTH);
    westPanel = getRegionPanel(WEST);
    eastPanel = getRegionPanel(EAST);
    southPanel = getRegionPanel(SOUTH);
    centerPanel = getRegionPanel(CENTER);
    centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    nameJLabel = new JLabel("姓名");

    northPanel.add(nameJLabel);
    nameTextField = new JTextField(10);
    northPanel.add(nameTextField);

    nameJButton = new JButton("添加账户");
    northPanel.add(nameJButton);
    clearButton = new JButton("删除账户");
    northPanel.add(clearButton);
    queryButton = new JButton("查询账户");
    northPanel.add(queryButton);

    updateStatus = new JTextField(10);
    statusJButton = new JButton("更新个性签名");
    updatePicture = new JTextField(10);
    pictureJButton = new JButton("更新图片");
    updateFriends = new JTextField(10);
    friendsJButton = new JButton("添加好友");
      findLongJButton = new JButton("最长朋友链");
    updateage = new JTextField(10);
    ageJButton = new JButton("更新年龄");
    westPanel.add(updateStatus);
    westPanel.add(statusJButton);
    westPanel.add(updatePicture);
    westPanel.add(pictureJButton);
    westPanel.add(updateFriends);
    westPanel.add(friendsJButton);
     westPanel.add(findLongJButton);
    westPanel.add(updateage);
    westPanel.add(ageJButton);

    friend = new JLabel("好友列表");
    friend.setFont(font);
    eastPanel.add(friend);

    statusJLabel = new JLabel();
      ageJLabel = new JLabel();
    yourNameLabel = new JLabel();
    pictureL = new ImageIcon();
    picc = new JLabel();
    map = new HashMap<>();
  }

  public void run() {
    showAccountPanel();
  }

  public void showAccountPanel() {
    bottomLabel = new JLabel("", JLabel.CENTER);
    bottomLabel.setForeground(Color.BLACK);
    bottomLabel.setFont(font);
    statusJLabel.setFont(font1);
    ageJLabel.setFont(font1);
    yourNameLabel.setFont(font1);
    southPanel.setLayout(new BorderLayout(0, 5));
    southPanel.add(bottomLabel);
    addAccount();
    setStatus();
    addFriend();
    setAge();
    setAvatar();
    getAccount();
    findLong();
    deleteAccount();
  }
  // 添加账户
  public void addAccount() {
    nameJButton.addActionListener(
        e -> {
          String out;
          String name = nameTextField.getText().trim();
          if ("".equals(name)) {
            out = "你输入的用户名为空";
          } else if (name.contains(",") || name.contains(" ")) {
            out = "你输入的用户名含有非法字符";
          } else {
            map.clear();
            map.put("name", name);
            String response = WeChatClientUtil.makeRequest("addAccount", map);
            if (SUCCESS_MSG.equals(response))
            {
              GImage image = new GImage(new ImageIcon("src/main/resources/no.jpg").getImage());
              String imageString = HAWTools.imageToString(image);
              map.put("imageString", imageString);
              WeChatClientUtil.makeRequest("setAvatar", map);
              updateJpanel(name);
              out = "注册成功";
            } else
            {out = response;}
          }
          bottomLabel.setText(out);
        });
  }
  // 添加状态
  public void setStatus() {
    statusJButton.addActionListener(
        e -> {
          String out;
          String status = updateStatus.getText().trim();
          String name = nameTextField.getText().trim();
          if ("".equals(status)) {
            out = "你输入的个性签名为空";
          } else {
            map.clear();
            map.put("name", name);
            map.put("status", status);
            String response = WeChatClientUtil.makeRequest("setStatus", map);
            if (SUCCESS_MSG.equals(response)) {
                updateJpanel(name);
              out = "个性签已名更新为" + status;
            } else {
              out = response;
            }
          }
          bottomLabel.setText(out);
        });
  }
  // 添加年龄
  public void setAge() {
    ageJButton.addActionListener(
        e -> {
          String out;
          String age = updateage.getText().trim();
          String name = nameTextField.getText().trim();
          if ("".equals(age)) {
            out = "你输入的年龄为空";
          } else {
            map.clear();
            map.put("name", name);
            map.put("age", age);
            String response = WeChatClientUtil.makeRequest("setAge", map);
            if (SUCCESS_MSG.equals(response)) {
                updateJpanel(name);
              out = "年龄已更新为" + age;
            } else {
              out = response;
            }
          }
          bottomLabel.setText(out);
        });
  }
  // 添加好友
  public void addFriend() {
    friendsJButton.addActionListener(
        e -> {
          String out;
          String name1 = nameTextField.getText().trim();
          String name2 = updateFriends.getText().trim();
          if ("".equals(name2) || name2.contains(",")) {
            out = "你输入的用户名不合法";
          } else {
            map.clear();
            map.put("name1", name1);
            map.put("name2", name2);
            String response = WeChatClientUtil.makeRequest("addFriend", map);
            if (SUCCESS_MSG.equals(response)) {
                map.clear();
                map.put("name", name1);
                updateJpanel(name1);
              out = "好友添加成功";
            } else {
              out = response;
            }
          }
          bottomLabel.setText(out);
        });
  }
  // 更新照片
  public void setAvatar() {
    pictureJButton.addActionListener(
        e -> {
          String name = nameTextField.getText().trim();
          String imageUrl = updatePicture.getText();
          boolean ifexist = true;
          try{
              GImage image = new GImage(new ImageIcon(imageUrl).getImage());
              String imageString = HAWTools.imageToString(image);
          }catch (Exception ex) {
              ifexist=false;
          }
          if(ifexist){
              GImage image = new GImage(new ImageIcon(imageUrl).getImage());
              String imageString = HAWTools.imageToString(image);
              map.clear();
              map.put("name", name);
              map.put("imageString", imageString);
              String response = WeChatClientUtil.makeRequest("setAvatar", map);
              updateJpanel(name);
              bottomLabel.setText(response);
          }else {
              bottomLabel.setText("打开图片失败");
          }
        });
  }

  // 删除账户
  public void deleteAccount() {
    clearButton.addActionListener(
        e -> {
          String name = nameTextField.getText().trim();
          if ("".equals(name)) {
              bottomLabel.setText("你输入的用户名为空");
          } else {
            map.clear();
            map.put("name", name);
            String response = WeChatClientUtil.makeRequest("deleteAccount", map);
            if (SUCCESS_MSG.equals(response))
            {
                bottomLabel.setText(response);
            } else
            {
                bottomLabel.setText(response);
            }
          }
        });
  }

  // 查询用户
  public void getAccount() {
    queryButton.addActionListener(
        e -> {
          String name = nameTextField.getText().trim();
          if ("".equals(name) || name.contains(",")) {
              bottomLabel.setText("你输入的用户名不合法");
          } else {
            map.clear();
            map.put("name", name);
            String response = WeChatClientUtil.makeRequest("haveAccount", map);
            if ("true".equals(response)) {
                bottomLabel.setText(response);
                updateJpanel(name);
            } else {
                bottomLabel.setText("用户不存在");
            }
          }
        });
  }
    // 添加好友
    public void findLong() {
        findLongJButton.addActionListener(
                e -> {
                    bottomLabel.setText(" ");
                    String name1 = nameTextField.getText().trim();
                    String name2 = updateFriends.getText().trim();
                    if ("".equals(name2) || name2.contains(",")) {
                        bottomLabel.setText("用户名不合法");
                    } else {
                        map.clear();
                        map.put("name1", name1);
                        map.put("name2", name2);
                        String response = WeChatClientUtil.makeRequest("findLong", map);
                        bottomLabel.setText(response);
                    }
                });
    }
    public void updateJpanel(String name){
        String avatar = WeChatClientUtil.makeRequest("getAvatar", map);
        String statusNew = WeChatClientUtil.makeRequest("getStatus", map);
        String ageNew = WeChatClientUtil.makeRequest("getAge", map);
        String friends = WeChatClientUtil.makeRequest("getFriends", map);
        yourNameLabel.setText("<html><body>"+"姓名："+"<br>"+name+"<body></html>");
        statusJLabel.setText("<html><body>"+"个性签名："+"<br>"+statusNew+"<body></html>");
        friend.setText("<html><body>"+"好友列表："+"<br>"+friends+"<body></html>");
        ageJLabel.setText("<html><body>"+"年龄："+"<br>"+ageNew+"岁"+"<body></html>");
        pictureL.setImage(HAWTools.stringToImage(avatar).getImage());
        centerPanel.add(yourNameLabel);
        centerPanel.add(statusJLabel);
        centerPanel.add(ageJLabel);
        picc.setIcon(pictureL);
        centerPanel.add(picc);
    }

}
