package dgist.todocalendar.service;

import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.repository.MysqlMemberRepository;
import dgist.todocalendar.repository.MysqlProjectRepository;
import dgist.todocalendar.repository.MysqlTaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    MysqlMemberRepository mysqlMemberRepository;
    @Autowired
    MysqlProjectRepository mysqlProjectRepository;

    @Autowired
    MysqlTaskRepository mysqlTaskRepository;
    @Autowired
    TaskService taskService;

    @Test
    @Transactional
    void findTasksByProjectIdsTest() {
        MemberJoinDto member = new MemberJoinDto("testName", "test@dgist.ac.kr", "testPassword");
        Long memberId = mysqlMemberRepository.save(member);
        ProjectSaveDto project1 = new ProjectSaveDto("testName1", memberId);
        ProjectSaveDto project2 = new ProjectSaveDto("testName2", memberId);
        Long projectId1 = mysqlProjectRepository.save(project1);
        Long projectId2 = mysqlProjectRepository.save(project2);
        TaskSaveDto task1 = new TaskSaveDto("testName1", LocalDate.parse("2023-12-04"), projectId1);
        TaskSaveDto task2 = new TaskSaveDto("testName2", LocalDate.parse("2023-12-04"), projectId2);
        Long taskId1 = mysqlTaskRepository.save(task1);
        Long taskId2 = mysqlTaskRepository.save(task2);

        List<Long> projectIds = Arrays.asList(projectId1, projectId2);
        List<Task> tasksByProjectIds = taskService.findTasksByProjectIds(projectIds);
        assertEquals(2, tasksByProjectIds.size());
        assertTrue(tasksByProjectIds.stream().anyMatch(p -> p.getTaskId().equals(taskId1)));
        assertTrue(tasksByProjectIds.stream().anyMatch(p -> p.getTaskId().equals(taskId2)));

    }

}