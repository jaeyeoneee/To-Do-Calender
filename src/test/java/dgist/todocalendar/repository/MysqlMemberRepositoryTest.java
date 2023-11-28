package dgist.todocalendar.repository;

import com.zaxxer.hikari.HikariDataSource;
import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MysqlMemberRepositoryTest {

    @Autowired
    private MysqlMemberRepository mysqlMemberRepository;

    @Test
    void crud(){
        //save
        MemberJoinDto joinMember = new MemberJoinDto("jane", "1234@naver", "1234");
        mysqlMemberRepository.save(joinMember);

        //findByEmail
        Member findEmailMember = mysqlMemberRepository.findByEmail("1234@naver");
        assertThat(joinMember.getEmail()).isEqualTo(findEmailMember.getEmail());
        assertThat(joinMember.getPassword()).isEqualTo(findEmailMember.getPassword());
        assertThat(joinMember.getName()).isEqualTo(findEmailMember.getMemberName());

        //update
        MemberUpdateDto updateMember = new MemberUpdateDto("jaeyeon");
        mysqlMemberRepository.update(findEmailMember.getMemberId(), updateMember);

        //findById
        Member findIdMember = mysqlMemberRepository.findById(findEmailMember.getMemberId());
        assertThat(updateMember.getName()).isEqualTo(findIdMember.getMemberName());

        //delete
        mysqlMemberRepository.delete(findEmailMember.getMemberId());
        assertThrows(EmptyResultDataAccessException.class ,()->mysqlMemberRepository.findById(findEmailMember.getMemberId()));
    }

    @Test
    void existsByEmail(){
        // email not exists
        boolean notExistsEmail = mysqlMemberRepository.existsByEmail("1234@dgist.ac.kr");
        Assertions.assertThat(notExistsEmail).isEqualTo(false);

        // email exists
        MemberJoinDto joinMember = new MemberJoinDto("jane", "1234@naver.com", "1234");
        mysqlMemberRepository.save(joinMember);

        boolean existsEmail = mysqlMemberRepository.existsByEmail("1234@naver.com");
        Assertions.assertThat(existsEmail).isEqualTo(true);

    }
}