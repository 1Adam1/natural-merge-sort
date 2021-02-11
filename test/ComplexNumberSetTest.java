import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberSetTest {
  @Test
  public void testConstructor_numberOfItemsInSetShouldBeEqualTo_maxSetSize() {
    ComplexNumber cn = new ComplexNumber(0, 4);
    ComplexNumber[] cnArray = new ComplexNumber[ComplexNumberSet.getSetMaxSize() + 1];
    for (int i = 0; i < ComplexNumberSet.getSetMaxSize() + 1; i++) {
      cnArray[i] = cn;
    }
    ComplexNumberSet cns = new ComplexNumberSet(cnArray);
    Assert.assertEquals(true, cns.getNumberOfItemsInSet() == ComplexNumberSet.getSetMaxSize());
  }

  @Test
  public void testConstructor_numberOfItemsInSetShouldBeLower_maxSetSize() {
    ComplexNumber cn = new ComplexNumber(0, 4);
    ComplexNumber[] cnArray = new ComplexNumber[ComplexNumberSet.getSetMaxSize() - 1];
    for (int i = 0; i < ComplexNumberSet.getSetMaxSize() - 1; i++) {
      cnArray[i] = cn;
    }
    ComplexNumberSet cns = new ComplexNumberSet(cnArray);
    Assert.assertEquals(true, cns.getNumberOfItemsInSet() < ComplexNumberSet.getSetMaxSize());
  }

  @Test
  public void testCalculateVariableRange() {
    ComplexNumber[] cnArray = {new ComplexNumber(3, -5),
        new ComplexNumber(-3, -4),
        new ComplexNumber(12, -5),
        new ComplexNumber(-5, -5)};
    ComplexNumberSet cns = new ComplexNumberSet(cnArray);
    Assert.assertEquals(8, cns.getVariablesRange(), 0);
  }

  @Test
  public void testEquals_shouldReturnTrue() {
    ComplexNumber[] cnArray = {new ComplexNumber(3, -4),
        new ComplexNumber(-3, -4)};
    Assert.assertEquals(true, new ComplexNumberSet(cnArray).equals(new ComplexNumberSet(cnArray)));
  }

  @Test
  public void testEquals_shouldReturnFalse() {
    ComplexNumber[] cnArray1 = {new ComplexNumber(3, -5),
        new ComplexNumber(-3, -4)};
    ComplexNumber[] cnArray2 = {new ComplexNumber(3, -5),
        new ComplexNumber(-3, -4),
        new ComplexNumber(-3, -4)};
    Assert.assertEquals(false, new ComplexNumberSet(cnArray1).equals(new ComplexNumberSet(cnArray2)));
  }

  @Test
  public void testToString_shouldBeEqual() {
    ComplexNumber[] cnArray = {new ComplexNumber(-3, -4),
        new ComplexNumber(12, -5)};
    ComplexNumberSet cns = new ComplexNumberSet(cnArray);
    Assert.assertEquals("8.0 { r = -3.0, i = -4.0; r = 12.0, i = -5.0; }", cns.toString());
  }
}