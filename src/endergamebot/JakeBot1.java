package endergamebot;

import com.ender.game.client.Bot;
import com.ender.game.client.EndersGameClient;
import com.ender.game.model.Base;
import com.ender.game.model.Grid;
import com.ender.game.model.Player;
import com.ender.game.model.UnitType;

/**
 * Timing attack bot: sets a number of workers (3) and then devotes all other resources to a military rush.
 * 
 * Future optimizations:
 * 
 * 1. Group warriors before attacking. Strength in numbers.
 * 
 * 2. Build warrior first, archer second. Send warrior to attack first.
 */
public class JakeBot1 implements Bot {

  private final String alias;

  private static final int WORKER_COUNT_GOAL = 1;

  public JakeBot1(String alias) {
    this.alias = alias;
  }

  @Override
  public String getName() {
    return alias;
  }

  @Override
  public String getEmail() {
    return "jake" + alias + "@ender.com";
  }

  @Override
  public void act(Player me, Grid grid) {
    JakeBoard jakeBoard = new JakeBoard(me, grid);
    maybeBuildWorkers(jakeBoard);
    maybeBuildWarriors(jakeBoard);
    gatherResources(jakeBoard);
    attackEnemy(jakeBoard);
  }

  private void maybeBuildWorkers(JakeBoard jakeBoard) {
    if (jakeBoard.getNumWorkers() < WORKER_COUNT_GOAL) {
      jakeBoard.getMyBase().construct(UnitType.WORKER);
    } else {
      return;
    }
  }

  private void maybeBuildWarriors(JakeBoard jakeBoard) {
    if (jakeBoard.getNumWorkers() >= WORKER_COUNT_GOAL) {
      jakeBoard.getMyBase().construct(UnitType.WARRIOR);
    } else {
      return;
    }
  }

  private void gatherResources(JakeBoard jakeBoard) {
    jakeBoard.getWorkers().forEach(jakeWorker -> {
      jakeWorker.gatherResources(jakeBoard);
    });
  }

  private void attackEnemy(JakeBoard jakeBoard) {
    Base enemyBase = jakeBoard.getTheirBase();
    jakeBoard.getWarriors().forEach(jakeWarrior -> {
      jakeWarrior.attackEnemy(enemyBase.tile, jakeBoard);
    });
  }

  public static void main(String[] args) {
    EndersGameClient.run(new JakeBot1("jakebot1"))
        .openWebBrowserWhenMatchStarts();
  }

}

