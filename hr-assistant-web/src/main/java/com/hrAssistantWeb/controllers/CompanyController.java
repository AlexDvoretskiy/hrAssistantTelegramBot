package com.hrAssistantWeb.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import persistence.dto.CompanyInfoDto;
import persistence.services.CompanyInfoService;


@Controller
@RequestMapping("/company")
public class CompanyController {
    private final CompanyInfoService companyInfoService;

    private CompanyController() {
       companyInfoService = CompanyInfoService.getInstance();
    }

    @GetMapping("")
    public String showPage(Model model) {
        CompanyInfoDto companyInfo = companyInfoService.findOneByActiveTrue();
        model.addAttribute("companyInfo", companyInfo);
        return "company";
    }

    @PostMapping("/change/{id}")
    public String changeCompanyInfo(@ModelAttribute("companyInfo") CompanyInfoDto companyInfo, @PathVariable(name = "id") Long id) {
        companyInfo.setId(id);
        companyInfoService.update(companyInfo);
        return "redirect:/company";
    }
}
