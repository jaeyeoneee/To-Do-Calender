package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;

import java.util.Optional;

public interface MemberRepository {

    void save(MemberJoinDto memberJoinDto);
    void delete(Long memberId);
    void update(Long memberId, MemberUpdateDto memberUpdateDto);
    //회원 정보 조회
    Member findById(Long memberId);
    //로그인
    Member findByEmail(String email);
    //회원가입 중복 확인
    boolean existsByEmail(String email);
}
