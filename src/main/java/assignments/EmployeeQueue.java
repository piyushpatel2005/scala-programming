package assignments;

class EmployeeNode {
    Employee employee;
    EmployeeNode next;
}

public class EmployeeQueue {
    EmployeeNode top, end;

    public void enQueue(EmployeeQueue queue, Employee e) {
        EmployeeNode tmp = new EmployeeNode();
        tmp.employee = e;
        if (queue.top == null)
            queue.top = tmp;
        else
            queue.end.next = tmp;

        queue.end = tmp;
        queue.end.next = queue.top;
    }

    public Employee deQueue(EmployeeQueue queue) {
        if (queue.top == null) {
            return null;
        }

        Employee e;
        if (queue.top == queue.end) {
            e = queue.top.employee;
            queue.top = null;
            queue.end = null;
        } else {
            EmployeeNode newNode = queue.top;
            e = newNode.employee;
            queue.top = queue.top.next;
            queue.end.next = queue.top;
        }
        return e;
    }
}
