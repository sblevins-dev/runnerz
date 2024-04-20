package dev.stephenblevins.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {

    InMemoryRunRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryRunRepository();
        repo.create(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR, null));

        repo.create(new Run(2,
                "Wednesday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                6,
                Location.INDOOR, null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repo.findAll();
        assertEquals(2, runs.size(), "Should have returned 2 runs");
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repo.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(3, run.miles());
    }

    @Test
    void shouldNotFindRunWithInvalidId() {
        RunNotFoundException notFoundException = assertThrows(
                RunNotFoundException.class,
                () -> repo.findById(3).get()
        );

        assertEquals("Run Not Found", notFoundException.getMessage());
    }

    @Test
    void shouldCreateNewRun() {
        repo.create(new Run(3,
                "Friday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR, null));
        List<Run> runs = repo.findAll();
        assertEquals(3, runs.size());
    }

    @Test
    void shouldUpdateRun() {
        repo.update(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR, null), 1);
        var run = repo.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(5, run.miles());
        assertEquals(Location.OUTDOOR, run.location());
    }

    @Test
    void shouldDeleteRun() {
        repo.delete(1);
        List<Run> runs = repo.findAll();
        assertEquals(1, runs.size());
    }
}