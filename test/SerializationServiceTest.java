import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SerializationServiceTest {
  private SerializationService serializationService = new SerializationService();

  @Test
  public void testSerializeAndDeserialize_shouldBeEqual() {
    try {
      ComplexNumberSet originalComplexNumberSet = new ComplexNumberSet(getExampleArrayOfComplexNumbers());
      byte[] byteArray = serializationService.serializeRecord(originalComplexNumberSet);
      ComplexNumberSet newComplexNumberSet = serializationService.deserializeRecord(byteArray);
      Assert.assertEquals(true, originalComplexNumberSet.equals(newComplexNumberSet));
    } catch (Exception e) {
      Assert.fail();
    }
  }

  private ComplexNumber[] getExampleArrayOfComplexNumbers() {
    Random random = new Random();
    ComplexNumber[] cnArray = new ComplexNumber[ComplexNumberSet.getSetMaxSize()];
    for (int i = 0; i < ComplexNumberSet.getSetMaxSize(); i++) {
      ComplexNumber cn = new ComplexNumber(100 * random.nextDouble(), 100 * random.nextDouble());
      cnArray[i] = cn;
    }
    return cnArray;
  }

  @Test
  public void testIfDifferentComplexNumberSetObjectsAreSameSize() {
    try {
      ComplexNumber[] cnArray1 = {new ComplexNumber(3.0, 4.0),
          new ComplexNumber(3.0, 4.0),
          new ComplexNumber(3.0, 4.0)};
      ComplexNumber[] cnArray2 = {new ComplexNumber(3.0, 4.0),
          new ComplexNumber(300000.0, 4000000.0),
          new ComplexNumber(300000000.0, 0.01),
      };
      byte[] byteArray1 = serializationService.serializeRecord(new ComplexNumberSet(cnArray1));
      byte[] byteArray2 = serializationService.serializeRecord(new ComplexNumberSet(cnArray2));
      Assert.assertEquals(true, byteArray1.length == byteArray2.length);
    } catch (Exception e) {
      Assert.fail();
    }
  }

  @Test
  public void testGetComplexNumberSetSize_shouldBeGreaterThanZero() {
    try {
      Assert.assertEquals(true, serializationService.getRecordSize() > 0);
    } catch (Exception e) {
      Assert.fail();
    }

  }
}