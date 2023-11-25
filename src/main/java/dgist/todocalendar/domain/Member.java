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
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}