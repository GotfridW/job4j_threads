package ru.job4j.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {
    private Cache cache;
    private Base base;

    @BeforeEach
    void setUp() {
        base = new Base(1, "Base", 1);
        cache = new Cache();
        cache.add(base);
    }

    @Test
    void whenAddFind() throws OptimisticException {
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    void whenAddUpdateFind() throws OptimisticException {
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
    }

    @Test
    void whenAddDeleteFind() throws OptimisticException {
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    void whenMultiUpdateThrowException() throws OptimisticException {
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }
}