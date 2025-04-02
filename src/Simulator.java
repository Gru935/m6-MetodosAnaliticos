package src;

import java.util.PriorityQueue;

public class Simulator {
    public static PriorityQueue<Event> scheduler = new PriorityQueue<>();
    public static Queue queue;
    public static double global_time = 0.0;
    public static int x = 57406;

    public static void main(String[] args) {
        queue = new Queue(2, 3, 1, 4, 3, 4, 0, 0, null);
        queue.setTimes(new double[queue.getCapacity() + 1]);

        Event first = new Event(1.5, EventType.ARRIVAL);
        scheduler.add(first);

        int rounds = 100000;
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
        for (int i = 0; i < queue.getTimes().length; i++) {
            System.out.println(
                    i + "       " + queue.getTimes()[i] + "    " + queue.getTimes()[i] / global_time * 100 + "%");
        }
        System.out.println();
        System.out.println("Number os losses: " + queue.getLosses());
        System.out.println("Global time: " + global_time);
    }

    public static double nextRandom() {
        x = ((4212002 * x) + 2224621) % 429496729;
        return ((double) x) / 429496729;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static void arrival(Event e) {
        queue.getTimes()[queue.getCustomers()] += e.getTime() - global_time;
        global_time = e.getTime();
        if (queue.getCustomers() < queue.getCapacity()) {
            queue.In();
            if (queue.getCustomers() <= queue.getServers()) {
                scheduleExit();
            }
        } else {
            queue.Loss();
        }
        scheduleArrival();
    }

    public static void exit(Event e) {
        queue.getTimes()[queue.getCustomers()] += e.getTime() - global_time;
        global_time = e.getTime();
        queue.Out();
        if (queue.getCustomers() >= queue.getServers()) {
            scheduleExit();
        }
    }

    public static void scheduleArrival() {
        double u = queue.getMinArrival() + ((queue.getMaxArrival() - queue.getMinArrival()) * nextRandom());
        Event event = new Event(u + global_time, EventType.ARRIVAL);
        scheduler.add(event);
    }

    public static void scheduleExit() {
        double u = queue.getMinService() + ((queue.getMaxService() - queue.getMinService()) * nextRandom());
        Event event = new Event(u + global_time, EventType.EXIT);
        scheduler.add(event);
    }
}