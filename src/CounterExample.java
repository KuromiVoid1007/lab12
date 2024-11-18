import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CounterExample {
    private int counter = 0;

    // Синхронизированный метод увеличения счётчика
    public synchronized void increment() {
        counter++;
    }

    // Синхронизированный метод уменьшения счётчика
    public synchronized void decrement() {
        counter--;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        CounterExample counterExample = new CounterExample();

        // Создаём пул потоков с двумя потоками
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Первый поток увеличивает счётчик от 0 до 1000
        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counterExample.increment();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Второй поток уменьшает счётчик от 1000 до 0
        Runnable decrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counterExample.decrement();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executor.submit(incrementTask);
        executor.submit(decrementTask);

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Конечное значение счётчика: " + counterExample.getCounter());
    }
}
