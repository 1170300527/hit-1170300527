package strategy;

import adt.Ladder;
import adt.LadderHaveMonkey;
import adt.Monkey;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.management.loading.MLet;


/**
 * 优先选择整体推进速度最快的梯子.
 * 
 * @author 吴昊
 *
 */
public class SpeedFirst implements CrossStrategy {

  @Override
  public int cross(Monkey monkey, Set<LadderHaveMonkey> ladders) {
    // TODO Auto-generated method stub
    Set<LadderHaveMonkey> ladderSet = new HashSet<LadderHaveMonkey>(ladders);
    for (LadderHaveMonkey ladderMonkey : ladders) {
      if (ladderMonkey.getMonkeys().size() == 0) {
        return ladderMonkey.getLadder().getNumber();
      }
    }
    char direcion = 'l';
    if (monkey.isDirection()) {
      direcion = 'r';
    }
    Iterator<LadderHaveMonkey> iterator = ladderSet.iterator();
    while (iterator.hasNext()) {
      char ladderDirection = iterator.next().getCurrentDirection();
      if (!(ladderDirection == direcion || ladderDirection == 'z')) {
        iterator.remove();
      }
    }
    int minNumber = Integer.MAX_VALUE;
    Set<LadderHaveMonkey> newladderHaveMonkeys = new HashSet<LadderHaveMonkey>();
    for (LadderHaveMonkey ladderMonkey : ladderSet) {
      Map<Monkey, Integer> map = ladderMonkey.getMonkeys();
      if (minNumber > map.size()) {
        minNumber = map.size();
        newladderHaveMonkeys.clear();
        newladderHaveMonkeys.add(ladderMonkey);
      } else if (minNumber == map.size()) {
        newladderHaveMonkeys.add(ladderMonkey);
      }
    }
    int maxSpeed = 0;
    int index = -1;
    int lspeed = 0;
    int rspeed = 0;
    int speed;
    for (LadderHaveMonkey ladderMonkey : newladderHaveMonkeys) {
      Ladder ladder = ladderMonkey.getLadder();
      Map<Integer, Monkey> map = ladderMonkey.getLocationMap();
      int min = ladder.getH();
      int max = 1;
      Monkey ml;
      for (int i = 1; i <= ladder.getH(); i++) {
        if ((ml = map.get(i)) != null) {
          max = i;
          lspeed = ml.getSpeed();
        }
      }
      Monkey rm;
      for (int i = ladder.getH(); i >= 1; i--) {
        if ((rm = map.get(i)) != null) {
          max = i;
          rspeed = rm.getSpeed();
        }
      }
      int distance = 0;
      if (monkey.isDirection()) {
        distance = min;
        speed = lspeed;
      } else {
        distance = ladder.getH() - max + 1;
        speed = rspeed;
      }
      if (distance == 1 && monkey.isDirection()
          || distance == ladder.getH() && !monkey.isDirection()) {
        continue;
      }
      if (maxSpeed < speed) {
        maxSpeed = speed;
        index = ladder.getNumber();
      }
    }
    return index;
  }

}
