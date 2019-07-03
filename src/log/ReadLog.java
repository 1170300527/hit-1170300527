package log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**.
 * ʵʱ��ȡ��־�ļ�
 * 
 * @author ���
 *
 */
public class ReadLog {
  // �ļ���ȡָ���α�
  public static long pointer = 0;

  /**
   * ��ȡ��־.
   * @param path �ļ�·��
   * @return �µ���־����
   */
  public static String randomRed(String path) {
    String log = "";
    File file = new File(path);
    try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

      raf.seek(pointer);// �ƶ��ļ�ָ��λ��
      String line = null;
      // ѭ����ȡ
      while ((line = raf.readLine()) != null) {
        if (line.equals("")) {
          continue;
        }
        log += line + "\n";
      }
      pointer = raf.getFilePointer();

    } catch (Exception e) {
      System.out.println("�쳣��" + e.getMessage());
      e.printStackTrace();
    }
    return log;
  }

}