package ru.job4j;

public final class DSLSingleton {

    private static volatile DSLSingleton instance;

    public static DSLSingleton getInstance() {
        if (instance == null) {
            synchronized (DSLSingleton.class) {
                if (instance == null) {
                    instance = new DSLSingleton();
                }
            }
        }
        return instance;
    }

    public DSLSingleton() {
    }
}
