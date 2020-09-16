package endergamebot;

import static com.google.common.base.Preconditions.checkState;

import com.ender.game.model.Tile;
import com.ender.game.model.Unit;
import com.ender.game.model.UnitType;

public class JakeWarrior {

  private final Unit warrior;

  public JakeWarrior(Unit unit) {
    checkState(unit.type == UnitType.WARRIOR);
    this.warrior = unit;
  }

  public void attackEnemy(Tile tile, JakeBoard jakeBoard) {
    if (canAttack(tile)) {
      warrior.attack(tile);
    } else {
      BotUtils.moveToward(warrior, tile, jakeBoard);
    }
  }

  public boolean canAttack(Tile tile) {
    return UnitType.WARRIOR.attackRange >= BotUtils.l1Distance(warrior.tile, tile);
  }
}
