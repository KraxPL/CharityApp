package pl.krax.charity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.service.UserService;
import pl.krax.charity.validator.UserRegisterDtoValidator;

@Controller
@RequiredArgsConstructor
public class SecurityController {
    private final UserRegisterDtoValidator userRegisterDtoValidator;
    private final UserService userService;
    @InitBinder("userRegisterDto")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userRegisterDtoValidator);
    }
    @GetMapping("/login")
    public String loginForm(){
        return "/views/login";
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("userRegisterDto", new UserRegisterDto());
        return "/views/register";
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @ModelAttribute UserRegisterDto userRegisterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }
        userService.save(userRegisterDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
