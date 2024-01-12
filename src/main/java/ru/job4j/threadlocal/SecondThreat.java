package ru.job4j.threadlocal;

public class SecondThreat extends Thread {
    @Override
    public void run() {
        ThreadLocalDemo.threadLocal.set("This is Thread 2");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
