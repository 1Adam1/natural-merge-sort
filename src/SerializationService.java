import java.io.*;

public class SerializationService {
  public static byte[] serializeRecord(ComplexNumberSet record) throws IOException {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         ObjectOutput objectOutput = new ObjectOutputStream(byteArrayOutputStream)) {
      objectOutput.writeObject(record);
      return byteArrayOutputStream.toByteArray();
    }
  }

  public static ComplexNumberSet deserializeRecord(byte[] bytes) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
         ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream)) {
      return (ComplexNumberSet) objectInput.readObject();
    }
  }

  public static int getRecordSize() throws IOException {
    ComplexNumber[] cnArray = {new ComplexNumber(1.0, 2.0)};
    byte[] bytesArray = serializeRecord(
        new ComplexNumberSet(cnArray));
    return bytesArray.length;
  }
}
