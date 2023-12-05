package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Project;
import dgist.todocalendar.domain.Task;
import dgist.todocalendar.dto.task.TaskSaveDto;
import dgist.todocalendar.dto.task.TaskUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MysqlTaskRepository implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(TaskSaveDto taskSaveDto) {
        String sql = "insert into task(name, date, project_id) values (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((PreparedStatementCreator) con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, taskSaveDto.getTaskName());

            Date taskDate = Date.valueOf(taskSaveDto.getDate());
            ps.setDate(2, taskDate);

            ps.setLong(3, taskSaveDto.getProjectId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long taskId) {
        String sql = "delete from task where id = ?";
        jdbcTemplate.update(sql, taskId);
    }

    @Override
    public void update(Long taskId, TaskUpdateDto taskUpdateDto) {
        String sql = "update task set name = ?, date = ? where id = ?";

        Date taskDate = Date.valueOf(taskUpdateDto.getDate());

        jdbcTemplate.update(sql, taskUpdateDto.getTaskName(), taskDate, taskId);
    }

    @Override
    public Optional<Task> findById(Long taskId) {
        String sql = "select * from task where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, taskMapper(), taskId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Task> findTasksByProjectId(Long projectId) {
        String sql = "select * from task where project_id = ?";
        return jdbcTemplate.query(sql, taskMapper(), projectId);
    }

    private RowMapper<Task> taskMapper() {
        return (rs, rowNum) -> {
            Task task = new Task();
            task.setTaskId(rs.getLong("id"));
            task.setTaskName(rs.getString("name"));
            task.setProjectId(rs.getLong("project_id"));

            Date date = rs.getDate("date");
            if (date != null) {
                task.setDate(date.toLocalDate());
            }

            return task;
        };
    }
}
