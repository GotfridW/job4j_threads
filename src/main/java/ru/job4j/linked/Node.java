package ru.job4j.linked;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Node<T> {
    private final Node<T> next;
    private final T value;
}
