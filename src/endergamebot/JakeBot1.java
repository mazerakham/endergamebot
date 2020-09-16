package endergamebot;

import com.ender.game.client.Bot;
import com.ender.game.client.EndersGameClient;
import com.ender.game.model.Base;
import com.ender.game.model.Direction;
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

  private static final int WORKER_COUNT_GOAL = 3;

  @Override
  public String getName() {
    return "JakeBot1";
  }

  @Override
  public String getEmail() {
    return "jake@ender.com";
  }

  @Override
  public void act(Player me, Grid grid) {
    JakeBoard jakeBoard = new JakeBoard(me, grid);
    maybeBuildWorkers(jakeBoard);
    maybeBuildWarriors(jakeBoard);
    gatherResources(jakeBoard);
    attackEnemy(jakeBoard);

    grid.getUnits(me).forEach(unit -> unit.move(Direction.NORTH));
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
      jakeWarrior.attackEnemy(enemyBase.tile);
    });
  }

  public static void main(String[] args) {
    EndersGameClient.run(new JakeBot1())
        .openWebBrowserWhenMatchStarts();
  }

}

