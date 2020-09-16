package endergamebot;

import static com.google.common.base.Preconditions.checkState;

import com.ender.game.model.Unit;
import com.ender.game.model.UnitType;

public class JakeWorker {

  Unit worker;

  public JakeWorker(Unit unit) {
    checkState(unit.type == UnitType.WORKER);
    this.worker = unit;
  }

  public void gatherResources(JakeBoard jakeBoard) {
    worker.moveTo(BotUtils.getNearestResourceLocation(worker.tile, jakeBoard));
  }
}
