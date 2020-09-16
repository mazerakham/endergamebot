package endergamebot;

import static com.google.common.base.Preconditions.checkState;
import static ox.util.Utils.propagate;

import java.util.HashMap;
import java.util.Queue;

import com.ender.game.model.Tile;
import com.google.common.collect.Queues;

import ox.Log;
import ox.Pair;
import ox.XList;

public class PathSearch {

  private final Tile start;
  private final Tile finish;
  private final JakeBoard board;
  private final Queue<Pair<Integer, Integer>> toCheck = Queues.newArrayDeque();
  private final HashMap<Pair<Integer, Integer>, Integer> dists = new HashMap<>();

  public PathSearch(Tile start, Tile finish, JakeBoard board) {
    this.start = start;
    this.finish = finish;
    this.board = board;
  }
  
  /**
   * Compute the neighbor toward which you should move from start to get to finish as quickly as possible.
   */
  public Tile getNextTile() {
    computeDists();
    try {
      return BotUtils.minimize(getNeighbors(start).filter(this::isChecked), this::getSavedDist);
    } catch (Exception e) {
      Log.debug("Path not found.");
      return null;
    }
  }

  private void computeDists() {
    enqueue(finish);
    while (!toCheck.isEmpty() && !isChecked(start)) {
      Pair<Integer, Integer> next = toCheck.remove();
      Tile nextTile = board.getTile(next.a, next.b);
      check(nextTile);
    }
  }

  private void check(Tile tile) {
    XList<Tile> neighbors = getNeighbors(tile);
    XList<Tile> checkedNeighbors = neighbors.filter(this::isChecked);

    if (BotUtils.equals(tile, finish)) {
      saveDist(tile, 0);
      enqueue(neighbors.filter(t -> !isChecked(t)));
    } else if (checkedNeighbors.isEmpty()) {
      Log.debug("We are here.");
      throw new RuntimeException();
    } else if (BotUtils.equals(tile, start)) {
      saveDist(tile, getSavedDist(BotUtils.minimize(checkedNeighbors, this::getSavedDist)) + 1);
    } else if (tile.canReceiveUnit()) {
      saveDist(tile, getSavedDist(BotUtils.minimize(checkedNeighbors, this::getSavedDist)) + 1);
      enqueue(neighbors.filter(t -> !isChecked(t)));
    } else {      
      checkState(!tile.canReceiveUnit());
    }

  }

  private int getSavedDist(Tile tile) {
    try {
      return dists.get(new Pair<Integer, Integer>(tile.i, tile.j));
    } catch (Exception e) {
      Log.debug("We are here.");
      throw propagate(e);
    }
  }

  private boolean isChecked(Tile tile) {
    return dists.containsKey(new Pair<Integer, Integer>(tile.i, tile.j));
  }

  private void enqueue(Iterable<Tile> tiles) {
    tiles.forEach(this::enqueue);
  }

  private void enqueue(Tile tile) {
    Pair<Integer, Integer> pair = new Pair<Integer, Integer>(tile.i, tile.j);
    toCheck.add(pair);
  }

  private void saveDist(Tile tile, int dist) {
    dists.put(new Pair<Integer, Integer>(tile.i, tile.j), dist);
  }

  private XList<Tile> getNeighbors(Tile tile) {
    int i = tile.i;
    int j = tile.j;
    return XList
        .create(board.getTile(i - 1, j), board.getTile(i + 1, j), board.getTile(i, j + 1), board.getTile(i, j - 1))
        .removeNulls();
  }
}
