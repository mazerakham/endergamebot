package endergamebot;

import static com.google.common.base.Preconditions.checkState;

import com.ender.game.model.Tile;
import com.ender.game.model.Unit;
import com.ender.game.model.UnitType;

import ox.Log;

public class JakeWarrior {

  private final Unit warrior;

  public JakeWarrior(Unit unit) {
    checkState(unit.type == UnitType.WARRIOR);
    this.warrior = unit;
  }

  public void attackEnemy(Tile tile, JakeBoard jakeBoard) {
    if (isInRange(tile) && !isOnCooldown()) {
      warrior.attack(tile);
    } else {
      BotUtils.moveToward(warrior, tile, jakeBoard);
    }
  }

  private boolean isOnCooldown() {
    if (warrior.attackCooldown > 0) {
      Log.debug("Waiting for cooldown: " + warrior.attackCooldown);
    }
    return warrior.attackCooldown <= 0;
  }

  public boolean isInRange(Tile tile) {
    return UnitType.WARRIOR.attackRange >= BotUtils.l1Distance(warrior.tile, tile);
  }
}
