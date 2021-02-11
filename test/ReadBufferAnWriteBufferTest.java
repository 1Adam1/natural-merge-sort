import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ReadBufferAnWriteBufferTest {
  private Random random = new Random();

  @Test
  public void testAddRecordAndReadRecord_shouldBeEqual() {
    WriteBuffer writeBuffer;
    ReadBuffer readBuffer;
    int indexOfNextReadRecord = 0;
    int numberOfRecords = 100;
    ComplexNumberSet[] originalRecords, readRecords;
    ComplexNumberSet readRecord;

    try {
      originalRecords = generateArrayOfRandomRecords(numberOfRecords);

      writeBuffer = new WriteBuffer("test.bin");
      writeBuffer.startWriting();
      for (int i = 0; i < originalRecords.length; i++) {
        writeBuffer.addRecord(originalRecords[i]);
      }
      writeBuffer.endWriting();

      readBuffer = new ReadBuffer("test.bin");
      readBuffer.startReading();
      readRecords = new ComplexNumberSet[numberOfRecords];
      while ((readRecord = readBuffer.readRecord()) != null) {
        readRecords[indexOfNextReadRecord] = readRecord;
        indexOfNextReadRecord++;
      }
      readBuffer.endReading();

      Assert.assertArrayEquals(originalRecords, readRecords);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  private ComplexNumberSet[] generateArrayOfRandomRecords(int size) {
    ComplexNumberSet cns[] = new ComplexNumberSet[size];
    for (int i = 0; i < size; i++) {
      cns[i] = generateRandomComplexNumberSet(5);
    }
    return cns;
  }

  private ComplexNumberSet generateRandomComplexNumberSet(int numberOfComplexNumbers) {
    ComplexNumber cnArray[] = new ComplexNumber[numberOfComplexNumbers];
    for (int i = 0; i < numberOfComplexNumbers; i++) {
      cnArray[i] = generateRandomComplexNumber();
    }
    return new ComplexNumberSet(cnArray);
  }

  private ComplexNumber generateRandomComplexNumber() {
    return new ComplexNumber(100 * random.nextDouble(), 100 * random.nextDouble());
  }
}

