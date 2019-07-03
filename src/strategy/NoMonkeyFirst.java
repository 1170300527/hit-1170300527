package strategy;

import adt.LadderHaveMonkey;
import adt.Monkey;

import java.util.List;
import java.util.Set;

/**
 * 优先选择没有猴子的梯子，若所有梯子上都有猴子， 则优先 选择没有与我对向而行的猴子的梯子； 若满足该条件的梯子有很多， 则随机选择.
 * 
 * @author 吴昊
 *
 */
public class NoMonkeyFirst implements CrossStrategy {

  @Override
  public int cross(Monkey monkey, Set<LadderHaveMonkey> ladders) {
    // TODO Auto-generated method stub
    for (LadderHaveMonkey ladderMonkey : ladders) {
      if (ladderMonkey.getMonkeys().size() == 0) {
        return ladderMonkey.getLadder().getNumber();
      }
    }
    char direction = 'l';
    if (monkey.isDirection()) {
      direction = 'r';
    }
    for (LadderHaveMonkey ladderMonkey : ladders) {
      char ladderDirection = ladderMonkey.getCurrentDirection();
      int height = 1;
      if (!monkey.isDirection()) {
        height = ladderMonkey.getLadder().getH();
      }
      if ((ladderDirection == direction || ladderDirection == 'z')
          && ladderMonkey.getLocationMap().get(height) == null) {
        return ladderMonkey.getLadder().getNumber();
      }
    }
    return -1;
  }

}
