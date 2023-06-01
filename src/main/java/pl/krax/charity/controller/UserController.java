package pl.krax.charity.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.service.UserService;


@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminProfile";
        }

        UserDto userDto = userService.findUserDtoByUserEmail(userEmail);

        model.addAttribute("user", userDto);
        return "views/profile";
    }
    @PostMapping("/profileUpdate")
    public String updateUserProfile(@ModelAttribute("user") UserDto updatedUser, BindingResult bindingResult,
                                    Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            model.addAttribute("error", "Incorrect name or email!");
        }

        userService.update(updatedUser);

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
    @PostMapping("/passwordChange")
    public String changePassword(@RequestParam Long userId, @RequestParam String currentPassword,
                                 @RequestParam String newPassword, @RequestParam String repeatPassword){
        if (userService.checkPasswordAndCurrentPassword(newPassword, repeatPassword, currentPassword, userId)){
            userService.changePassword(newPassword, userId);
        }
        return "redirect:/profile";
    }
}
