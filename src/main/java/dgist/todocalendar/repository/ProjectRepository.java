package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.project.ProjectDto;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    Project save(ProjectDto projectDto);
    void delete(Long projectId);
    void update(Long projectId, ProjectDto projectDto);
    Optional<Project> findById(Long projectId);
    // member의 project list 조회
    List<Project> findProjectsByMemberId(Long memberId);

}
