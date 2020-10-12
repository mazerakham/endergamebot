package endergamebot;

import com.ender.game.client.EndersGameClient;

public class JakeBotTester {

  public static void run2BotsOnServer() {
    EndersGameClient.run(new JakeBot1("Alice3")).openWebBrowserWhenMatchStarts();
    EndersGameClient.run(new JakeBot1("Bob3"));
  }
  public static void run1BotOnServer() {
    EndersGameClient.run(new JakeBot1("Bob")).openWebBrowserWhenMatchStarts();
  }

  public static void run2BotsLocal() {
    EndersGameClient.runLocal(new JakeBot1("Alice"));

    EndersGameClient.runLocal(new JakeBot1("Bob"))
        .openWebBrowserWhenMatchStarts();
  }

  public static void main(String[] args) {
    // run1BotOnServer();

    run2BotsOnServer();
  }
}
