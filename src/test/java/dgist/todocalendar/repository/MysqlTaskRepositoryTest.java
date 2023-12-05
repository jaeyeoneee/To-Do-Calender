package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.dto.task.TaskUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MysqlTaskRepositoryTest {

    @Autowired MysqlProjectRepository mysqlProjectRepository;
    @Autowired MysqlMemberRepository mysqlMemberRepository;
    @Autowired MysqlTaskRepository mysqlTaskRepository;

    @Test
    @Transactional
    void crud(){
        //save
        MemberJoinDto member = new MemberJoinDto("testName", "test@dgist.ac.kr", "testPassword");
        Long memberId = mysqlMemberRepository.save(member);
        ProjectSaveDto project = new ProjectSaveDto("testName", memberId);
        Long projectId = mysqlProjectRepository.save(project);
        TaskSaveDto task = new TaskSaveDto("testName", LocalDate.parse("2023-12-04"), projectId);
        Long taskId = mysqlTaskRepository.save(task);

        //findById
        Optional<Task> optionalFindTask = mysqlTaskRepository.findById(taskId);
        Task findTask = optionalFindTask.get();
        assertThat(findTask.getTaskName()).isEqualTo(task.getTaskName());
        assertThat(findTask.getDate()).isEqualTo(task.getDate());
        assertThat(findTask.getProjectId()).isEqualTo(task.getProjectId());

        //update
        TaskUpdateDto updateTask = new TaskUpdateDto("updateName", LocalDate.parse("2023-12-01"));
        mysqlTaskRepository.update(taskId, updateTask);
        Optional<Task> optionalUpdateFindTask = mysqlTaskRepository.findById(taskId);
        Task updateFindTask = optionalUpdateFindTask.get();
        assertThat(updateFindTask.getTaskName()).isEqualTo(updateTask.getTaskName());
        assertThat(updateFindTask.getDate()).isEqualTo(updateTask.getDate());

        //delete
        mysqlTaskRepository.delete(taskId);
        Optional<Task> optionalDeleteFindTask = mysqlTaskRepository.findById(taskId);
        assertThat(optionalDeleteFindTask.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    void findTasksByProjectsId(){
        //save
        MemberJoinDto member = new MemberJoinDto("testName", "test@dgist.ac.kr", "testPassword");
        Long memberId = mysqlMemberRepository.save(member);
        ProjectSaveDto project1 = new ProjectSaveDto("testName1", memberId);
        Long projectId1 = mysqlProjectRepository.save(project1);
        TaskSaveDto task1 = new TaskSaveDto("testName1", LocalDate.parse("2023-12-04"), projectId1);
        TaskSaveDto task2 = new TaskSaveDto("testName2", LocalDate.parse("2023-12-04"), projectId1);
        Long taskId1 = mysqlTaskRepository.save(task1);
        Long taskId2 = mysqlTaskRepository.save(task2);

        List<Task> tasksByProjectId = mysqlTaskRepository.findTasksByProjectId(projectId1);
        assertEquals(2, tasksByProjectId.size());
        assertTrue(tasksByProjectId.stream().anyMatch(p -> p.getTaskId().equals(taskId1)));
        assertTrue(tasksByProjectId.stream().anyMatch(p -> p.getTaskId().equals(taskId2)));

    }

    @Test
    @Transactional
    void countTasksByProjectId() {
        MemberJoinDto member = new MemberJoinDto("testName", "test@dgist.ac.kr", "testPassword");
        Long memberId = mysqlMemberRepository.save(member);
        ProjectSaveDto project1 = new ProjectSaveDto("testName1", memberId);
        Long projectId1 = mysqlProjectRepository.save(project1);
        TaskSaveDto task1 = new TaskSaveDto("testName1", LocalDate.parse("2023-12-04"), projectId1);
        TaskSaveDto task2 = new TaskSaveDto("testName2", LocalDate.parse("2023-12-04"), projectId1);
        Long taskId1 = mysqlTaskRepository.save(task1);
        Long taskId2 = mysqlTaskRepository.save(task2);

        Integer countTask = mysqlTaskRepository.countTasksByProjectId(projectId1);
        assertEquals(2, countTask);

    }
}