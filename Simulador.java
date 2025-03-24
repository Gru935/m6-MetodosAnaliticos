import java.util.PriorityQueue;

public class Simulador {
    public static PriorityQueue<Event> scheduler = new PriorityQueue<>();
    public static final int capacity = 3;
    public static int size = 0;
    public static double total_time = 0.0;

    public static void main(String[] args) {
        int rounds = 100000;
        Event first = new Event(2.0, EventType.ARRIVAL);
        scheduler.add(first);

        while (rounds > 0) {
            Event event = nextEvent();

            if (event.getType() == EventType.ARRIVAL) {
                arrival(event);
            } else {
                exit(event);
            }

        }

    }

    public static double nextRandom(double n, int a, int c, double m) {
        double next = ((a * n) + c) % m;
        return next / m;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static void arrival(Event e) {
        // total_time = e.getTime() - total_time;
        if (size < capacity) {
            size++;
            if (size <= 1) {
                scheduleExit();
            }
        }
        scheduleArrival();
    }

    public static void exit(Event e) {

    }

    public static void scheduleArrival() {

    }

    public static void scheduleExit() {

    }
}