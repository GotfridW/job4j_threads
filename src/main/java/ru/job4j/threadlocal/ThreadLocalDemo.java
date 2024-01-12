package ru.job4j.threadlocal;

public class ThreadLocalDemo {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThreat();
        threadLocal.set("This is main thread");
        System.out.println(threadLocal.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
