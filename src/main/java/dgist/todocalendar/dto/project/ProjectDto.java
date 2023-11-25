package dgist.todocalendar.dto.project;

import lombok.Data;

@Data
public class ProjectDto {

    private String projectName;

    public ProjectDto() {
    }

    public ProjectDto(String projectName) {
        this.projectName = projectName;
    }
}
