package strategy;

import adt.Ladder;
import adt.LadderHaveMonkey;
import adt.Monkey;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Choose the ladder that is the furthest away from the monkey on the ladder.
 * 
 * @author Œ‚Íª
 *
 */

public class FarthestDistance implements CrossStrategy {

  @Override
  public int cross(Monkey monkey, Set<LadderHaveMonkey> ladders) {
    // TODO Auto-generated method stub
    Set<LadderHaveMonkey> ladderSet = new HashSet<LadderHaveMonkey>(ladders);
    for (LadderHaveMonkey ladderMonkey : ladders) {
      if (ladderMonkey.getMonkeys().size() == 0) {
        return ladderMonkey.getLadder().getNumber();
      }
    }
    char direction = 'l';
    if (monkey.isDirection()) {
      direction = 'r';
    }
    Iterator<LadderHaveMonkey> iterator = ladderSet.iterator();
    while (iterator.hasNext()) {
      char ladderDirection = iterator.next().getCurrentDirection();
      if (!(ladderDirection == direction || ladderDirection == 'z')) {
        iterator.remove();
      }
    }
    int maxDistance = 0;
    int index = -1;
    for (LadderHaveMonkey ladderMonkey : ladderSet) {
      Ladder ladder = ladderMonkey.getLadder();
      Map<Integer, Monkey> map = ladderMonkey.getLocationMap();
      int min = ladder.getH();
      int max = 1;
     for (int i = 1; i <= ladder.getH(); i++) {
       if (map.get(i) != null) {
         max = i;
       }
     }
     for (int i = ladder.getH(); i >= 1; i--) {
       if (map.get(i) != null) {
         max = i;
       }
     }
      int distance = 0;
      if (monkey.isDirection()) {
        distance = min;
      } else {
        distance = ladder.getH() - max + 1;
      }
      if (distance == 1) {
        continue;
      }
      if (maxDistance < distance) {
        maxDistance = distance;
        index = ladder.getNumber();
      }
    }
    return index;
  }

}
