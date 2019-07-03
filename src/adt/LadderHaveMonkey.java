package adt;

import java.util.HashMap;
import java.util.Map;

public class LadderHaveMonkey {

  private final Ladder ladder;
  private final Map<Monkey, Integer> monkeys = new HashMap<Monkey, Integer>();
  private final Map<Integer, Monkey> locationMap = new HashMap<Integer, Monkey>();
  private char currentDirection = 'z';

  // Rep invariant:
  // ladder can't be null
  // Abstraction function:
  // currentDirection is now the monkey in ladder's direction
  // monkeys are in ladder which location
  // Safety from rep exposure
  // ladder is private final and is immutable
  // currentDirecion is immutable

  /**
   * make a LadderHaveMonkey.
   * 
   * @param ladder the ladder
   */
  public LadderHaveMonkey(Ladder ladder) {
    super();
    this.ladder = ladder;
  }

  /**
   * Update current direction.
   */
  public synchronized void setCurrentDirection() {
    if (monkeys.size() == 0) {
      currentDirection = 'z';
    } else {
      for (int i = 1; i <= ladder.getH(); i++) {
        if (locationMap.get(i) != null) {
          if (locationMap.get(i).isDirection()) {
            currentDirection = 'r';
          } else {
            currentDirection = 'l';
          }
          break;
        }
      }
    }
  }

  public char getCurrentDirection() {
    return currentDirection;
  }

  public Ladder getLadder() {
    return ladder;
  }

  public Map<Integer, Monkey> getLocationMap() {
    return locationMap;
  }

  public Map<Monkey, Integer> getMonkeys() {
    return monkeys;
  }
}
