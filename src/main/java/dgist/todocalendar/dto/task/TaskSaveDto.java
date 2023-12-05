package dgist.todocalendar.dto.task;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskSaveDto {
    private String taskName;
    private LocalDate date;
    //foreign key
    private Long projectId;

    public TaskSaveDto() {
    }

    public TaskSaveDto(String taskName, LocalDate date, Long projectId) {
        this.taskName = taskName;
        this.date = date;
        this.projectId = projectId;
    }
}
