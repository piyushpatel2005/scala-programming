public class TextTriangle {

    public static void main(String[] args) {
        printTriangle("abcdefghi", 0);
        printTriangle("abcdefghij", 0);
        printTriangle("disestablishmentarianism",0);
        printTriangle("+-! *# =~ ~= #* !-+",0);
    }

    public static void printTriangle(String text, int i) {
        String spaces = "";
        if (text.length() > 1) {
            if (text.length() % 2 == 0) {
                int currentLength = (text.length() - 2) / 2;
                String updatedString = text.substring(0, currentLength) + text.substring(text.length() - currentLength);
//            System.out.println(updatedString);
                printTriangle(updatedString, i + 1);
            } else {
                int currentLength = text.length() / 2;
                String updatedString = text.substring(0, currentLength) + text.substring(text.length() - currentLength + 1);
//            System.out.println(updatedString);
                printTriangle(updatedString, i + 1);
            }
        }

//        System.out.println("Running with text:" + text + " i as " + i);
        System.out.print(printSpaces("", i));
//        System.out.print(spaces);
        System.out.println(text);
    }

    public static String printSpaces(String text, int n) {
//        String text = "";
//        System.out.println(n);

        if (n == 0) {
            return text;
        }
        else {
            text = text + " ";
//            printSpaces(text, n -1);
        }
//        System.out.println(text + "|");
//        System.out.println("text "+ text + " has length " + text.length());
        return printSpaces(text, n -1);
    }
}
