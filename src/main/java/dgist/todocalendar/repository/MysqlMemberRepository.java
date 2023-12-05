 package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MysqlMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(MemberJoinDto memberJoinDto) {
        String sql = "insert into member(name, email, password) values (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((PreparedStatementCreator) con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, memberJoinDto.getName());
            ps.setString(2, memberJoinDto.getEmail());
            ps.setString(3, memberJoinDto.getPassword());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void delete(Long memberId) {
        String sql = "delete from member where id = ?";
        jdbcTemplate.update(sql, memberId);
    }

    @Override
    public void update(Long memberId, MemberUpdateDto memberUpdateDto) {
        String sql = "update member set name = ? where id = ?";
        jdbcTemplate.update(sql, memberUpdateDto.getName(), memberId);
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        String sql = "select * from member where id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberMapper(), memberId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        String sql = "select * from member where email = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberMapper(), email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "select exists (select 1 from member where email = ?)";
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    private RowMapper<Member> memberMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setMemberId(rs.getLong("id"));
            member.setMemberName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        };
    }
}
