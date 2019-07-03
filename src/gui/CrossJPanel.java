package gui;

import adt.Monkey;
import adt.MonkeyInLadder;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class CrossJPanel extends JPanel {

  private int length;
  private int ladderNumber;
  private Map<Monkey, MonkeyInLadder> monkeysMap = new HashMap<Monkey, MonkeyInLadder>();
  private List<Monkey> monkeys = new ArrayList<Monkey>();
  Image monkeyImage;

  /**
   * make cross gui.
   * 
   * @param length       ladder's length
   * @param ladderNumber ladder's number
   * @param monkeysMap   relationship between monkey and ladder
   */
  public CrossJPanel(int length, int ladderNumber, Map<Monkey, MonkeyInLadder> monkeysMap, List<Monkey> monkeys) {
    super();
    this.length = length;
    this.ladderNumber = ladderNumber;
    this.monkeysMap = monkeysMap;
    this.monkeys = monkeys;
    try {
      monkeyImage = ImageIO.read(new File("image//monkey.PNG"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * 空的构造方法.
   * 
   * @throws IOException throw IOException when get image fail
   */
  public CrossJPanel() {
    // TODO Auto-generated constructor stub
    try {
      monkeyImage = ImageIO.read(new File("image//monkey.PNG"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void paint(Graphics arg0) {
    // TODO Auto-generated method stub
    super.paint(arg0);
    int height = 900 / (ladderNumber + 1);
    int width = 950 / length;
    int nowHeight = height / 2;
    for (int i = 0; i < ladderNumber; i++) {
      arg0.drawLine(100, nowHeight, 1050, nowHeight);
      arg0.drawLine(100, nowHeight + 80, 1050, nowHeight + 80);
      int nowWidth = width / 2 + 100;
      for (int j = 0; j < length; j++) {
        arg0.drawLine(nowWidth, nowHeight, nowWidth, nowHeight + 80);
        nowWidth += width;
      }
      nowHeight += height;
    }
    for (int i = 0; i < monkeys.size(); i++) {
      MonkeyInLadder location = monkeysMap.get(monkeys.get(i));
      if (location != null) {
        int newWidth = Math.min(70, width);
        arg0.drawImage(monkeyImage, 100 + location.getH() * width - width / 2 - newWidth / 2,
            location.getLadder().getNumber() * height - height / 2 + (90 - newWidth) / 2,
            newWidth - 10, newWidth - 10, null);
      }
    }
  }

}
