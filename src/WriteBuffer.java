import java.io.*;

public class WriteBuffer {
  private int bufferMaxSize = 10000;
  private int numberOfBytesInBuffer = 0;
  private byte[] buffer = new byte[bufferMaxSize];
  private SerializationService serializationService = new SerializationService();
  private OutputStream outputStream;
  private int numberOfWrites = 0;
  private String filename;

  WriteBuffer(String filename) throws IOException {
    this.filename = filename;
  }

  public void startWriting() throws IOException {
    outputStream = new BufferedOutputStream(new FileOutputStream(filename));
    numberOfBytesInBuffer = 0;
  }

  public void endWriting() throws IOException {
    if (numberOfBytesInBuffer > 0) {
      saveBufferToFile();
    }
    outputStream.close();
  }

  public void addRecord(ComplexNumberSet record) throws IOException {
    byte[] bytes = serializationService.serializeRecord(record);
    addBytesToBuffer(bytes);
  }

  private void addBytesToBuffer(byte[] bytes) throws IOException {
    for (int i = 0; i < bytes.length; i++) {
      if (numberOfBytesInBuffer == bufferMaxSize) {
        saveBufferToFile();
      }
      buffer[numberOfBytesInBuffer] = bytes[i];
      numberOfBytesInBuffer++;
    }
  }

  private void saveBufferToFile() throws IOException {
    outputStream.write(buffer, 0, numberOfBytesInBuffer);
    buffer = new byte[bufferMaxSize];
    numberOfBytesInBuffer = 0;
    numberOfWrites++;
  }

  public int getNumberOfWrites() {
    return numberOfWrites;
  }
}
