package endergamebot;

import static com.google.common.base.Preconditions.checkNotNull;
import static ox.util.Utils.first;

import java.util.Collection;
import java.util.function.Function;

import com.ender.game.model.Tile;
import com.ender.game.model.Unit;

public class BotUtils {

  private BotUtils() {
    // should never be constructed. Static methods only.
  }

  public static int linfDistance(Tile a, Tile b) {
    return Math.max(Math.abs(a.i - b.i), Math.abs(a.j - b.j));
  }

  public static int l1Distance(Tile a, Tile b) {
    return Math.abs(a.i - b.i) + Math.abs(a.j - b.j);
  }

  /**
   * Uses l1Distance (assumes no diagonal movement).
   */
  public static Tile getNearestResourceLocation(Tile startPoint, JakeBoard board) {
    Collection<Tile> resourceLocations = board.getResourceLocations();
    return minimize(resourceLocations, tile -> l1Distance(startPoint, tile));
  }

  public static void moveToward(Unit unit, Tile target) {
    unit.moveTo(target);
  }

  public static <T> T minimize(Collection<T> objs, Function<T, Integer> function) {
    T optimal = checkNotNull(first(objs));
    int optimum = function.apply(optimal);
    for (T obj : objs) {
      if (function.apply(obj) < optimum) {
        optimum = function.apply(obj);
        optimal = obj;
      }
    }
    return optimal;
  }
}
