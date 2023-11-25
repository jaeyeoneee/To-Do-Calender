package dgist.todocalendar.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Task {

    private Long taskId;

    private String taskName;
    private LocalDate date;
    //foreign key
    private Long projectId;

    public Task() {
    }

    public Task(Long taskId, String taskName, LocalDate date, Long projectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.date = date;
        this.projectId = projectId;
    }
}
