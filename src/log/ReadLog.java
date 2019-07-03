package log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**.
 * 实时读取日志文件
 * 
 * @author 吴昊
 *
 */
public class ReadLog {
  // 文件读取指针游标
  public static long pointer = 0;

  /**
   * 读取日志.
   * @param path 文件路径
   * @return 新的日志内容
   */
  public static String randomRed(String path) {
    String log = "";
    File file = new File(path);
    try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

      raf.seek(pointer);// 移动文件指针位置
      String line = null;
      // 循环读取
      while ((line = raf.readLine()) != null) {
        if (line.equals("")) {
          continue;
        }
        log += line + "\n";
      }
      pointer = raf.getFilePointer();

    } catch (Exception e) {
      System.out.println("异常：" + e.getMessage());
      e.printStackTrace();
    }
    return log;
  }

}