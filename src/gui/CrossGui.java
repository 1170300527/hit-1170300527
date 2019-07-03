package gui;

import cross.CrossLadder;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import log.ReadLog;
import strategy.CrossStrategy;
import strategy.FarthestDistance;
import strategy.NoMonkeyFirst;
import strategy.SpeedFirst;

public class CrossGui extends JFrame {

  private JPanel timeJpanel = new JPanel();
  private JPanel lengthJpanel = new JPanel();
  private JPanel ladderNumberJpanel = new JPanel();
  private JPanel maxSpeedJpanel = new JPanel();
  private JPanel monkeyNumberJpanel = new JPanel();
  private JPanel eachMonkeyJpanel = new JPanel();
  private JPanel buttonJPanel = new JPanel();

  private JButton getButton = new JButton("确定");
  private JButton cancelButton = new JButton("取消");
  private JButton readButton = new JButton("读取日志及结果");

  private JTextField timeField = new JTextField(10);
  private JTextField lengthField = new JTextField(10);
  private JTextField ladderNumberField = new JTextField(10);
  private JTextField maxSpeedField = new JTextField(10);
  private JTextField monkeyNumberField = new JTextField(10);
  private JTextField eachMonkeyField = new JTextField(10);

  private int time;
  private int length;
  private int ladderNumber;
  private int maxSpeed;
  private int monkeyNumer;
  private int eachMonkey;

  private String log = "";
  
  CrossLadder crossLadder = new CrossLadder();
  CrossStrategy cross = new NoMonkeyFirst();

  /**
   * make gui.
   */
  public CrossGui() {
    // TODO Auto-generated constructor stub
    setLayout(null);
    setJPanel(ladderNumberJpanel, "梯子数量");
    ladderNumberJpanel.add(ladderNumberField);
    setJPanel(lengthJpanel, "梯子长度");
    lengthJpanel.add(lengthField);
    setJPanel(timeJpanel, "时间间隔");
    timeJpanel.add(timeField);
    setJPanel(monkeyNumberJpanel, "猴子数量");
    monkeyNumberJpanel.add(monkeyNumberField);
    setJPanel(eachMonkeyJpanel, "单次数量");
    eachMonkeyJpanel.add(eachMonkeyField);
    setJPanel(maxSpeedJpanel, "最大速度");
    maxSpeedJpanel.add(maxSpeedField);
    getButton.setPreferredSize(new Dimension(75, 25));
    buttonJPanel.add(getButton);
    cancelButton.setPreferredSize(new Dimension(75, 25));
    buttonJPanel.add(cancelButton);

    ladderNumberJpanel.setBounds(1200, 50, 250, 30);
    lengthJpanel.setBounds(1200, 150, 250, 30);
    timeJpanel.setBounds(1200, 250, 250, 30);
    monkeyNumberJpanel.setBounds(1200, 350, 250, 30);
    eachMonkeyJpanel.setBounds(1200, 450, 250, 30);
    maxSpeedJpanel.setBounds(1200, 550, 250, 30);
    buttonJPanel.setBounds(1200, 650, 250, 30);
    readButton.setBounds(1200, 750, 250, 30);

    this.add(timeJpanel);
    this.add(lengthJpanel);
    this.add(ladderNumberJpanel);
    this.add(maxSpeedJpanel);
    this.add(monkeyNumberJpanel);
    this.add(eachMonkeyJpanel);
    this.add(buttonJPanel);
    this.add(readButton);

    addActionListener(getButton);
    cancelButton.addActionListener(event -> {
      ladderNumberField.setText("");
      lengthField.setText("");
      timeField.setText("");
      monkeyNumberField.setText("");
      eachMonkeyField.setText("");
      maxSpeedField.setText("");
    });
    addLogButtoListener(readButton);
    
    this.setTitle("猴子过河");
    this.setSize(1500, 900);
    this.setLocation(200, 120);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
  }

  private void setJPanel(JPanel panel, String name) {
    // TODO Auto-generated method stub
    Font font = new Font("宋体", Font.PLAIN, 22);
    JLabel label = new JLabel(name);
    label.setFont(font);
    panel.add(label);
  }

  private void addActionListener(JButton button) {
    // TODO Auto-generated method stub
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        ladderNumber = Integer.parseInt(ladderNumberField.getText());
        length = Integer.parseInt(lengthField.getText());
        time = Integer.parseInt(timeField.getText());
        monkeyNumer = Integer.parseInt(monkeyNumberField.getText());
        eachMonkey = Integer.parseInt(eachMonkeyField.getText());
        maxSpeed = Integer.parseInt(maxSpeedField.getText());
        crossLadder.goLadder(time, monkeyNumer, eachMonkey, length, ladderNumber, maxSpeed, cross);
        CrossJPanel crossJPanel = new CrossJPanel(length, ladderNumber,
            crossLadder.getMonkeyLadder(), crossLadder.getMonkeys());
        crossJPanel.setBounds(0, 0, 1150, 900);
        addCrossJpanel(crossJPanel);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

          @Override
          public void run() {
            // TODO Auto-generated method stub
            crossJPanel.repaint();
          }
        }, 0, 1000);
      }
    });
  }

  private void addLogButtoListener(JButton button) {
    // TODO Auto-generated method stub
    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        frame.setSize(900, 900);
        frame.setLayout(null);
        Font font = new Font("宋体", Font.PLAIN, 22);
        JTextArea textArea = new JTextArea();
        textArea.setFont(font);
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setBounds(0, 0, 650, 850);
        frame.add(jsp);
        JLabel rateLabel = new JLabel("吞吐率：");
        JLabel numberLabel = new JLabel();
        JLabel fairnessLabel = new JLabel("公平性：");
        JPanel ratePanel = new JPanel();
        ratePanel.add(rateLabel);
        ratePanel.add(numberLabel);
        JPanel fairPanel = new JPanel();
        fairPanel.add(fairnessLabel);
        JLabel fnLabel = new JLabel();
        fairPanel.add(fnLabel);
        ratePanel.setBounds(650, 200, 250, 30);
        fairPanel.setBounds(650, 400, 250, 30);
        frame.add(fairPanel);
        frame.add(ratePanel);
        frame.setBounds(200, 100, 900, 900);
        frame.setVisible(true);
        Timer timer = new Timer();
        String path = "log//cross.log";
        timer.schedule(new TimerTask() {

          @Override
          public void run() {
            // TODO Auto-generated method stub
            log += ReadLog.randomRed(path);
            textArea.setText(log);
            numberLabel.setText(crossLadder.getThroughputRate() + "");
            fnLabel.setText(crossLadder.getFairRate() + "");
          }
        }, 15, 1000);
      }
    });
  }

  private void addCrossJpanel(JPanel jpanel) {
    // TODO Auto-generated method stub
    this.add(jpanel);
  }

}
