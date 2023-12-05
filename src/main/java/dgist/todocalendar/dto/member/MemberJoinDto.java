package dgist.todocalendar.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberJoinDto {

    @NotBlank
    @Size(max=10)
    private String name;

    @Email
    @NotBlank
    @Size(max=20)
    private String email;

    @NotBlank
    @Size(max=20)
    private String password;

    public MemberJoinDto() {
    }

    public MemberJoinDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
