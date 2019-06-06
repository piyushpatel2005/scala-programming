public class Event {

    private String startTime;
    private String eventName;
    private int eventPriority;

    public Event(String startTime, String eventName, int eventPriority ) {
        setStartTime(startTime);
        setEventName(eventName);
        setEventPriority(eventPriority);
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if (startTime.trim().length() == 0)
            System.out.println("Please, enter valid event time.");
        else
            this.startTime = startTime;
        return;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        if (eventName.trim().length() == 0)
            System.out.println("Please, enter valid event name.");
        else
            this.eventName = eventName;
        return;
    }

    public int getEventPriority() {
        return eventPriority;
    }

    public void setEventPriority(int eventPriority) {
        if (eventPriority >= 1 || eventPriority < 4)
            this.eventPriority = eventPriority;
        return;
    }

    @Override
    public String toString() {
        return ": " + startTime +
                ": " + eventName +
                "(priority " + eventPriority +
                ")";
    }
}
