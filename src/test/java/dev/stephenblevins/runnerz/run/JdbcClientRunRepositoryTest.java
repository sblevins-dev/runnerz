package dev.stephenblevins.runnerz.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcClientRunRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcClientRunRepositoryTest {

    @Autowired
    JdbcClientRunRepository repo;

    @BeforeEach
    void setUp() {
        repo.create(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR, null));

        repo.create(new Run(2,
                "Wednesday Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                6,
                Location.INDOOR, null));
    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repo.findAll();
        assertEquals(2, runs.size());
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repo.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(3, run.miles());
    }

    @Test
    void shouldNotFindRunWithInvalidId() {
        var run = repo.findById(3);
        assertTrue(run.isEmpty());
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