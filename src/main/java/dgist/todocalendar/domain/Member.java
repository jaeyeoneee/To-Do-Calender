package dgist.todocalendar.domain;

import lombok.Data;

@Data
public class Member {

    private String name;
    private String email;
    private String password;

    public Member() {
    }

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
