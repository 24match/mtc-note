package decorators;

/**
 * 定义读取和写入
 */
public interface DataSource {

    void writeData(String data);

    String readData();
}
