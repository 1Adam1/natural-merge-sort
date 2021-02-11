import java.io.*;

public class ReadBuffer {
  private int bufferMaxSize = 10000;
  private int indexOfNextByteToRead;
  private byte[] buffer = new byte[bufferMaxSize];
  private boolean endOfFileOccurred = false;
  private int numberOfReadBytes;
  private SerializationService serializationService = new SerializationService();
  private InputStream inputStream;
  private int numberOfReads = 0;
  private String filename;

  ReadBuffer(String filename) {
    this.filename = filename;
  }

  public void startReading() throws IOException {
    inputStream = new BufferedInputStream(new FileInputStream(filename));
    numberOfReadBytes = 0;
    indexOfNextByteToRead = 0;
  }

  public void endReading() throws IOException {
    inputStream.close();
  }

  public ComplexNumberSet readRecord() throws IOException, ClassNotFoundException {
    byte[] readBytes = readRecordBytesFromBuffer();
    if (readBytes == null) {
      return null;
    } else {
      return serializationService.deserializeRecord(readBytes);
    }
  }

  private byte[] readRecordBytesFromBuffer() throws IOException {
    int recordSize = SerializationService.getRecordSize();
    byte[] recordBytes = new byte[recordSize];
    for (int i = 0; i < recordSize; i++) {
      if (isNextByteAvailable()) {
        recordBytes[i] = buffer[indexOfNextByteToRead];
        indexOfNextByteToRead++;
      } else {
        return null;
      }
    }
    return recordBytes;
  }

  private boolean isNextByteAvailable() throws IOException {
    if (indexOfNextByteToRead == numberOfReadBytes) {
      loadFromFileToBuffer();
    }
    return !endOfFileOccurred;
  }

  private void loadFromFileToBuffer() throws IOException {
    numberOfReadBytes = inputStream.read(buffer);
    endOfFileOccurred = numberOfReadBytes == -1;
    indexOfNextByteToRead = 0;
    numberOfReads++;
  }

  public int getNumberOfReads() {
    return numberOfReads;
  }
}
