package assignments;

import java.util.Objects;

public class Employee {

    private int id;
    private int arrivalTime;
    private int arrivalFloor;
    private int desiredFloor;

    public Employee(int id, int arrivalTime, int arrivalFloor, int desiredFloor) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.arrivalFloor = arrivalFloor;
        this.desiredFloor = desiredFloor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getArrivalFloor() {
        return arrivalFloor;
    }

    public void setArrivalFloor(int arrivalFloor) {
        this.arrivalFloor = arrivalFloor;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return "Employee " + this.getId() + ", arrival floor " + this.getArrivalFloor() + ", arrival time " + this.getArrivalTime() + ", desired floor " + this.getDesiredFloor();
    }
}
