 package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MysqlMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(MemberJoinDto memberJoinDto) {
        String sql = "insert into member(name, email, password) values (?, ?, ?)";
        jdbcTemplate.update(sql, memberJoinDto.getName(), memberJoinDto.getEmail(), memberJoinDto.getPassword());
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
    public Member findById(Long memberId) {
        String sql = "select * from member where id = ?";
        return jdbcTemplate.queryForObject(sql, memberMapper(), memberId);
    }

    @Override
    public Member findByEmail(String email) {
        String sql = "select * from member where email = ?";
        return jdbcTemplate.queryForObject(sql, memberMapper(), email);
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
