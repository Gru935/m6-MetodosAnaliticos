import java.util.PriorityQueue;

public class Simulador {
    public static PriorityQueue<Event> scheduler = new PriorityQueue<>();
    public static final int capacity = 5;
    public static int size = 0;
    public static double global_time = 0.0;
    public static double[] times = new double[capacity];
    public static int rounds = 100000;
    public static final int arrival_a = 2;
    public static final int arrival_b = 5;
    public static final int exit_a = 3;
    public static final int exit_b = 5;
    public static double x = 57406;

    public static void main(String[] args) {
        Event first = new Event(2.0, EventType.ARRIVAL);
        scheduler.add(first);

        while (rounds > 0) {
            Event event = nextEvent();

            if (event.getType() == EventType.ARRIVAL) {
                arrival(event);
            } else {
                exit(event);
            }
            rounds--;
        }
        // Realizar os prints dos conteudos exigidos

    }

    public static double nextRandom(double x, int a, int c, double m) {
        x = ((a * x) + c) % m;
        return x / m;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static void arrival(Event e) {
        times[size] = e.getTime() - global_time;
        global_time = e.getTime();
        if (size < capacity) {
            size++;
            if (size <= 1) {
                scheduleExit();
            }
        }
        scheduleArrival();
    }

    public static void exit(Event e) {
        times[size] = e.getTime() - global_time;
        global_time = e.getTime();
        size--;
        if (size >= 1) {
            scheduleExit();
        }
    }

    public static void scheduleArrival() {
        double u = arrival_a + ((arrival_b - arrival_a) * nextRandom(x, 4212002, 2224621, 429496729));
        Event event = new Event(u + global_time, EventType.ARRIVAL);
        scheduler.add(event);
    }

    public static void scheduleExit() {
        double u = exit_a + ((exit_b - exit_a) * nextRandom(x, 4212002, 2224621, 429496729));
        Event event = new Event(u + global_time, EventType.EXIT);
        scheduler.add(event);
    }
}