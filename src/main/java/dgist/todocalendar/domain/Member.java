package dgist.todocalendar.domain;

import lombok.Data;

@Data
public class Member {

    private Long memberId;

    private String memberName;
    private String email;
    private String password;

    public Member() {
    }

    public Member(Long id, String name, String email, String password) {
        this.memberId = id;
        this.memberName = name;
        this.email = email;
        this.password = password;
    }
}