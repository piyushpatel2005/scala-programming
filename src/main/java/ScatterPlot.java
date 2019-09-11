import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ScatterPlot {
    private static List<Point> points = new ArrayList<>();

    private static void readFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            int x;
            int y;
            String[] values = line.split(" ");
            try {
                x = Integer.parseInt(values[0]);
                y = Integer.parseInt(values[1]);
                points.add(new Point(x,y));
            } catch (NumberFormatException nfe) {
//                System.out.println("Number format exception with line : " + line);
            } catch (IllegalArgumentException iae){
//                System.out.println("Illegal argument passed.");
            }
        }
    }

    private static Point calculateMeanPoint() {
        int sumX = 0;
        int sumY = 0;
        for(Point point: points) {
            sumX += point.getX();
            sumY += point.getY();
        }
        return new Point(sumX / points.size(), sumY / points.size());
    }

    public static void main(String[] args) {
        try {
            readFile("C:\\Users\\piypatel\\Downloads\\workspace\\scala-programming\\src\\main\\java\\test4.txt");
        } catch (Exception fnf) {
            fnf.printStackTrace();
        }
        plotPoints();
//        displayPointsList(getRegressionPoints());
    }

    private static void plotPoints() {
        // i represents lines
        for(int i = 20; i > -1; i--) {
            // j represents columns
        List<Point> pointsList = new ArrayList<>();
        pointsList = getPointsWithYvalue(i, points);
        List<Point> regressionPoints = getPointsWithYvalue(i, getRegressionPoints());
            for(int j = -1; j <= 40; j++) {
                if(i == 0 && j == -1){
                    System.out.print("+");
                } else if (i == 0) {
                    System.out.print("-");
                } else if (j == -1) {
                    System.out.print("/");
                } else if(i >= 0 && i <=20 && j >= 0 && j <=40){
                    Point currentPoint = new Point(j, i);
//                    System.out.println("Current Point: " + currentPoint);
                    if(pointsList.contains(currentPoint) && regressionPoints.contains(currentPoint)){
                        System.out.print("*");
                    }
                    else if (pointsList.contains(currentPoint)) {
                        System.out.print("x");
                    } else if (regressionPoints.contains(currentPoint)) {
                        System.out.print("-");
                    } else {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    private static List<Point> getPointsWithYvalue(int yValue, List<Point> points) {
        List<Point> yPoints = new ArrayList<>();
        for(Point p: points) {
            if(p.getY() == yValue) {
                yPoints.add(p);
            }
        }
        return yPoints;
    }

    private static double calculateSlope() {
        double sumX = 0;
        double sumY = 0;
        for(Point point: points) {
            sumX += point.getX();
            sumY += point.getY();
        }
        int n = points.size();
        double xBar = sumX / n;
        double yBar = sumY / n;
        int squareOfXY = 0;
        int xSquared = 0;
        for(Point point: points) {
            xSquared += Math.pow(point.getX(), 2);
            squareOfXY += point.getY() * point.getX();
        }
        double numerator = squareOfXY - n * xBar * yBar;
        double denominator = xSquared - n * Math.pow(xBar, 2);
        return numerator / denominator;
    }

    private static List<Point> getRegressionPoints() {
        double sumX = 0;
        double sumY = 0;
        for(Point point: points) {
            sumX += point.getX();
            sumY += point.getY();
        }

        int n = points.size();
//        displayPointsList(points);
        double xBar = sumX / n;
        double yBar = sumY / n;
        double slope = calculateSlope();
//        System.out.println("xBar : " +xBar + ", yBar  : " + yBar + ", slope : " + slope); // 16, 13.33, 0.329268
        List<Point> regressionPoints = new ArrayList<>();
        for(int x = 0; x <= 40; x++) {
            int y = (int)(yBar + slope * (x - xBar));
            if (y >= 1 && y <= 20)
                regressionPoints.add(new Point(x, (int)y));
        }
        return regressionPoints;
    }

    private static void displayPointsList(List<Point> points) {
        for(Point point: points) {
            System.out.println(point);
        }
    }
}
