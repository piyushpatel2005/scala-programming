import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScatterPlot2 {
    private static char[][] points = new char[41][21];
    private static int[][] xPoints = new int[41][21];
    private static int[][] regressionPoints = new int[41][21];
    private static double meanX;
    private static double meanY;
    private static double slope;

    /**
     * Initialize 2D array with value as ' '. Not the most efficient way, but still works.
     */
    private static void initPoints() {
        for(int i = 0; i < points.length; i++) {
            for(int j = 0; j < 21; j++) {
                points[i][j] = ' ';
            }
        }
    }

    /**
     * Read file and mark that point as 'x' as well as mark another 2D array. Also, check the values are valid.
     * @param file
     * @throws FileNotFoundException
     */
    private static void readFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x;
            int y;
            int sumX = 0;
            int sumY = 0;
            int count = 0;
            String[] values = line.split(" ");
            try {
                x = Integer.parseInt(values[0]);
                y = Integer.parseInt(values[1]);
                if (x >= 0 && x <= 40 && y >= 1 && y <= 20) {
                    xPoints[x][y] = 1;
                    points[x][y] = 'x';
                    sumX += x;
                    sumY += y;
                    count += 1;
                }
                    double meanX = calculateMean(sumX, count);
                    double meanY = calculateMean(sumY, count);
                    double slope = calculateSlope(meanX, meanY, count);
//                System.out.println("meanX : " + meanX + ", meanY : " + meanY + ", slope : " + slope);
//                    markRegressionPoints(meanX, meanY, slope);

            } catch (NumberFormatException nfe) {
//                System.out.println("Number format exception with line : " + line);
            } catch (IllegalArgumentException iae){
//                System.out.println("Illegal argument passed.");
            }
        }
    }

    /**
     * calculate slope, mean X and mean Y
     */
    private static void calculateParameters() {
        int sumX = 0;
        int sumY = 0;
        int count = 0;
        int multiplicationXY = 0;
        int xSquared = 0;
        for(int i = 0; i < 41; i++) {
            for(int j = 0; j < 21; j++) {
                if(xPoints[i][j] == 1) {
                    count += 1;
                    sumX += i;
                    sumY += j;
                    multiplicationXY += i * j;
                    xSquared += i * i;
                }

            }
        }
        meanX = sumX / (double)count;
        meanY = sumY / (double)count;
        slope = (multiplicationXY - count * meanX * meanY) / (xSquared - count * Math.pow(meanX, 2));
//        System.out.println("meanX : " + meanX + ", meanY: " + meanY + ", slope: " + slope);
    }

    /**
     * This method was used during developments to print the points
     * @param arr
     */
    private static void printPoints(int[][] arr) {
        for(int i = 0; i < 41; i++) {
            for(int j = 0; j < 21; j++) {
                if(arr[i][j] == 1) {
//                    System.out.printf("%d,%d\n", i, j);
                }
            }
        }
    }

    /**
     * calculate slope from passed parameters
     * @param meanX
     * @param meanY
     * @param n
     * @return
     */
    private static double calculateSlope(double meanX, double meanY, int n) {

        int multiplicationOfXY = 0;
        int xSquared = 0;
        for(int i = 0; i < 41; i++) {
            for(int j = 1; j < 21; j++) {
                if(points[i][j] == 'x') {
                    multiplicationOfXY += i * j;
                    xSquared += Math.pow(i, 2);
                }
            }
        }
        return (multiplicationOfXY - n * meanX * meanY) / (xSquared - n * Math.pow(meanX, 2));

    }

    // calculate regression points for each value of x and mark them on 2d array.
    private static void markRegressionPoints(double meanX, double meanY, double slope) {

        // find regression point and see if it is already marked as 'x'
        for(int x = 0; x < 41;x++) {
            int y = (int)(meanY + slope * (x - meanX));
            if (y >= 1 && y <= 20 && xPoints[x][y] == 1) {
                points[x][y] = '*';
            } else if(y >= 1 && y <= 20) {
                points[x][y] = '-';
            } else {
                points[x][y] = ' ';
            }
        }
    }

    private static double calculateMean(int sum, int count) {
        return ((double)(sum)) / count;
    }

    public static void main(String[] args) {
        // initialize array with either ' '
        initPoints();
        try {
            // read input points and mark them on array
            readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\a3plot1.txt");
            // helper method to calculate mean and slope to use in next method.
            calculateParameters();
            // mark regression points on array
            markRegressionPoints(meanX, meanY, slope);
            // plot points
            plotPoints();
        } catch (Exception fnf) {
            fnf.printStackTrace();
        }
    }

    /**
     * plot the 2D array as graph.
     */
    private static void plotPoints() {
        // i represents lines
        for(int i = 20; i > 0; i--) {
            System.out.print("/");
            for(int j = 0; j <= 40; j++) {
                System.out.print(points[j][i]);
            }
            System.out.println();
        }
        System.out.print("+");
        for(int i = 0; i < 40; i++)
            System.out.print("-");
    }

}
