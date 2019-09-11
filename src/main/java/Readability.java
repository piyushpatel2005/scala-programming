import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Readability {
    private static ArrayList<Sentence> sentences = new ArrayList<>();
    private static int numberOfWords = 0;
    private static int numberOfLetters = 0;


    public static void main(String[] args) {
        try {
            readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\test3.txt");
            displayResults();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        Sentence sentence = new Sentence();
        sentences.add(sentence);
        int i = 0;
        while(scanner.hasNextLine()) {
//            int i = 0;

            String line = scanner.nextLine().trim();
            String[] words = line.split(" ");
            for(String word : words) {
//                Sentence sentence = new Sentence();
//                if (sentences.size() == 0)
//                    sentences.add(sentence);
//                if(sentences.get(i) == null) {
//                    sentences.add(new Sentence());
//                } else {
                word = word.trim();
                    sentences.get(i).add(word);
                    numberOfWords += 1;
                    numberOfLetters += word.length();
//                }
                if(word.endsWith("!") || word.endsWith("?") || word.endsWith(".")) {
                    i++;
                    numberOfLetters -= 1;
                    Sentence nextSentence = new Sentence();
                    sentences.add(nextSentence);
                }

            }
        }
    }
//
//    public static void printSentences() {
//        for(Sentence sentence: sentences) {
//            if(sentence.getText().length() > 0)
//                System.out.println(sentence);
//        }
//    }

    public static int countSentences() {
        int count = 0;
        for (Sentence sentence: sentences) {
            if (sentence.getText().length() > 0)
                count += 1;
        }
        return count;
    }

    public static int countWords() {
        int count = 0;
        for (Sentence sentence: sentences) {
            String[] words = sentence.getText().split(" ");
            for(String word: words) {
                word = word.replaceAll("[^a-zA-Z]+","");
                if (word.matches("[a-zA-Z]+"))
                    count += 1;
            }
        }
        return count;
    }

    public static int countLetters() {
        int count = 0;
        for (Sentence sentence: sentences) {
            String[] words = sentence.getText().split(" ");
            for(String word: words) {
                word = word.replaceAll("[^a-zA-Z]+","");
                if (word.matches("[a-zA-Z]+"))
                    count += word.length();
            }
        }
        return count;
    }

    public static double calculateReadability() {
        double readability = (4.71 * ((double)countLetters() / countWords()) + 0.5 * ((double)countWords() / countSentences()) - 21.43);
        return Math.round(readability * 10) / 10.0;
    }

    public static void displayResults() {
        System.out.println();
        System.out.println("The last five sentences:");
        for(int i = sentences.size() - 6; i < sentences.size() - 1; i++) {
            System.out.println("(" + (i + 1) + ") " + sentences.get(i));
        }


        System.out.println();
        System.out.println("Summary statistics:");
        System.out.println("Letters: " + countLetters());
        System.out.println("Words: " + countWords());
        System.out.println("Sentences: " + countSentences());
        System.out.println("Readability: " + calculateReadability());
    }


}
