package generator;

import adt.Monkey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonkeyGenerator implements Runnable {
  
  private final int time;
  private final int total;
  private final int k;
  private final int maxSpeed;
  private List<Monkey> monkeyList = new ArrayList<Monkey>();
  
  /**
   * 构造方法.
   * @param time 间隔时间
   * @param total 猴子总数
   * @param k 每次产生猴子数量
   */
  public MonkeyGenerator(int time, int total,int k, int maxSpeed) {
    super();
    this.time = time;
    this.total = total;
    this.k = k;
    this.maxSpeed = maxSpeed;
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    int order = 1;
    int nowTime = 0;
    Random random = new Random();
    for (int i = 0; i < total / k; i++) {
      for (int j = 0; j < k; j++) {
        int id = order;
        int direction = random.nextInt();
        int speed = random.nextInt(maxSpeed) + 1;
        Monkey monkey = new Monkey(id, direction % 2 == 0, speed, nowTime);
        monkeyList.add(monkey);
        order++;
      }
      nowTime += time;
      try {
        Thread.sleep(time * 1000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    for (int i = 0; i < total % k; i++) {
      int id = order;
      int direction = random.nextInt();
      int speed = random.nextInt(maxSpeed - 1) + 1;
      Monkey monkey = new Monkey(id, direction % 2 == 0, speed, nowTime);
      monkeyList.add(monkey);
      order++;
    }
  }
  
  /**
   * 返回生成的猴子，并清空.
   * @return 猴子
   */
  public List<Monkey> getMokeyList() {
    List<Monkey> monkeys = new ArrayList<Monkey>(monkeyList);
    monkeyList.clear();
    return monkeys;
  }
  
}
