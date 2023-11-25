package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.task.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(TaskDto taskDto);
    void delete(Long taskId);
    void update(Long taskId, TaskDto taskDto);
    Optional<Task> findById(Long taskId);
    List<Task> findTasksByProjectId(Long projectId);

}
