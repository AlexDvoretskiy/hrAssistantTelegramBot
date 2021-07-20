package com.hrAssistantWeb.controllers;


import com.hrAssistantWeb.model.VacancyModel;
import com.hrAssistantWeb.services.FileService;
import com.hrAssistantWeb.services.VacancyModelService;
import com.hrAssistantWeb.utils.MediaTypeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import persistence.dto.AssignmentDto;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;


@Controller
@RequestMapping("/vacancies")
public class VacancyController {

    private FileService fileService;
    private ServletContext servletContext;
    private VacancyModelService vacancyModelService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("")
    public String showPage(Model model) {
        List<VacancyModel> vacancyList = vacancyModelService.findAllByActiveTrue();
        model.addAttribute("vacancies", vacancyList);
        return "vacancies";
    }

    @GetMapping("assignment/{assignmentFileName}")
    public ResponseEntity<InputStreamResource> downloadAssignmentFile(@PathVariable(name = "assignmentFileName") String assignmentFileName) throws FileNotFoundException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, assignmentFileName);

        File file = fileService.getAssignmentFile(assignmentFileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("/add")
    public String addVacancy(Model model) {
        VacancyModel vacancyModel = new VacancyModel();
        List<AssignmentDto> assignmentDtoList = vacancyModelService.findAllAssignments();
        model.addAttribute("vacancy", vacancyModel);
        model.addAttribute("assignments", assignmentDtoList);
        return "add-vacancy";
    }

    @PostMapping("/add")
    public String addVacancy(@Valid @ModelAttribute("vacancy") VacancyModel vacancyModel, BindingResult bindingResult,
                             @RequestParam("assignmentFile") MultipartFile assignmentFile, Model model) {
        if (bindingResult.hasErrors()) {
            List<AssignmentDto> assignmentDtoList = vacancyModelService.findAllAssignments();
            model.addAttribute("assignments", assignmentDtoList);
            return "add-vacancy";
        }
        vacancyModelService.save(vacancyModel, assignmentFile);
        return "redirect:/vacancies";
    }

    @GetMapping("/change/{id}")
    public String changeVacancy(@PathVariable(name = "id") Long id, Model model) {
        VacancyModel vacancyModel = vacancyModelService.findById(id);
        List<AssignmentDto> assignmentDtoList = vacancyModelService.findAllAssignments();
        model.addAttribute("vacancy", vacancyModel);
        model.addAttribute("assignments", assignmentDtoList);
        return "edit-vacancy";
    }

    @PostMapping("/change/{id}")
    public String changeVacancy(@ModelAttribute("vacancy") VacancyModel vacancyModel, @PathVariable(name = "id") Long id) {
        vacancyModel.setId(id);
        vacancyModelService.update(vacancyModel);
        return "redirect:/vacancies";
    }

    @GetMapping("/remove/{id}")
    public String removeVacancy(@PathVariable(name = "id") Long id) {
        vacancyModelService.remove(id);
        return "redirect:/vacancies";
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Autowired
    public void setVacancyModelService(VacancyModelService vacancyModelService) {
        this.vacancyModelService = vacancyModelService;
    }
}
