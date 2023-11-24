package dgist.todocalendar.dto;

import lombok.Data;

@Data
public class MemberLoginDto {

    private String email;
    private String password;

    public MemberLoginDto() {
    }

    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
