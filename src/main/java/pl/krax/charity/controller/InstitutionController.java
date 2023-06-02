package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.charity.entities.Institution;
import pl.krax.charity.service.InstitutionService;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RequestMapping("/manage")
public class InstitutionController {
    private final InstitutionService institutionService;
    @GetMapping("/institutions")
    public String institutionsPage(Model model) {
        List<Institution> institutions = institutionService.findAll();
        model.addAttribute("institutions", institutions);
        return "views/institutions/list";
    }

    @PostMapping("/institutions")
    public String addInstitution(@ModelAttribute Institution institution) {
        institutionService.create(institution);
        return "redirect:/manage/institutions";
    }

    @GetMapping("/institutions/{id}")
    public String editInstitutionPage(@PathVariable("id") Long institutionId, Model model) {
        Institution institution = institutionService.findById(institutionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid institution ID: " + institutionId));
        model.addAttribute("institution", institution);
        return "views/institutions/edit";
    }

    @PostMapping("/institutions/{id}")
    public String editInstitution(@PathVariable Long id, @ModelAttribute Institution updatedInstitution,
                                  BindingResult bindingResult) {
        if (id.equals(updatedInstitution.getId()) && !bindingResult.hasErrors()) {
            institutionService.update(updatedInstitution);
        } else {
            return "views/institutions/edit";
        }
        return "redirect:/manage/institutions";
    }

    @DeleteMapping("/institutions/{id}")
    public String deleteInstitution(@PathVariable Long id) {
        institutionService.delete(id);
        return "redirect:/manage/institutions";
    }

}
