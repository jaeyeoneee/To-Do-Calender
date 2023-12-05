package dgist.todocalendar.dto.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberUpdateDto {

    @NotBlank
    @Size(max=10)
    private String name;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String name) {
        this.name = name;
    }
}
