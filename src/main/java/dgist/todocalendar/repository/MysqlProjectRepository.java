package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.project.ProjectUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MysqlProjectRepository implements ProjectRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(ProjectSaveDto projectSaveDto) {
        String sql = "insert into project(name, member_id) values (?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((PreparedStatementCreator) con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, projectSaveDto.getProjectName());
            ps.setLong(2, projectSaveDto.getMemberId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long projectId) {
        String sql = "delete from project where id = ?";
        jdbcTemplate.update(sql, projectId);
    }

    @Override
    public void update(Long projectId, ProjectUpdateDto projectUpdateDto) {
        String sql = "update project set name = ? where id = ?";
        jdbcTemplate.update(sql, projectUpdateDto.getProjectName(), projectId);
    }

    @Override
    public Optional<Project> findById(Long projectId) {
        String sql = "select * from project where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, projectMapper(), projectId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Project> findProjectsByMemberId(Long memberId) {
        String sql = "select * from project where member_id = ?";
        return jdbcTemplate.query(sql, projectMapper(), memberId);
    }

    private RowMapper<Project> projectMapper() {
        return (rs, rowNum) -> {
            Project project = new Project();
            project.setProjectId(rs.getLong("id"));
            project.setProjectName(rs.getString("name"));
            project.setMemberId(rs.getLong("member_id"));
            return project;
        };
    }
}
