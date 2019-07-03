package strategy;

import adt.LadderHaveMonkey;
import adt.Monkey;

import java.util.List;
import java.util.Set;

/**
 * ����ѡ��û�к��ӵ����ӣ������������϶��к��ӣ� ������ ѡ��û�����Ҷ�����еĺ��ӵ����ӣ� ������������������кܶ࣬ �����ѡ��.
 * 
 * @author ���
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
