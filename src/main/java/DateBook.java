import java.util.Random;

public class DateBook {

    private static String weekdays[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private final static int september = 30;
    private final static int october = 31;
    String eventName[] = {"Meet friend", "Buy Groceries", "Do Homework", "Finish assignment", "Read novel", "Attend seminar", "Interview with Joshua", "Attend Mr.Zakir's lecture", "Meet uncle", "Work"};

    Random rand = new Random();

    public String generateRandomStartTime() {
        String flag[] = {"AM", "PM"};
        int hour = rand.nextInt(12) + 1;
        int minute = rand.nextInt(59) + 1;
        int amPm = rand.nextInt(2);
        return String.valueOf(hour) + ":" + String.valueOf(minute) + " " + flag[amPm];
    }

//    public String generateRandomEventName() {
//        String eventName[] = {"Meet friend", "Buy Groceries", "Do Homework", "Finish assignment", "Read novel", "Attend seminar", "Interview with Joshua", "Attend Mr.Zakir's lecture", "Meet uncle", "Work"};
//        return eventName[rand.nextInt(10)];
//    }

    public Event[] generateEvents(int days) {
        int eventsNum = 10;
        int day, priority;
        Event events [] = new Event[days];
        for (int i = 0; i < eventsNum; i++) {
            day = rand.nextInt(days);
            while(events[day] != null) {
                day = rand.nextInt(days);
            }
            priority = rand.nextInt(3) + 1;
            events[day] = new Event(generateRandomStartTime(), this.eventName[i], priority);
        }
        return events;
    }

    public static void outputCalendarView(Event e[], int firstDayOfMonth, int days) {
        for(String s: weekdays) {
            System.out.print(s + "\t");
        }
        System.out.println();
        int i = 0;
        int count = 0;
        while (i < days) {
            while (firstDayOfMonth > 1) {
                System.out.print("\t");
                firstDayOfMonth--;
                count++;

            }

            if (count % 7 == 0 && count != 0) {
                System.out.println();
            }

            if (e[i] == null) {
                System.out.print(i + 1 + "\t");
            } else {
                System.out.print(i + 1 + "*\t");
            }
            i++;
            count++;
        }
    }

    public static void outputEventView(Event event[]) {
        System.out.println();
        System.out.println("Events:");
        for(int i = 0; i < event.length; i++) {
            if(event[i] != null) {
                System.out.println(i + 1 + event[i].toString());
            }
        }
    }

    public static void outputPriorityView(Event event[]) {
        System.out.println();
        System.out.println("High-Priority Events:");
        for(int i = 0; i < event.length; i++) {
            if(event[i] != null) {
                if(event[i].getEventPriority() == 3) {
                    System.out.println(i + 1 + ": " + event[i].getEventName());
                }
            }
        }
    }

    public static void main(String[] args) {
        int rand = (int) (Math.random() * 6) + 1;
        Event sept[];
        DateBook dateBook = new DateBook();
        sept = dateBook.generateEvents(september);
        System.out.println("---------------SEP--------------");
        System.out.println();
        DateBook.outputCalendarView(sept, rand, september);
        System.out.println();
        DateBook.outputEventView(sept);
        DateBook.outputPriorityView(sept);

        System.out.println("---------------OCT--------------");
        System.out.println();
        Event oct[];
        oct = dateBook.generateEvents(october);
        int firstDay = (30 + rand) % 7;
        DateBook.outputCalendarView(oct, firstDay, october);
        System.out.println();
        DateBook.outputEventView(oct);
        DateBook.outputPriorityView(oct);
    }
}
