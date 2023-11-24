package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.MemberJoinDto;
import dgist.todocalendar.dto.MemberUpdateDto;

import java.util.Optional;

public interface MemberRepository {

    Member save(MemberJoinDto memberJoinDto);
    void delete(Long id);
    void update(Long id, MemberUpdateDto memberUpdateDto);
    //회원 정보 조회
    Optional<Member> findById(Long id);
    //로그인
    Optional<Member> findByEmail(String email);
    //회원가입 중복 확인
    boolean existsByEmail(String email);
}
