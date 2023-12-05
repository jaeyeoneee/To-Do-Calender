package dgist.todocalendar.service;

import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.dto.task.TaskUpdateDto;
import dgist.todocalendar.repository.MysqlTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final MysqlTaskRepository mysqlTaskRepository;

    public List<Task> findTasksByProjectIds(List<Long> projectIds) {
        List<Task> tasks = projectIds.stream()
                .map(mysqlTaskRepository::findTasksByProjectId)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return tasks;
    }

    public Integer countTasksByProjectId(Long projectId) {
        return mysqlTaskRepository.countTasksByProjectId(projectId);
    }

    public Task save(TaskSaveDto taskSaveDto) {
        Long taskId = mysqlTaskRepository.save(taskSaveDto);
        return mysqlTaskRepository.findById(taskId).get();
    }

    public Task update(Long taskId, TaskUpdateDto taskUpdateDto) {
        mysqlTaskRepository.update(taskId, taskUpdateDto);
        return mysqlTaskRepository.findById(taskId).get();
    }

    public void delete(Long taskId) {
        mysqlTaskRepository.delete(taskId);
    }
}
