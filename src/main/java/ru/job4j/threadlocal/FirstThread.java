package ru.job4j.threadlocal;

public class FirstThread extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("This is Thread 1");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
