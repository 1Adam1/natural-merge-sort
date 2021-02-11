import java.io.IOException;

public class Tape {
  private ReadBuffer readBuffer;
  private WriteBuffer writeBuffer;
  private boolean endOfSeriesOccurred = false;
  private ComplexNumberSet previousRecord;
  private ComplexNumberSet currentRecord;
  private int numberOfSeries = 0;

  Tape(String filename) throws IOException {
    this.readBuffer = new ReadBuffer(filename);
    this.writeBuffer = new WriteBuffer(filename);
  }

  public void startReading() throws IOException {
    readBuffer.startReading();
    previousRecord = null;
    currentRecord = null;
    numberOfSeries = 0;
  }

  public void endReading() throws IOException {
    readBuffer.endReading();
  }

  public ComplexNumberSet readRecord() throws IOException, ClassNotFoundException {
    previousRecord = currentRecord;
    currentRecord = readBuffer.readRecord();

    if (currentRecord == null ||
        (previousRecord != null
        && previousRecord.getVariablesRange() > currentRecord.getVariablesRange())) {
      endOfSeriesOccurred = true;
      numberOfSeries++;
    } else {
      endOfSeriesOccurred = false;
    }

    return currentRecord;
  }

  public void startWriting() throws IOException {
    writeBuffer.startWriting();
    previousRecord = null;
    currentRecord = null;
  }

  public void endWriting() throws IOException {
    writeBuffer.endWriting();
  }

  public void addRecord(ComplexNumberSet record) throws IOException {
    writeBuffer.addRecord(record);

  }

  public boolean hasEndOfSeriesOccurred() {
    return endOfSeriesOccurred;
  }

  public int getNumberOfWrites() {
    return writeBuffer.getNumberOfWrites();
  }

  public int getNumberOfSeries() {
    return numberOfSeries;
  }

  public int getNumberOfReads() {
    return readBuffer.getNumberOfReads();
  }
}
