package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Category;
import pl.krax.charity.entities.Institution;
import pl.krax.charity.service.CategoryService;
import pl.krax.charity.service.DonationService;
import pl.krax.charity.service.InstitutionService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DonationController {
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final DonationService donationService;
    @GetMapping("/donation")
    public String donationCreateForm(Model model){
        model.addAttribute("donationDto", new DonationDto());
        return "/views/form";
    }
    @PostMapping("/donation")
    public String donationCreate(@ModelAttribute("donationDto") DonationDto donationDto){
        donationService.create(donationDto);
        return "redirect:/";
    }
    @ModelAttribute(name = "categoriesList")
    public List<Category> categoriesList(){
        return categoryService.findAll();
    }
    @ModelAttribute(name = "institutionsList")
    public List<Institution> institutionsList(){
        return institutionService.findAll();
    }
}
