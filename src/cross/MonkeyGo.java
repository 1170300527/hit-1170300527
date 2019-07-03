package cross;

import adt.Ladder;
import adt.LadderHaveMonkey;
import adt.Monkey;
import adt.MonkeyInLadder;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import strategy.CrossStrategy;
import strategy.FarthestDistance;
import strategy.NoMonkeyFirst;
import strategy.SpeedFirst;

/**
 * the monkey choice which ladder.
 * 
 * @author Œ‚Íª
 *
 */
public class MonkeyGo implements Runnable {

  private final Map<Monkey, MonkeyInLadder> monkeyLadder;
  private final Map<Integer, LadderHaveMonkey> ladderMonkey;
  private final Monkey monkey;
  private CrossStrategy cross;
  private final Map<Integer, Ladder> ladders;
  private final Map<Monkey, String> monkeyState;
  private final Map<Monkey, Integer> realTimeMap;
  private final Map<Monkey, Integer> finishTimeMap;

  public final Logger myLogger;

  /**
   * the monkey make a choice.
   * 
   * @param monkeyLadder monkey and Ladder relationship
   * @param ladderMonkey Ladder and monkey relationship
   * @param monkey       the monkey
   * @param ladders      all ladders
   * @param monkeyState  monkey's state wait cross or finish
   * @param realTimeMap  real time
   */
  public MonkeyGo(Map<Monkey, MonkeyInLadder> monkeyLadder,
      Map<Integer, LadderHaveMonkey> ladderMonkey, Monkey monkey, CrossStrategy cross,
      Map<Integer, Ladder> ladders, Map<Monkey, String> monkeyState,
      Map<Monkey, Integer> realTimeMap, Map<Monkey, Integer> finishTimeMap,Logger myLogger) {
    super();
    this.monkeyLadder = monkeyLadder;
    this.ladderMonkey = ladderMonkey;
    this.monkey = monkey;
    this.cross = cross;
    this.ladders = ladders;
    this.monkeyState = monkeyState;
    this.realTimeMap = realTimeMap;
    this.finishTimeMap = finishTimeMap;
    this.myLogger = myLogger;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    int index;
    Map<Integer, Monkey> ladderMap;
    Map<Monkey, Integer> monkeyMap;
    Ladder ladder;
    synchronized (ladderMonkey) {
      index = choice();
      if (index == -1) {
        myLogger.info("id: " + monkey.getId() + "; state: " + monkeyState.get(monkey) + "; age: "
            + realTimeMap.get(monkey));
        realTimeMap.put(monkey, realTimeMap.get(monkey) + 1);
        return;
      }
      monkeyState.put(monkey, "cross");
      ladder = ladders.get(index);
      int height = 1;
      if (!monkey.isDirection()) {
        height = ladder.getH();
      }
      monkeyLadder.put(monkey, new MonkeyInLadder(ladder, height));
      ladderMap = ladderMonkey.get(index).getLocationMap();
      ladderMap.put(height, monkey);
      monkeyMap = ladderMonkey.get(index).getMonkeys();
      monkeyMap.put(monkey, height);
      ladderMonkey.get(index).setCurrentDirection();
    }
    myLogger.info("id: " + monkey.getId() + "; ladder: " + index + "; location: "
        + monkeyMap.get(monkey) + "; direction: " + (!monkey.isDirection() ? " R->L" : " L->R")
        + "; age: " + realTimeMap.get(monkey));
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    realTimeMap.put(monkey, realTimeMap.get(monkey) + 1);
    MonkeyInLadder ml = monkeyLadder.get(monkey);
    while (ml.getH() <= ladder.getH()) {
      int next = ml.getH();
      int nowLocation = next;
      synchronized (ladderMap) {
        if (monkey.isDirection()) {
          for (int i = nowLocation + 1; i <= monkey.getSpeed() + nowLocation; i++) {
            if (ladderMap.get(i) != null) {
              break;
            }
            next = i;
          }
        } else {
          for (int i = nowLocation - 1; i >= nowLocation - monkey.getSpeed(); i--) {
            if (ladderMap.get(i) != null) {
              break;
            }
            next = i;
          }
        }
        if (next > ladder.getH() || next < 1) {
          monkeyState.put(monkey, "finish");
          finishTimeMap.put(monkey, realTimeMap.get(monkey));
          ladderMap.remove(ml.getH());
          monkeyMap.remove(monkey);
          monkeyLadder.remove(monkey);
          myLogger.info("id: " + monkey.getId() + "; finish " + "; direction: "
              + (!monkey.isDirection() ? " R->L" : " L->R") + "; age: " + realTimeMap.get(monkey));
          ladderMonkey.get(index).setCurrentDirection();
          break;
        }
        ladderMap.remove(ml.getH());
        monkeyMap.remove(monkey);
        ml.setH(next);
        ladderMap.put(next, monkey);
        monkeyMap.put(monkey, next);
        myLogger.info(
            "id: " + monkey.getId() + "; ladder: " + index + "; location: " + next + "; direction: "
                + (!monkey.isDirection() ? "R->L" : "L->R") + "; age: " + realTimeMap.get(monkey));
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      realTimeMap.put(monkey, realTimeMap.get(monkey) + 1);
    }
  }

  private synchronized int choice() {
    Set<LadderHaveMonkey> set = new HashSet<LadderHaveMonkey>(ladderMonkey.values());
    int witch = (new Random()).nextInt() % 3;
    switch (witch) {
      case 0:
        cross = new NoMonkeyFirst();
        break;
      case 1:
        cross = new SpeedFirst();
        break;
      default:
        cross = new FarthestDistance();
        break;
    }
    return cross.cross(monkey, set);
  }

}
