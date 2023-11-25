package dgist.todocalendar.dto.member;

import lombok.Data;

@Data
public class MemberUpdateDto {

    private String name;

    public MemberUpdateDto() {
    }

    public MemberUpdateDto(String name) {
        this.name = name;
    }
}
