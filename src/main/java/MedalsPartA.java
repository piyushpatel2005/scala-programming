import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class MedalsPartA {
    private static Map<String, Integer> medalsByCountry = new HashMap<>();
    private static Map <String, Integer> medalsByEvent = new HashMap<>();

    public static void main(String[] args) {
        try {
            readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\test.txt");
            display();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        while(scanner.hasNext()) {
            String countryName = scanner.nextLine();
            String eventType = scanner.nextLine();
            String whichEvent = scanner.nextLine();

            if(!medalsByCountry.containsKey(countryName)) {
                medalsByCountry.put(countryName, 1);
            } else {
                int medalCount = medalsByCountry.get(countryName);
                medalCount++;
                medalsByCountry.put(countryName, medalCount);
            }

            if(!medalsByEvent.containsKey(eventType)) {
                medalsByEvent.put(eventType, 1);
            } else {
                int medals = medalsByEvent.get(eventType);
                medals++;
                medalsByEvent.put(eventType, medals);
            }

        }
    }

    private static void display() throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(new File("a2q1out.txt"));
        writeToConsoleAndFile(writer, "Count of gold medallists by country:");

        for (Map.Entry<String, Integer> entry: medalsByCountry.entrySet()) {
            writeToConsoleAndFile(writer, entry.getKey() + " - " + entry.getValue());
        }

        System.out.println();
        writeToConsoleAndFile(writer, "Count of gold medallists by event type:");

        for (Map.Entry<String, Integer> entry: medalsByEvent.entrySet()) {
            writeToConsoleAndFile(writer, entry.getKey() + " - " + entry.getValue());
        }

        System.out.println();
        writeToConsoleAndFile(writer,"End of processing.");
        writer.close();
    }

    private static void writeToConsoleAndFile(PrintWriter writer, String message) {
        System.out.println(message);
        writer.println(message);
    }
}
