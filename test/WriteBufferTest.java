import org.junit.Assert;
import org.junit.Test;

public class WriteBufferTest {
  @Test
  public void testWriteBufferClass_shouldNotThrowAnyException() {
    WriteBuffer buffer;
    try {
      ComplexNumber[] cnArray = {new ComplexNumber(1.0, 2.0), new ComplexNumber(3.0, 4.0)};
      buffer = new WriteBuffer("test.bin");
      buffer.startWriting();
      for (int i = 0; i < 1000; i++) {
        ComplexNumberSet record = new ComplexNumberSet(cnArray);
        buffer.addRecord(record);
      }
      buffer.endWriting();
    } catch (Exception e) {
      Assert.fail();
    }
  }
}
