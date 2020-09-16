package endergamebot;

import com.ender.game.client.EndersGameClient;

public class JakeBotTester {

  public static void main(String[] args) {
    EndersGameClient.runLocal(new JakeBot1("Alice"));

    EndersGameClient.runLocal(new JakeBot1("Bob"))
        .openWebBrowserWhenMatchStarts();
  }
}
