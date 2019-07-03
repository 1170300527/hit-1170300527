package adt;

/**
 * . monkey has id, direction and speed
 * 
 * @author Îâê»
 *
 */
public class Monkey {

  private final int id;
  private final boolean direction;
  private final int speed;
  private final int birthTime;

  // Abstraction function:
  // id, direction and speed are the track's attribute
  // Representation invariant:
  // speed and birthTime must be greater than zero
  // Safety from rep exposure
  // all fields are private final

  /**
   * . Construction method
   * 
   * @param id        monkey's id
   * @param direction monkey's direction
   * @param speed     Moving speed
   * @param birthTime which time the monkey produce
   */
  public Monkey(int id, boolean direction, int speed, int birthTime) {
    super();
    this.id = id;
    this.direction = direction;
    this.speed = speed;
    this.birthTime = birthTime;
    checkRep();
  }

  public int getId() {
    return id;
  }

  public boolean isDirection() {
    return direction;
  }

  public int getSpeed() {
    return speed;
  }

  public int getBirthTime() {
    return birthTime;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + birthTime;
    result = prime * result + (direction ? 1231 : 1237);
    result = prime * result + id;
    result = prime * result + speed;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Monkey other = (Monkey) obj;
    if (birthTime != other.birthTime) {
      return false;
    }
    if (direction != other.direction) {
      return false;
    }
    if (id != other.id) {
      return false;
    }
    if (speed != other.speed) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    String directionString = direction ? "L->R" : "R->L";
    return "Monkey [id=" + id + ", direction=" + directionString + ", speed=" + speed
        + ", birthTime=" + birthTime + "]";
  }

  /**
   * . Speed is greater than 0
   */
  private void checkRep() {
    // TODO Auto-generated method stub
    assert speed > 0;
    assert birthTime >= 0;
  }
}
