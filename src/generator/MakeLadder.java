package generator;

import adt.Ladder;
import adt.LadderHaveMonkey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeLadder {

  private Map<Integer, Ladder> ladders = new HashMap<Integer, Ladder>();
  private Map<Integer, LadderHaveMonkey> ladderMonkeyMap = new HashMap<Integer, LadderHaveMonkey>();
  
  /**
   * make add ladders.
   * 
   * @param n number of ladders
   * @param h ladder's length
   */
  public MakeLadder(int n, int h) {
    for (int i = 1; i <= n; i++) {
      Ladder ladder = new Ladder(h, i);
      ladders.put(i, ladder);
      ladderMonkeyMap.put(i, new LadderHaveMonkey(ladder));
    }
  }

  public Map<Integer, Ladder> getLadders() {
    return ladders;
  }

  public Map<Integer, LadderHaveMonkey> getLadderMonkeyMap() {
    return ladderMonkeyMap;
  }

}
