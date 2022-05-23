import acm.program.ConsoleProgram;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;
import java.util.HashMap;
import acm.util.HAWTools;
public class WeChatServer extends ConsoleProgram
        implements SimpleServerListener {

    private HashMap<String,Account> accounts = new HashMap<>();
    /* 端口 */
    private static final int PORT = 8000;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILURE_PREFIX = "Error: ";

    /* 服务器对象，composition over inheritance */
    private SimpleServer server = new SimpleServer(this, PORT);

    public void run() {
        server.start();
        println("Starting server on port " + PORT);
    }
   public String requestMade(Request request) {
        String cmd = request.getCommand();
        println(request.toString());

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
                     return "";
                 }else {
                     return accounts.get(request.getParam("name")).getStatus();
                 }
             }else {
                 return FAILURE_PREFIX + "找不到账户【" + request.getParam("name") + "】";
             }
         }
//       if (cmd.equals("getFriends")) {
//           return "[]";
//           return accounts.get(request.getParam("name")).getFriends().toString();
//       }

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
      if (cmd.equals("getFriends")) {
           if(accounts.get(request.getParam("name")).friends.isEmpty() ){
               return "[]";
           }else{
               String a = "";
               for (int i = 0; i < accounts.get(request.getParam("name")).friends.size(); i++) {
                   a += accounts.get(request.getParam("name")).friends.get(i).getName() + ", ";
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
               for(int i = 0; i < accounts.get(request.getParam("name1")).friends.size(); i++) {
                   if(accounts.get(request.getParam("name1")).friends.get(i).getName().equals(request.getParam("name2"))){
                       ifa = true;
                   }
               }
               if(ifa){
                   return FAILURE_PREFIX + request.getParam("name1")+"和"+request.getParam("name2")+"已经是朋友了";
               }else {
                   accounts.get(request.getParam("name1")).addFriends(accounts.get(request.getParam("name2")));
                   return SUCCESS_MSG;
               }
           }else {
               return FAILURE_PREFIX + "找不到账户【" + request.getParam("name2") + "】";
           }
       }

        return FAILURE_PREFIX + "未知命令【" + cmd + "】";
    }

}
