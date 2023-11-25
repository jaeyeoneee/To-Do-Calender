package dgist.todocalendar.domain;

import lombok.Data;

@Data
public class Project {

    private Long projectId;

    private String projectName;
    //foreign key
    private Long memberId;

    public Project() {
    }

    public Project(Long projectId, String projectName, Long memberId) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.memberId = memberId;
    }
}
