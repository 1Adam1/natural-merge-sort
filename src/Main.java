import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int inputMethodNumber;
    String sourceFilename;
    while (true) {
      try {
        System.out.println("");
        System.out.println("Program sortujący plik metodą scalania naturalnego");
        System.out.println("Metody dostarczania rekordów do pliku:");
        System.out.println("1) Rekordy podawane z klawiatury");
        System.out.println("2) Rekordy wczytywane z pliku binarnego");
        System.out.println("3) Rekordy generowane losowo");
        System.out.println("Proszę podać numer wybranej metody");

        inputMethodNumber = scanner.nextInt();

        if (inputMethodNumber == 1) {
          System.out.println("Proszę podać nazwę pliku, do którego zostaną zapisane wprowadzone rekordy");
          sourceFilename = scanner.next();
          readRecordsFromKeyboard(sourceFilename);
        } else if (inputMethodNumber == 2) {
          System.out.println("Proszę podać nazwę pliku");
          sourceFilename = scanner.next();
        } else if (inputMethodNumber == 3) {
          System.out.println("Proszę podać liczbę rekordów do wygenerowania");
          sourceFilename = "randomRecords.bin";
          generateFileWithRandomRecords(scanner.nextInt(), sourceFilename);
        } else {
          System.out.println("Podano zły numer");
          continue;
        }

        System.out.println("Czy zawartość taśm powinna być wypisywana po każdej fazie? (t - tak)");
        if (scanner.next().equals("t")) {
          sortFile(sourceFilename, true);
        } else {
          sortFile(sourceFilename, false);
        }

        System.out.println("Czy zakończyć program? (t - tak)");
        if (scanner.next().equals("t")) {
          break;
        }
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

  private static void sortFile(String sourceFilename, boolean tapesPrintedAfterEachPhase) throws IOException, ClassNotFoundException {
    SortingService sortingService = new SortingService();
    sortingService.sort(sourceFilename, tapesPrintedAfterEachPhase);
  }

  private static void readRecordsFromKeyboard(String destinationFilename) throws IOException {
    WriteBuffer writeBuffer = new WriteBuffer(destinationFilename);
    writeBuffer.startWriting();

    while (true) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Proszę podać liczbę elementów zbioru");
      int numberOfElements = scanner.nextInt();
      ComplexNumber[] complexNumberArray;

      if (numberOfElements > ComplexNumberSet.getSetMaxSize()) {
        System.out.println("Podana liczba elementów jest większa od maksymalnego rozmiaru zbioru");
        continue;
      }

      complexNumberArray = new ComplexNumber[numberOfElements];
      for (int i = 0; i < numberOfElements; i++) {
        double imaginaryPart, realPart;
        System.out.println("Prosze podać część rzeczywistą");
        realPart = scanner.nextDouble();
        System.out.println("Prosze podać część urojoną");
        imaginaryPart = scanner.nextDouble();
        complexNumberArray[i] = new ComplexNumber(realPart, imaginaryPart);
      }

      ComplexNumberSet complexNumberSet = new ComplexNumberSet(complexNumberArray);
      writeBuffer.addRecord(complexNumberSet);

      System.out.println("Czy zakończyć podawanie rekordów? (t - tak)");
      if (scanner.next().equals("t")) {
        break;
      }
    }

    writeBuffer.endWriting();
  }

  private static void generateFileWithRandomRecords(int numberOfRecords, String destinationFilename) throws IOException {
    WriteBuffer writeBuffer = new WriteBuffer(destinationFilename);
    Random random = new Random();
    writeBuffer.startWriting();

    for (int i = 0; i < numberOfRecords; i++) {
      int numberOfComplexNumbers = random.nextInt(ComplexNumberSet.getSetMaxSize());
      numberOfComplexNumbers++;
      ComplexNumberSet record = generateRandomRecord(numberOfComplexNumbers);
      writeBuffer.addRecord(record);
    }

    writeBuffer.endWriting();
  }

  private static ComplexNumberSet generateRandomRecord(int numberOfComplexNumbers) {
    ComplexNumber cnArray[] = new ComplexNumber[numberOfComplexNumbers];
    for (int i = 0; i < numberOfComplexNumbers; i++) {
      cnArray[i] = generateRandomComplexNumber();
    }
    return new ComplexNumberSet(cnArray);
  }

  private static ComplexNumber generateRandomComplexNumber() {
    Random random = new Random();
    return new ComplexNumber(100 * random.nextDouble(), 100 * random.nextDouble());
  }
}
