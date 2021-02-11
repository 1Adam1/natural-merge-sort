import java.io.IOException;

public class TapeSet {
  private int numberOfTapes = 3;
  private String[] tapeFilenames = new String[numberOfTapes];
  private String sourceFilename;
  private Tape resultTape;
  private Tape[] additionalTapes;
  private int numberOfPhases = 0;
  private int numberOfReads = 0;
  private int numberOfWrites = 0;

  TapeSet(String sourceFilename) throws IOException {
    this.sourceFilename = sourceFilename;
    generateTapeFilenames();
    initializeTapes();
  }

  private void generateTapeFilenames() {
    for (int i = 0; i < numberOfTapes - 1; i++) {
      tapeFilenames[i] = "tape" + i + ".bin";
    }
    tapeFilenames[numberOfTapes - 1] = sourceFilename;
  }

  private void initializeTapes() throws IOException {
    additionalTapes = new Tape[numberOfTapes - 1];
    for (int i = 0; i < numberOfTapes - 1; i++) {
      additionalTapes[i] = new Tape(tapeFilenames[i]);
    }
    resultTape = new Tape(tapeFilenames[numberOfTapes - 1]);
  }

  public void processPhase() throws IOException, ClassNotFoundException {
    prepareTapesForDistribution();
    distributeSeries();
    prepareTapesForMerge();
    mergeSeries();
    endPhase();
  }

  private void prepareTapesForDistribution() throws IOException {
    for (int i = 0; i < numberOfTapes - 1; i++) {
      additionalTapes[i].startWriting();
    }
    resultTape.startReading();
  }

  private void distributeSeries() throws IOException, ClassNotFoundException {
    ComplexNumberSet record;
    int indexOfTape = 0;
    while ((record = resultTape.readRecord()) != null) {
      if (resultTape.hasEndOfSeriesOccurred()) {
        indexOfTape = (indexOfTape + 1) % (numberOfTapes - 1);
      }

      additionalTapes[indexOfTape].addRecord(record);
    }
  }

  private void prepareTapesForMerge() throws IOException {
    for (int i = 0; i < numberOfTapes - 1; i++) {
      additionalTapes[i].endWriting();
      additionalTapes[i].startReading();
    }
    resultTape.endReading();
    resultTape.startWriting();
  }

  private void mergeSeries() throws IOException, ClassNotFoundException {
    ComplexNumberSet[] recordsReadFromEachTape = new ComplexNumberSet[numberOfTapes - 1];
    boolean[] currentSeriesHasEndedOnTape = new boolean[numberOfTapes - 1];
    for (int i = 0; i < numberOfTapes - 1; i++) {
      recordsReadFromEachTape[i] = additionalTapes[i].readRecord();
      currentSeriesHasEndedOnTape[i] = recordsReadFromEachTape[i] == null;
    }

    while(!areAllRecordsFromAdditionalTapesRead(recordsReadFromEachTape)) {
      ComplexNumberSet record = null;
      int indexOfTapeWithReadRecord = -1;

      for (int i = 0; i < recordsReadFromEachTape.length; i++) {
        if (!currentSeriesHasEndedOnTape[i] &&
            (record == null || recordsReadFromEachTape[i].getVariablesRange() < record.getVariablesRange())) {
          record = recordsReadFromEachTape[i];
          indexOfTapeWithReadRecord = i;
        }
      }

      if (indexOfTapeWithReadRecord != -1) {
        resultTape.addRecord(record);
        recordsReadFromEachTape[indexOfTapeWithReadRecord] = additionalTapes[indexOfTapeWithReadRecord].readRecord();
        currentSeriesHasEndedOnTape[indexOfTapeWithReadRecord] = additionalTapes[indexOfTapeWithReadRecord].hasEndOfSeriesOccurred();
      } else {
        for (int i = 0; i < numberOfTapes - 1; i++) {
          currentSeriesHasEndedOnTape[i] = recordsReadFromEachTape[i] == null;
        }
      }
    }
  }

  private boolean areAllRecordsFromAdditionalTapesRead(ComplexNumberSet[] recordsReadFromEachTape) {
    for (int i = 0; i < recordsReadFromEachTape.length; i++) {
      if (recordsReadFromEachTape[i] != null) {
        return false;
      }
    }
    return true;
  }


  private void endPhase() throws IOException {
    for (int i = 0; i < numberOfTapes - 1; i++) {
      additionalTapes[i].endReading();
    }
    resultTape.endWriting();
    numberOfPhases++;
  }

  public void closeTapeSet() {
    for (int i = 0; i < numberOfTapes - 1; i++) {
      numberOfWrites += additionalTapes[i].getNumberOfWrites();
      numberOfReads += additionalTapes[i].getNumberOfReads();
    }
    numberOfWrites += resultTape.getNumberOfWrites();
    numberOfReads += resultTape.getNumberOfReads();
  }

  public void printTapes() throws IOException, ClassNotFoundException {
    for (int i = 0; i < numberOfTapes; i++) {
      System.out.println("Tasma nr " + i);
      System.out.println();
      printFile(tapeFilenames[i]);
      System.out.println("================================");
      System.out.println();
    }
  }

  public void printSourceFile() throws IOException, ClassNotFoundException {
    printFile(sourceFilename);
  }

  private void printFile(String filename) throws IOException, ClassNotFoundException {
    ReadBuffer readBuffer = new ReadBuffer(filename);
    ComplexNumberSet readRecord;
    int counter = 1;

    readBuffer.startReading();

    while ((readRecord = readBuffer.readRecord()) != null) {
     System.out.println(counter + ") " + readRecord.toString());
     counter++;
    }
    readBuffer.endReading();
  }

  public int getNumberOfSeriesOnResultTape() {
    return resultTape.getNumberOfSeries();
  }

  public int getNumberOfReads() {
    return numberOfReads;
  }

  public int getNumberOfWrites() {
    return numberOfWrites;
  }

  public int getNumberOfPhases() {
    return numberOfPhases;
  }
}
