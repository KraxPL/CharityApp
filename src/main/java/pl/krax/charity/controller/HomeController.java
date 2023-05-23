package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.krax.charity.service.DonationService;
import pl.krax.charity.service.InstitutionService;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final InstitutionService institutionService;
    private final DonationService donationService;

    @RequestMapping("/")
    public String homeAction(Model model) {
        model.addAttribute("institutions", institutionService.evenList());
        model.addAttribute("donatedBags", donationService.donatedBagsSoFar());
        model.addAttribute("donationsCount", donationService.donationsSoFar());
        return "/views/index";
    }
}
