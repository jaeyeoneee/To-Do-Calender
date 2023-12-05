package dgist.todocalendar.controller;

import dgist.todocalendar.argumentresolver.LoginMemberId;
import dgist.todocalendar.domain.Project;
import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.service.ProjectService;
import dgist.todocalendar.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final ProjectService projectService;
    private final TaskService taskService;

    @GetMapping("/calendar")
    public String calendarPageGet(@LoginMemberId Long memberId, Model model) {
        log.info("memberId={}", memberId);
        List<Project> projects = projectService.findProjectsByMemberId(memberId);
        model.addAttribute("projects", projects);
        log.info("projects={}", projects);

        return "calendar";
    }

    @PostMapping("/calendar/loadTasks")
    @ResponseBody
    public List<Task> loadTasks(@RequestBody List<Long> projectIds) {

        List<Task> tasksByProjectIds = taskService.findTasksByProjectIds(projectIds);
        log.info("tasksByProjectIds={}", tasksByProjectIds);
        return tasksByProjectIds;
    }

    @PostMapping("/calendar/taskSave")
    @ResponseBody
    public Task saveTask(@RequestParam String taskName,
                         @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate taskDate,
                         @RequestParam Long projectId) {

        //TODO: argumentResolver로 변환 필요
        log.info("taskName={}", taskName);
        log.info("taskDate={}", taskDate);
        log.info("projectId={}", projectId);

        TaskSaveDto taskSaveDto = new TaskSaveDto(taskName, taskDate, projectId);
        Task savedTask = taskService.save(taskSaveDto);

        return savedTask;
    }

    @PostMapping("/calendar/taskDelete")
    @ResponseBody
    public void deleteTask(@RequestParam Long taskId){
        log.info("deleteTaskId={}", taskId);
        taskService.delete(taskId);
    }

}
