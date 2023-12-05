package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.project.ProjectUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Long save(ProjectSaveDto projectSaveDto);
    void delete(Long projectId);
    void update(Long projectId, ProjectUpdateDto projectUpdateDto);
    Optional<Project> findById(Long projectId);
    // member의 project list 조회
    List<Project> findProjectsByMemberId(Long memberId);

}
