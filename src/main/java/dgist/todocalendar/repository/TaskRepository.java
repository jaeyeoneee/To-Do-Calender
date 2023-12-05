package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.dto.task.TaskUpdateDto;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Long save(TaskSaveDto taskSaveDto);
    void delete(Long taskId);
    void update(Long taskId, TaskUpdateDto taskUpdateDto);
    Optional<Task> findById(Long taskId);
    List<Task> findTasksByProjectId(Long projectId);

    Integer countTasksByProjectId(Long projectId);
}
