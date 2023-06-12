package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.krax.charity.dto.DonationDto;
import pl.krax.charity.entities.Category;
import pl.krax.charity.entities.Donation;
import pl.krax.charity.entities.Institution;
import pl.krax.charity.service.CategoryService;
import pl.krax.charity.service.DonationService;
import pl.krax.charity.service.InstitutionService;
import pl.krax.charity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DonationController {
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UserService userService;

    @GetMapping("/donation")
    public String donationCreateForm(Model model) {
        model.addAttribute("donationDto", new DonationDto());
        return "views/form";
    }

    @PostMapping("/donation")
    public String donationCreate(@ModelAttribute("donationDto") DonationDto donationDto) {
        donationService.create(donationDto);
        return "redirect:/userDonations";
    }

    @GetMapping("/userDonations")
    public String displayUsersDonations(Principal principal, Model model) {
        Long userId = getUserId(principal);
        model.addAttribute("donations", donationService.donationsByUserId(userId));
        return "views/userInfo/donationsList";
    }

    @PostMapping("/userDonations/{id}/status")
    public String changeDonationStatus(@PathVariable Long id) {
        donationService.itemsCollected(id);
        return "redirect:/userDonations";
    }

    @GetMapping("/userDonations/{id}")
    public String showDonationDetails(@PathVariable Long id, Principal principal, Model model) {
        Long userId = getUserId(principal);
        if (donationService.checkDonationIdAndUserIdMatch(id, userId)) {
            Donation donation = donationService.findById(id).orElse(null);
            model.addAttribute("donation", donation);
            return "views/userInfo/donationDetails";
        }
        return "redirect:/userDonations?error=NotYourDonation";
    }

    private Long getUserId(Principal principal) {
        String userEmail = principal.getName();
        return userService.findUserDtoByUserEmail(userEmail)
                .getId();
    }

    @ModelAttribute(name = "categoriesList")
    public List<Category> categoriesList() {
        return categoryService.findAll();
    }

    @ModelAttribute(name = "institutionsList")
    public List<Institution> institutionsList() {
        return institutionService.findAll();
    }
}
