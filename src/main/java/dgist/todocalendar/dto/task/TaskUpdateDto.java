package dgist.todocalendar.dto.task;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDto {
    private String taskName;
    private LocalDate date;

    public TaskUpdateDto() {
    }

    public TaskUpdateDto(String taskName, LocalDate date) {
        this.taskName = taskName;
        this.date = date;
    }
}
