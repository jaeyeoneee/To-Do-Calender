package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.project.ProjectUpdateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MysqlProjectRepositoryTest {

    @Autowired
    private MysqlMemberRepository mysqlMemberRepository;
    @Autowired
    private MysqlProjectRepository mysqlProjectRepository;

    @Test
    @Transactional
    void crud() {
        //save
        MemberJoinDto memberJoinDto = new MemberJoinDto("nameName", "test@dgist.ac.kr", "testPassword");
        Long saveMemberId = mysqlMemberRepository.save(memberJoinDto);
        ProjectSaveDto projectSaveDto = new ProjectSaveDto("testName", saveMemberId);
        Long saveProjectId = mysqlProjectRepository.save(projectSaveDto);

        //findById
        Optional<Project> optionalSaveProject = mysqlProjectRepository.findById(saveProjectId);
        Project saveProject = optionalSaveProject.get();
        assertThat(saveProject.getProjectName()).isEqualTo(projectSaveDto.getProjectName());
        assertThat(saveProject.getMemberId()).isEqualTo(saveMemberId);

        //update
        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto("updateTestName");
        mysqlProjectRepository.update(saveProjectId, projectUpdateDto);
        Optional<Project> optionalUpdateSaveProject = mysqlProjectRepository.findById(saveProjectId);
        Project updateSaveProject = optionalUpdateSaveProject.get();
        assertThat(updateSaveProject.getProjectName()).isEqualTo(projectUpdateDto.getProjectName());

        //delete
        mysqlProjectRepository.delete(saveProjectId);
        Optional<Project> optionalDeleteProject = mysqlProjectRepository.findById(saveProjectId);
        assertThat(optionalDeleteProject.isEmpty()).isTrue();
    }

    @Test
    @Transactional
    void findProjectsByMemberIdTest() {
        // save member
        MemberJoinDto memberJoinDto = new MemberJoinDto("nameName", "test@dgist.ac.kr", "testPassword");
        Long saveMemberId = mysqlMemberRepository.save(memberJoinDto);

        // save projects
        ProjectSaveDto projectSaveDto1 = new ProjectSaveDto("testName1", saveMemberId);
        ProjectSaveDto projectSaveDto2 = new ProjectSaveDto("testName2", saveMemberId);
        ProjectSaveDto projectSaveDto3 = new ProjectSaveDto("testName3", saveMemberId);

        Long saveProjectId1 = mysqlProjectRepository.save(projectSaveDto1);
        Long saveProjectId2 = mysqlProjectRepository.save(projectSaveDto2);
        Long saveProjectId3 = mysqlProjectRepository.save(projectSaveDto3);

        List<Project> projectsByMemberId = mysqlProjectRepository.findProjectsByMemberId(saveMemberId);

        assertEquals(3, projectsByMemberId.size());
        assertTrue(projectsByMemberId.stream().anyMatch(p -> p.getProjectId().equals(saveProjectId1)));
        assertTrue(projectsByMemberId.stream().anyMatch(p -> p.getProjectId().equals(saveProjectId2)));
        assertTrue(projectsByMemberId.stream().anyMatch(p -> p.getProjectId().equals(saveProjectId3)));
    }
}