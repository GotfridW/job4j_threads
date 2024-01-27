package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    private final int capacity;

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= capacity) {
            wait();
        }
        queue.add(value);
        System.out.println("Value added to the queue: " + value);
        notify();
    }

    public synchronized T poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        T value = queue.poll();
        notify();
        return value;
    }
}