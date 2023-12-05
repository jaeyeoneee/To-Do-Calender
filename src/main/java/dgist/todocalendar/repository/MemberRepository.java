package dgist.todocalendar.repository;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository {

    Long save(MemberJoinDto memberJoinDto);
    void delete(Long memberId);
    void update(Long memberId, MemberUpdateDto memberUpdateDto);
    //회원 정보 조회
    Optional<Member> findById(Long memberId);
    //로그인
    Optional<Member> findByEmail(String email);
    //회원가입 중복 확인
    boolean existsByEmail(String email);
}
