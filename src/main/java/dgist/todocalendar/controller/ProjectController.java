package dgist.todocalendar.controller;

import dgist.todocalendar.argumentresolver.LoginMemberId;
import dgist.todocalendar.domain.Project;
import dgist.todocalendar.dto.project.ProjectSaveDto;
import dgist.todocalendar.dto.project.ProjectUpdateDto;
import dgist.todocalendar.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/calendar/projects")
    public String projects(@LoginMemberId Long memberId, Model model) {

        List<Project> projectsByMemberId = projectService.findProjectsByMemberId(memberId);

        model.addAttribute("projects", projectsByMemberId);

        return "projects";
    }

    @GetMapping("/calendar/projects/add")
    public String projectAddGet(@ModelAttribute ProjectSaveDto projectSaveDto) {
        return "projectAdd";
    }

    @PostMapping("/calendar/projects/add")
    public String projectAddPost(@LoginMemberId Long memberId, @Validated @ModelAttribute ProjectSaveDto projectSaveDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "projectAdd";
        }

        projectSaveDto.setMemberId(memberId);
        log.info("ProjectUpdateDto={}", projectSaveDto);

        //projectUpdateDto 저장
        projectService.save(projectSaveDto);

        return "redirect:/calendar/projects";
    }

    @GetMapping("/calendar/projects/update/{projectId}")
    public String projectUpdateGet(@ModelAttribute ProjectUpdateDto projectUpdateDto) {
        return "projectUpdate";
    }

    @PostMapping("/calendar/projects/update/{projectId}")
    public String projectUpdatePost(@PathVariable Long projectId, @Validated @ModelAttribute ProjectUpdateDto projectUpdateDto,
                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "projectUpdate";
        }

        // update
        projectService.update(projectId, projectUpdateDto);

        return "redirect:/calendar/projects";
    }

    @GetMapping("/calendar/projects/deleteCheck/{projectId}")
    public String projectDeleteCheck(@PathVariable Long projectId, Model model) {

        model.addAttribute("projectId", projectId);

        return "projectDeleteCheck";
    }

    @GetMapping("/calendar/projects/delete/{projectId}")
    public String projectDelete(@PathVariable Long projectId) {

        projectService.delete(projectId);

        return "redirect:/calendar/projects";
    }
}
