package adt;


/**
 * The relationship between monkeys and bridges.
 * 
 * @author Îâê»
 *
 */
public class MonkeyInLadder {

  private Ladder ladder;
  private int h;
  
  //Abstraction function:
  // the monkey is in the ladder
  // the h is where does the monkey go to the ladder
  // Representation invariant:
  // h must be greater than zero
  // Safety from rep exposure
  //  monkey and ladder are private final
  //  h will be change so is only private
  

  public int getH() {
    checkRep();
    return h;
  }

  /**
   * make a monkey in ladder.
   * @param ladder the ladder
   * @param h location
   */
  public MonkeyInLadder(Ladder ladder, int h) {
    super();
    this.ladder = ladder;
    this.h = h;
  }

  public void setH(int h) {
    assert h >= 1;
    this.h = h;
    checkRep();//mutator
    return ;
  }

  public Ladder getLadder() {
    return ladder;
  }
  
  public void setLadder(Ladder ladder) {
    assert ladder != null;
    this.ladder = ladder;
    checkRep();//mutator
    return ;
  }

  @Override
  public String toString() {
    return "MonkeyInLadder [" + (ladder != null ? "ladder=" + ladder + ", " : "") + "h=" + h + "]";
  }

  private void checkRep() {
    // TODO Auto-generated method stub
    assert ladder != null;
    assert h >= 1;
  }
}
