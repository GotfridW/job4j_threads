package ru.job4j.pool;

import ru.job4j.concurrent.ConsoleProgress;
import ru.job4j.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        threadPool.work(new ConsoleProgress());
        threadPool.work(new ConsoleProgress());
        threadPool.threads.forEach(thread ->
                System.out.printf("%n%s: %s", thread.getName(), thread.getState()));
        Thread.sleep(4000);
        threadPool.shutdown();
        Thread.sleep(500);
        threadPool.threads.forEach(thread ->
                System.out.println(thread.getName() + ": " + thread.getState()));
    }
}
