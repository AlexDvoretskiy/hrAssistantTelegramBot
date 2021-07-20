package com.hrAssistantWeb.controllers;


import com.hrAssistantWeb.model.CandidateModel;
import com.hrAssistantWeb.services.CandidateModelService;
import com.hrAssistantWeb.services.FileService;
import com.hrAssistantWeb.utils.MediaTypeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;


@Controller
@RequestMapping("/candidates")
public class CandidateController {

    private FileService fileService;
    private ServletContext servletContext;
    private CandidateModelService candidateModelService;

    
    @GetMapping("")
    public String showPage(Model model) {
        List<CandidateModel> candidateModels = candidateModelService.getCandidateModelList();
        model.addAttribute("candidateModels", candidateModels);
        return "candidates";
    }

    @GetMapping("cv/{cvFileName}")
    public ResponseEntity<InputStreamResource> downloadCvFile(@PathVariable(name = "cvFileName") String cvFileName) throws FileNotFoundException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, cvFileName);

        File file = fileService.getCvFile(cvFileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @GetMapping("assignment/{assignmentFileName}")
    public ResponseEntity<InputStreamResource> downloadAssignmentResultFile(@PathVariable(name = "assignmentFileName") String assignmentFileName) throws FileNotFoundException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, assignmentFileName);

        File file = fileService.getAssignmentResultFile(assignmentFileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    @Autowired
    public void setCandidateModelService(CandidateModelService candidateModelService) {
        this.candidateModelService = candidateModelService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
