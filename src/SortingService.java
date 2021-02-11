import java.io.IOException;

public class SortingService {
  TapeSet tapeSet;

  public void sort(String filename, boolean tapesPrintedAfterEachPhase) throws IOException, ClassNotFoundException {
    tapeSet = new TapeSet(filename);
    printRecordsBeforeSort();

    do {
      tapeSet.processPhase();
      if (tapesPrintedAfterEachPhase) {
        printTapesAfterEachPhase();
      }
    } while (tapeSet.getNumberOfSeriesOnResultTape() > 1);

    tapeSet.closeTapeSet();
    printRecordsAfterSort();
  }

  private void printRecordsBeforeSort() throws IOException, ClassNotFoundException {
    System.out.println("Początek sortowania");
    System.out.println("Rekordy przed sortowaniem");
    tapeSet.printSourceFile();
    System.out.println();
  }

  private void printRecordsAfterSort() throws IOException, ClassNotFoundException {
    int numberOfReads = tapeSet.getNumberOfReads();
    int numberOfWrites = tapeSet.getNumberOfWrites();
    int numberOfPhases = tapeSet.getNumberOfPhases();

    System.out.println("Koniec sortowania");
    System.out.println("Rekordy po sortowaniu");
    tapeSet.printSourceFile();
    System.out.println();
    System.out.println("Liczba faz: " + numberOfPhases);
    System.out.println("Liczba zapisów: " + numberOfWrites);
    System.out.println("Liczba odczytów: " + numberOfReads);
    System.out.println();
  }

  private void printTapesAfterEachPhase() throws IOException, ClassNotFoundException {
    System.out.println("Faza " + tapeSet.getNumberOfPhases());
    System.out.println();
    tapeSet.printTapes();
    System.out.println();
  }
}
