import java.util.Objects;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(x >= 0 && x <=40)
            this.x = x;
        else
            throw new IllegalArgumentException("value of x must be between 0 and 41 but passed :" + x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        if (y >= 1 && y <= 20)
            this.y = y;
        else
            throw new IllegalArgumentException("value of y must be between 1 and 21 but pased :" + y);

    }

    @Override
    public String toString() {
        return "x: " + this.getX() + ", y: " + this.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
