package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.service.UserService;

@Controller
@RequestMapping("/manage/users")
@RequiredArgsConstructor
public class UserManagementController {
    private final UserService userService;
    @GetMapping
    public String listUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "views/users/list";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.delete(id);
        if (!isDeleted) {
            return "redirect:/manage/users?error=Error";
        }
        return "redirect:/manage/users";
    }
    @PostMapping("")
    public String addUser(@ModelAttribute UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/manage/users";
    }
    @GetMapping("/{id}")
    public String editUserPage(@PathVariable("id") Long userId, Model model) {
        UserDto userDto = userService.findUserDtoById(userId);
        model.addAttribute("user", userDto);
        return "views/users/edit";
    }

    @PostMapping("/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute UserDto updatedUserDto,
                            BindingResult bindingResult) {
        if (id.equals(updatedUserDto.getId()) && !bindingResult.hasErrors()) {
            userService.update(updatedUserDto);
        } else {
            return "views/users/edit";
        }
        return "redirect:/manage/users";
    }
    @GetMapping("/lockOrUnlock/{id}")
    public String lockOrUnlockUser(@PathVariable("id") Long userId){
        userService.lockOrUnlockUser(userId);
        return "redirect:/manage/users";
    }
}
