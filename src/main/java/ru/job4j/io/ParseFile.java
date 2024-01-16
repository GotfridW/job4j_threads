package ru.job4j.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> predicate) {
        StringBuilder builder = new StringBuilder();
        try (var in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) != -1) {
                if (predicate.test((char) data)) {
                    builder.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public synchronized String getAllContent() {
        return getContent(character -> true);
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(character -> character < 0x80);
    }
}
