package dgist.todocalendar.dto;

import lombok.Data;

@Data
public class MemberJoinDto {

    private String name;
    private String email;
    private String password;

    public MemberJoinDto() {
    }

    public MemberJoinDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
