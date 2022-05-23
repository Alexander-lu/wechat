import acm.program.ConsoleProgram;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

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
            Account account = new Account(request.getParam("name"));
            accounts.put(request.getParam("name"),account);
//            accounts.add(request.getParam("name"),account);
            return "pong1";}
        if (cmd.equals("deleteAccount")) {
            accounts.remove(request.getParam("name"));
            return "pong2";}

        return FAILURE_PREFIX + "未知命令【" + cmd + "】";
    }

}
