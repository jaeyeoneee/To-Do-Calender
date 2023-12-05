package dgist.todocalendar.dto.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectSaveDto {

    @NotBlank
    @Size(max=20)
    private String projectName;
    //foreign key
    private Long memberId;

    public ProjectSaveDto() {
    }

    public ProjectSaveDto(String projectName, Long memberId) {
        this.projectName = projectName;
        this.memberId = memberId;
    }
}
