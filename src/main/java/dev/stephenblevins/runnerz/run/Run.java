package dev.stephenblevins.runnerz.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Run(
        @Id
    Integer id,
    @NotEmpty
    String title, 
    LocalDateTime startedOn, 
    LocalDateTime completedOn,
    @Positive
    Integer miles, 
    Location location,
        @Version
        Integer version
    ) 
{
//    public Run {
//        if(!completedOn.isAfter(startedOn)) {
//            throw new IllegalArgumentException("completed On must be after startedOn");
//        }
//    }
}
