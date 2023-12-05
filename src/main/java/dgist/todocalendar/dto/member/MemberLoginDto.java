package dgist.todocalendar.dto.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MemberLoginDto {

    @Email
    @NotBlank
    @Size(max=20)
    private String email;

    @NotBlank
    @Size(max=20)
    private String password;

    public MemberLoginDto() {
    }

    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
