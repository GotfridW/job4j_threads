package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> updateFunction = (key, stored) -> {
            if (model.version() != stored.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(key, model.name(), stored.version() + 1);
        };

        return memory.computeIfPresent(model.id(), updateFunction) != null;
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
