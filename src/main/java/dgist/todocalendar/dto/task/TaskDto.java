package dgist.todocalendar.dto.task;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private String taskName;
    private LocalDate date;

    public TaskDto() {
    }

    public TaskDto(String taskName, LocalDate date) {
        this.taskName = taskName;
        this.date = date;
    }
}
