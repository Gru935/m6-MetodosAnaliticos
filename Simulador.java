import java.util.PriorityQueue;

public class Simulador {
    public static PriorityQueue<Event> scheduler = new PriorityQueue<>();
    public static final int capacity = 5;
    public static int size = 0;
    public static double global_time = 0.0;
    public static double[] times = new double[capacity + 1];
    public static int rounds = 100000;
    public static final int arrival_a = 2;
    public static final int arrival_b = 5;
    public static final int exit_a = 3;
    public static final int exit_b = 5;
    public static int x = 57406;
    public static final int a = 4212002;
    public static final int c = 2224621;
    public static final int m = 429496729;
    public static int losts = 0;

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
        System.out.println("State   Time                  Probability");
        for (int i = 0; i < times.length; i++) {
            System.out.println(i + "       " + times[i] + "    " + times[i] / global_time * 100 + "%");
        }
        System.out.println();
        System.out.println("Number os losses: " + losts);
        System.out.println("Global time: " + global_time);
    }

    public static double nextRandom() {
        x = ((a * x) + c) % m;
        return ((double) x) / m;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static void arrival(Event e) {
        times[size] += e.getTime() - global_time;
        global_time = e.getTime();
        if (size < capacity) {
            size++;
            if (size <= 1) {
                scheduleExit();
            }
        } else {
            losts++;
        }
        scheduleArrival();
    }

    public static void exit(Event e) {
        times[size] += e.getTime() - global_time;
        global_time = e.getTime();
        size--;
        if (size >= 1) {
            scheduleExit();
        }
    }

    public static void scheduleArrival() {
        double u = arrival_a + ((arrival_b - arrival_a) * nextRandom());
        Event event = new Event(u + global_time, EventType.ARRIVAL);
        scheduler.add(event);
    }

    public static void scheduleExit() {
        double u = exit_a + ((exit_b - exit_a) * nextRandom());
        Event event = new Event(u + global_time, EventType.EXIT);
        scheduler.add(event);
    }
}