package dgist.todocalendar.dto.project;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectUpdateDto {

    @NotBlank
    @Size(max=20)
    private String projectName;

    public ProjectUpdateDto() {
    }

    public ProjectUpdateDto(String projectName) {
        this.projectName = projectName;
    }
}
