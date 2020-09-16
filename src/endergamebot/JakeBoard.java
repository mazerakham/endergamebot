package endergamebot;

import static ox.util.Functions.filter;

import java.util.Collection;

import com.ender.game.model.Base;
import com.ender.game.model.Grid;
import com.ender.game.model.Player;
import com.ender.game.model.Tile;
import com.ender.game.model.UnitType;

import ox.XList;

public class JakeBoard {

  private final Player me;
  private final Player them;
  private final Grid grid;

  public JakeBoard(Player me, Player them, Grid grid) {
    this.me = me;
    this.them = them;
    this.grid = grid;
  }
  
  public Base getMyBase() {
    return grid.getBase(me);
  }

  public Base getTheirBase() {
    return grid.getBase(them);
  }

  public int getNumWorkers() {
    return getWorkers().size();
  }

  public Collection<JakeWorker> getWorkers() {
    return XList.create(grid.getUnits(me)).filter(u -> u.type == UnitType.WORKER).map(JakeWorker::new);
  }

  public Collection<Tile> getResourceLocations() {
    return filter(grid.getTiles(), tile -> tile.resourceAmount > 0);
  }

  public Collection<JakeWarrior> getWarriors() {
    return XList.create(grid.getUnits(me)).filter(u -> u.type == UnitType.WARRIOR).map(JakeWarrior::new);
  }

}
