package adt;

/**
 * . ladder
 * 
 * @author Îâê»
 *
 */

public class Ladder {

  private final int h;
  private final int number;

  // Abstraction function:
  // h is the ladder's attribute
  // Representation invariant:
  // ladder must be greater than zero
  // Safety from rep exposure
  // all fields are private final

  /**
   * make a ladder.
   * @param h ladder's length
   * @param number the number
   */
  public Ladder(int h, int number) {
    super();
    this.h = h;
    this.number = number;
    checkRep();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + h;
    result = prime * result + number;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ladder other = (Ladder) obj;
    if (h != other.h)
      return false;
    if (number != other.number)
      return false;
    return true;
  }

  public int getH() {
    return h;
  }

  public int getNumber() {
    return number;
  }

  private void checkRep() {
    // TODO Auto-generated method stub
    assert h > 0;
    assert number > 0;
  }

}
