package endergamebot;

import static com.google.common.base.Preconditions.checkState;

import com.ender.game.model.Tile;
import com.ender.game.model.Unit;
import com.ender.game.model.UnitType;

public class JakeWorker {

  private final Unit worker;

  public JakeWorker(Unit unit) {
    checkState(unit.type == UnitType.WORKER);
    this.worker = unit;
  }

  public void gatherResources(JakeBoard jakeBoard) {
    if (worker.cargo.isPresent()) {
      if (worker.tile.isAdjacentTo(jakeBoard.getMyBase().tile)) {
        worker.depositResource();
      } else {
        BotUtils.moveToward(worker, jakeBoard.getMyBase().tile, jakeBoard);
      }
    } else {
      Tile nearestResourceTile = BotUtils.getNearestResourceLocation(worker.tile, jakeBoard);
      if (BotUtils.equals(worker.tile, nearestResourceTile)) {
        worker.extractResource();
      } else {
        BotUtils.moveToward(worker, nearestResourceTile, jakeBoard);
      }
    }
  }
}
