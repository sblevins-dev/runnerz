package dev.stephenblevins.runnerz.run;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcClientRunRepository {

    private List<Run> runs = new ArrayList<>();

    private final JdbcClient jdbcClient;

    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("select id, title, started_on, completed_on, miles, " +
                        "location FROM Run WHERE id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    public void create(Run run) {
        var created = jdbcClient.sql("INSERT INTO Run(id, title, started_on, completed_on, miles, location)" +
                " values(?,?,?,?,?,?)")
                .params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString()))
                .update();

        Assert.state(created == 1, "Failed to create run " + run.title());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.title());
    }

    public void delete(Integer id) {
        var deleted = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(deleted == 1, "Failed to delete run with id of: " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Run")
                .query()
                .listOfRows()
                .size();
    }

    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM Run WHERE location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }

//    // get
//    List<Run> findAll() {
//        return runs;
//    }
//
//    Optional<Run> findById(int id) {
//        return runs.stream()
//                .filter(run -> run.id() == id)
//                .findFirst();
//    }
//
//    // post
//    void create(Run run) {
//        runs.add(run);
//    }
//
//    @PostConstruct
//    private void init() {
//        runs.add(new Run(
//                1,
//                "Monday Morning Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
//                3,
//                Location.INDOOR
//        ));
//
//        runs.add(new Run(
//                2,
//                "Wednesday Evening Run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
//                6,
//                Location.INDOOR
//        ));
//    }
//
//    // update
//    void update(int id, Run run) {
//        Optional<Run> existingRun = findById(id);
//        if (existingRun.isPresent()) {
//            runs.set(runs.indexOf((Run) existingRun.get()), run);
//        }
//    }
//
//    // delete
//    void delete(int id) {
//        runs.removeIf(run -> run.id().equals(id));
//    }
}
