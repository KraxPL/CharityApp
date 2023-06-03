package pl.krax.charity.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Incorrect name or email!");
        }

        userService.update(updatedUser);

        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

    @PostMapping("/passwordChange")
    public String changePassword(@RequestParam Long userId, @RequestParam String currentPassword,
                                 @RequestParam String newPassword, @RequestParam String repeatPassword) {
        if (userService.checkPasswordAndCurrentPassword(newPassword, repeatPassword, currentPassword, userId)) {
            userService.changePassword(newPassword, userId);
        }
        return "redirect:/profile";
    }

    @GetMapping("/adminProfile")
    public String adminPage() {
        return "/views/adminPanel";
    }

    @GetMapping("/activateUser")
    @ResponseBody
    public String activateUserFromToken(@RequestParam String token, @RequestParam String email) {
        userService.activateUserAccount(email, token);
        return "Account has been activated! You can now log in";
    }
    @GetMapping("/password")
    public String changePasswordForm(@RequestParam String token, @RequestParam String email, Model model){
        if (userService.checkTokenAndEmailMatch(token, email)) {
            model.addAttribute("email", email);
            model.addAttribute("token", token);
            return "views/newPassword";
        }
        return "redirect:/?error=tokenAndEmailDontMatch";
    }
    @PostMapping("/forgetPassword")
    @ResponseBody
    public String forgetPassword(@RequestParam String email) {
        return userService.sendMailWithPasswordChange(email)
                ? "Mail with a link to password changing has been sent!"
                : "There is no such email in our database!";
    }
    @GetMapping("/forgetPassword")
    public String forgetPasswordForm(){
        return "views/password";
    }
    @PostMapping("/password")
    public String changePassword(@RequestParam String newPassword, @RequestParam String repeatedPassword,
                                 @RequestParam String email, @RequestParam String token) {
        if (userService.changeForgottenPassword(email, token, newPassword, repeatedPassword)){
            return "redirect:/?message=YouCanNowLogin";
        }
    return "redirect:/password?error=PasswordsDontMatch&email=" + email + "&token=" + token;
    }
}
