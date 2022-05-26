import adalab.core.net.Request;
import adalab.core.net.SimpleClient;

import java.util.Map;

public class WeChatClientUtil {
//  private static final String HOST="http://localhost:3200";
  private static final String HOST = "http://175.178.4.131:3200";
//  private static final String HOST = "http://120.78.127.243:8000";
  private static final SimpleClient client = new SimpleClient(HOST);

  private WeChatClientUtil() {}

  public static String makeRequest(String cmd, Map<String, String> params) {
    Request request = new Request(cmd);
    for (Map.Entry<String, String> entry : params.entrySet()) {
      request.addParam(entry.getKey(), entry.getValue());
    }
    try {
      return client.makeRequest(request);

    } catch (Exception e) {
      return e.getMessage();
    }
  }
}
