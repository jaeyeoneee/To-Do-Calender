package dgist.todocalendar.service;

import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.project.ProjectUpdateDto;
import dgist.todocalendar.repository.MysqlProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final MysqlProjectRepository projectRepository;

    public List<Project> findProjectsByMemberId(Long memberId) {

        return projectRepository.findProjectsByMemberId(memberId);
    }

    public void save(ProjectSaveDto projectSaveDto) {

        projectRepository.save(projectSaveDto);
    }

    public void update(Long projectId ,ProjectUpdateDto projectUpdateDto) {

        projectRepository.update(projectId, projectUpdateDto);
    }

    public void delete(Long projectId) {

        projectRepository.delete(projectId);
    }

    public Optional<Project> findProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }
}
