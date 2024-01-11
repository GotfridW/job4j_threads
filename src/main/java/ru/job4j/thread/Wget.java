package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static java.lang.System.currentTimeMillis;

public class Wget implements Runnable {
    private static final int ONE_SECOND = 1000;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        if (args[0] == null || args[1] == null) {
            throw new IllegalArgumentException("One of the arguments was null");
        }
        try {
            new URL(args[0]).toURI();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL argument");
        }
        if (!args[1].matches("^\\d+$")) {
            throw new IllegalArgumentException("Invalid speed argument");
        }
    }

    private void delay(int bytesRead, long loadTime) {
        long sleepTime = (long) ((double) bytesRead / this.speed * ONE_SECOND) - loadTime;
        if (sleepTime > 0) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = Paths.get("data", fileName).toFile();
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead, bytesCounter = 0;
            long start = currentTimeMillis(), loadStart = start;

            while (!Thread.currentThread().isInterrupted()
                    && (bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                long loadTime = currentTimeMillis() - loadStart;
                output.write(dataBuffer, 0, bytesRead);
                bytesCounter += bytesRead;

                if (bytesCounter >= speed) {
                    delay(bytesCounter, loadTime);
                    bytesCounter = 0;
                    loadStart = currentTimeMillis();
                } else if (input.available() == 0) {
                    delay(bytesCounter, loadTime);
                }
            }

            long finish = currentTimeMillis();
            System.out.printf("Downloaded %d bytes in %d ms, download rate - %sb/s",
                    file.length(), finish - start, speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        validate(args);
        Thread wget = new Thread(new Wget(args[0], Integer.parseInt(args[1])));
        wget.start();
        try {
            wget.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Program aborted");
        }
    }
}
