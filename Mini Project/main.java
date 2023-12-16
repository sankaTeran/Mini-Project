public class Process {
    int pid;
    int priority;
    int burstTime;

    public Process(int pid, int priority, int burstTime) {
        this.pid = pid;
        this.priority = priority;
        this.burstTime = burstTime;
    }
}

public class MultilevelQueue {
    ArrayList<Queue<Process>> queues;
    int quantum;

    public MultilevelQueue(int numQueues, int quantum) {
        this.queues = new ArrayList<>();
        this.quantum = quantum;

        for (int i = 0; i < numQueues; i++) {
            this.queues.add(new LinkedList<>());
        }
    }

    public void addProcess(Process process) {
        this.queues.get(process.priority).add(process);
    }

    public void execute() {
        for (Queue<Process> queue : queues) {
            while (!queue.isEmpty()) {
                Process process = queue.poll();
                System.out.println("Executing Process " + process.pid + " from Queue " + process.priority);
                process.burstTime -= quantum;
                if (process.burstTime > 0) {
                    queue.add(process);
                } else {
                    System.out.println("Process " + process.pid + " completed.");
                }
            }
        }
    }

    public static void main(String[] args) {
        // Creating different processes
        Process[] processes = {
                new Process(1, 0, 8),  // Process ID, Priority, Burst Time
                new Process(2, 1, 5),
                new Process(3, 0, 6),
                new Process(4, 2, 4)
        };

        // Creating a Multilevel Queue with 3 priority levels and time quantum of 3
        MultilevelQueue mlq = new MultilevelQueue(3, 3);

        // Adding processes to the queues
        for (Process process : processes) {
            mlq.addProcess(process);
        }

        // Executing processes in the queues
        mlq.execute();
    }
}