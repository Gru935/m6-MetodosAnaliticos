public class Event {
    private double time;
    private EventType type;

    public Event(double time, EventType type) {
        this.time = time;
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public EventType getType() {
        return type;
    }

}
