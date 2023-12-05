package dgist.todocalendar.controller;

import dgist.todocalendar.argumentresolver.LoginMemberId;
import dgist.todocalendar.domain.Member;
import dgist.todocalendar.dto.member.MemberJoinDto;
import dgist.todocalendar.dto.member.MemberLoginDto;
import dgist.todocalendar.dto.member.MemberUpdateDto;
import dgist.todocalendar.service.MemberService;
import dgist.todocalendar.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String memberJoinGet(Model model) {

        MemberJoinDto memberJoinDto = new MemberJoinDto();

        model.addAttribute("memberJoinDto", memberJoinDto);

        return "join";
    }

    @PostMapping("/join")
    public String memberJoinPost(@Validated @ModelAttribute MemberJoinDto memberJoinDto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "join";
        }

        if (memberService.checkEmailDuplicate(memberJoinDto.getEmail())) {
            bindingResult.reject("duplicate", new Object[]{"email"}, null);
            return "join";
        }

        log.info("memberJoinDto={}", memberJoinDto);

        memberService.join(memberJoinDto);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String memberLoginGet(Model model) {

        MemberLoginDto memberLoginDto = new MemberLoginDto();
        model.addAttribute("memberLoginDto", memberLoginDto);

        return "login";
    }

    @PostMapping("/login")
    public String memberLoginPost(@Validated @ModelAttribute MemberLoginDto memberLoginDto, BindingResult bindingResult,
                                  @RequestParam(defaultValue = "/calendar") String redirectURL,
                                  HttpServletRequest request) {

        // binding error
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login";
        }

        Optional<Member> optionalLoginMember = memberService.login(memberLoginDto);

        // email 또는 password가 없는 경우
        if (optionalLoginMember.isEmpty()) {
            bindingResult.reject("noSuchMember");
            return "login";
        }

        //세션: 쿠키 전송 및 id 저장

        Member loginMember = optionalLoginMember.get();

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getMemberId());

        log.info("redirctURL={}", redirectURL);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

    @GetMapping("/mypage")
    public String mypage(@LoginMemberId Long memberId, Model model) {

        Optional<Member> optionalMember = memberService.findMemberById(memberId);

        //TODO: optionalMember가 없는 상황

        Member member = optionalMember.get();

        model.addAttribute("memberName", member.getMemberName());

        return "mypage";
    }

    @GetMapping("/memberUpdate")
    public String memberUpdateGet(@ModelAttribute MemberUpdateDto memberUpdateDto) {
        return "memberUpdate";
    }

    @PostMapping("/memberUpdate")
    public String memberUpdatePost(@LoginMemberId Long memberId, @Validated @ModelAttribute MemberUpdateDto memberUpdateDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "memberUpdate";
        }

        // 회원 이름 수정
        memberService.update(memberId, memberUpdateDto);

        return "redirect:/mypage";
    }

    @GetMapping("/memberDeleteCheck")
    public String memberDeleteCheck() {
        return "memberDeleteCheck";
    }

    @GetMapping("/memberDelete")
    public String memberDelete(@LoginMemberId Long memberId, HttpServletRequest request) {

        // TODO: session != null 이 꼭 필요한지
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }

        // TODO: 저장소 delete error를 어떻게 다뤄야 하는지
        memberService.delete(memberId);

        return "redirect:/login";
    }
}
