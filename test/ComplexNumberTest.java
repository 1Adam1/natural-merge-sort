import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {
  @Test
  public void testCountModulus_shouldReturn_4() {
    ComplexNumber cn = new ComplexNumber(0, 4);
    Assert.assertEquals(4, cn.getModulus(), 0);
  }

  @Test
  public void testCountModulus_shouldReturn_13() {
    ComplexNumber cn = new ComplexNumber(-5, 12);
    Assert.assertEquals(13, cn.getModulus(), 0);
  }

  @Test
  public void testEquals_shouldReturn_true() {
    ComplexNumber cn1 = new ComplexNumber(-5, 12);
    ComplexNumber cn2 = new ComplexNumber(-5, 12);
    Assert.assertEquals(true, cn1.equals(cn2));
  }

  @Test
  public void testEquals_shouldReturn_false() {
    ComplexNumber cn1 = new ComplexNumber(-5, 12);
    ComplexNumber cn2 = new ComplexNumber(12, -5);
    Assert.assertEquals(false, cn1.equals(cn2));
  }


}