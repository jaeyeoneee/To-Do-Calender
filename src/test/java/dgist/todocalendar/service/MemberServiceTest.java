package dgist.todocalendar.service;

import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberLoginDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import dgist.todocalendar.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void checkEmailDuplicateTest() {
        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberRepository.save(memberJoinDto);

        boolean checkTrue = memberService.checkEmailDuplicate(memberJoinDto.getEmail());
        assertThat(checkTrue).isTrue();

        boolean checkFalse = memberService.checkEmailDuplicate("123");
        assertThat(checkFalse).isFalse();
    }
    
    @Test
    void joinTest() {
        boolean checkFalse = memberService.checkEmailDuplicate("email");
        assertThat(checkFalse).isFalse();

        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberService.join(memberJoinDto);

        boolean checkTrue = memberService.checkEmailDuplicate("email");
        assertThat(checkTrue).isTrue();
    }

    @Test
    void loginTest(){
        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberService.join(memberJoinDto);

        MemberLoginDto falseEmailMemberDto = new MemberLoginDto("falseEmail", "password");
        boolean emptyEmail = memberService.login(falseEmailMemberDto).isEmpty();
        assertThat(emptyEmail).isTrue();

        MemberLoginDto falsePasswordMemberDto = new MemberLoginDto("email", "falsePassword");
        boolean emptyPassword = memberService.login(falseEmailMemberDto).isEmpty();
        assertThat(emptyPassword).isTrue();

        MemberLoginDto trueMemberDto = new MemberLoginDto("email", "password");
        Member trueMember = memberService.login(trueMemberDto).get();
        assertThat(trueMember).isNotNull();
    }

    @Test
    void findMemberByIdTest(){
        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberService.join(memberJoinDto);

        Member member = memberRepository.findByEmail("email").get();
        Long id = member.getMemberId();

        Member trueFindMember = memberService.findMemberById(id).get();
        assertThat(memberJoinDto.getEmail()).isEqualTo(trueFindMember.getEmail());

        boolean empty = memberService.findMemberById(id + 1L).isEmpty();
        assertThat(empty).isTrue();
    }

    @Test
    void updateTest(){
        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberService.join(memberJoinDto);

        Member member = memberRepository.findByEmail("email").get();
        Long id = member.getMemberId();

        MemberUpdateDto memberUpdateDto = new MemberUpdateDto("updateName");
        memberService.update(id, memberUpdateDto);

        Member updateMember = memberRepository.findByEmail("email").get();
        assertThat(updateMember.getMemberName()).isEqualTo("updateName");
    }

    @Test
    void deleteTest(){
        MemberJoinDto memberJoinDto = new MemberJoinDto("name", "email", "password");
        memberService.join(memberJoinDto);

        Member member = memberRepository.findByEmail("email").get();
        Long id = member.getMemberId();

        memberService.delete(id);

        boolean empty = memberRepository.findByEmail("email").isEmpty();
        assertThat(empty).isTrue();
    }
}