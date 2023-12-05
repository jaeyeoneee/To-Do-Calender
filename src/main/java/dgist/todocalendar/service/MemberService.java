package dgist.todocalendar.service;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberLoginDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import dgist.todocalendar.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입 시 email 중복 체크
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public void join(MemberJoinDto memberJoinDto) {
        memberRepository.save(memberJoinDto);
    }

    // email이 존재하고, password가 동일하면 member 반환, 이외는 null
    public Optional<Member> login(MemberLoginDto memberLoginDto) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberLoginDto.getEmail());

        if (optionalMember.isEmpty()) {
            return Optional.empty();
        }

        Member member = optionalMember.get();

        if (!member.getPassword().equals(memberLoginDto.getPassword())) {
            return Optional.empty();
        }

        return Optional.ofNullable(member);
    }

    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public void update(Long memberId, MemberUpdateDto memberUpdateDto) {
        memberRepository.update(memberId, memberUpdateDto);
    }

    public void delete(Long memberId) {
        memberRepository.delete(memberId);
    }


}
