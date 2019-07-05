package cross;

import adt.Ladder;
import adt.LadderHaveMonkey;
import adt.Monkey;
import adt.MonkeyInLadder;
import generator.MakeLadder;
import generator.MonkeyGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import strategy.CrossStrategy;

public class CrossLadder {

  private final Map<Monkey, MonkeyInLadder> monkeyLadder = new HashMap<Monkey, MonkeyInLadder>();
  private final Map<Integer, LadderHaveMonkey> ladderMonkey = new HashMap<Integer, LadderHaveMonkey>();
  private final Map<Integer, Ladder> ladders = new HashMap<Integer, Ladder>();
  private final List<Monkey> monkeys = new ArrayList<Monkey>();
  private final Map<Monkey, String> monkeyState = new HashMap<Monkey, String>();
  private final Map<Monkey, Integer> realTimeMap = new HashMap<Monkey, Integer>();
  private final Map<Monkey, Integer> finishTimeMap = new HashMap<Monkey, Integer>();
  private double throughputRate = 0;
  private double fairRate = 0;

  private Logger myLogger = Logger.getLogger("cross.log");
  private FileHandler fileHandler;

  /**
   * cross ladder.
   * 
   * @param time     Intervals time
   * @param total    Total number of monkeys
   * @param k        Number generated each time
   * @param maxSpeed monkey's Maximum speed
   * @param cross    Choose a ladder strategy
   */
  public void goLadder(int time, int total, int k, int h, int n, int maxSpeed,
      CrossStrategy cross) {
    // TODO Auto-generated method stub
    makeLog();
    MakeLadder makeLadder = new MakeLadder(n, h);
    ladders.putAll(makeLadder.getLadders());
    ladderMonkey.putAll(makeLadder.getLadderMonkeyMap());
    MonkeyGenerator createMonkeyGenerator = new MonkeyGenerator(time, total, k, maxSpeed);
    Thread monkeyGeneratorThread = new Thread(createMonkeyGenerator);
    monkeyGeneratorThread.start();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      int time = -2;

      @Override
      public void run() {
        // TODO Auto-generated method stub
        time++;
        int old = monkeys.size();
        monkeys.addAll(createMonkeyGenerator.getMokeyList());
        int now = monkeys.size();
        for (int i = old; i < now; i++) {
          monkeyState.put(monkeys.get(i), "wait");
          realTimeMap.put(monkeys.get(i), 0);
        }
        for (int j = 0; j < monkeys.size(); j++) {
          if (!(monkeyState.values().contains("wait") || monkeyState.values().contains("cross"))
              && finishTimeMap.size() == monkeys.size()) {
            throughputRate = (double) total / time;
            if (total == 1) {
              fairRate = 1;
            } else {
              fairRate = (double) makeFair() / (total * (total - 1)) * 2;
            }
            timer.cancel();
          }
        }
        for (int i = 0; i < monkeys.size(); i++) {
          if (monkeyState.get(monkeys.get(i)).equals("wait")) {
            Thread crossThread = new Thread(new MonkeyGo(monkeyLadder, ladderMonkey, monkeys.get(i),
                cross, ladders, monkeyState, realTimeMap, finishTimeMap, myLogger));
            crossThread.start();
          }
        }
      }
    }, 5, 1000);

  }

  public Map<Monkey, MonkeyInLadder> getMonkeyLadder() {
    return monkeyLadder;
  }

  private int makeFair() {
    // TODO Auto-generated method stub
    int fair = 0;
    for (int i = 0; i < finishTimeMap.size() - 1; i++) {
      for (int j = i + 1; j < finishTimeMap.size(); j++) {
        int y = monkeys.get(i).getBirthTime() - monkeys.get(j).getBirthTime();
        int z = finishTimeMap.get(monkeys.get(i)) - finishTimeMap.get(monkeys.get(j));
        if (y * z >= 0) {
          fair++;
        } else {
          fair--;
        }
      }
    }
    return fair;
  }

  public double getThroughputRate() {
    return throughputRate;
  }

  public double getFairRate() {
    return fairRate;
  }

  private void makeLog() {
    // TODO Auto-generated method stub
    try {
      fileHandler = new FileHandler("log//cross.log");
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    fileHandler.setLevel(Level.INFO);
    fileHandler.setFormatter(new Formatter() {

      @Override
      public String format(LogRecord record) {
        // TODO Auto-generated method stub
        return record.getMessage() + "\n";
      }
    });
    myLogger.setUseParentHandlers(false);
    myLogger.addHandler(fileHandler);
  }

  public List<Monkey> getMonkeys() {
    return Collections.unmodifiableList(monkeys);
  }
}
