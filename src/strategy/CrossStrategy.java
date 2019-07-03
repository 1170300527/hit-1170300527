package strategy;

import adt.LadderHaveMonkey;
import adt.Monkey;
import java.util.Set;


public interface CrossStrategy {

  /**
   * the monkey how to choose ladder.
   * @param monkey which monkey will choose ladder
   * @param ladders all ladders situation
   * @return the selected ladder number ,-1 if not appropriate
   */
  public int cross(Monkey monkey, Set<LadderHaveMonkey> ladders);
  
}
