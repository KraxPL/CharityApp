package pl.krax.charity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.service.UserService;

@Controller
@RequestMapping("/manage/admins")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @GetMapping
    public String listAdmins(Model model){
        model.addAttribute("admins", userService.findAllAdmins());
        return "views/admins/list";
    }
    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        boolean isDeleted = userService.delete(id);
        if (!isDeleted) {
            return "redirect:/manage/admins?error=lastAdmin";
        }
        return "redirect:/manage/admins";
    }
    @PostMapping
    public String addAdmin(@ModelAttribute UserDto userDto) {
        userService.createAdmin(userDto);
        return "redirect:/manage/admins";
    }
    @GetMapping("/{id}")
    public String editAdminPage(@PathVariable("id") Long adminId, Model model) {
        UserDto adminDto = userService.findUserDtoById(adminId);
        model.addAttribute("admin", adminDto);
        return "views/admins/edit";
    }

    @PostMapping("/{id}")
    public String editAdmin(@PathVariable Long id, @ModelAttribute UserDto updatedUserDto,
                                  BindingResult bindingResult) {
        if (id.equals(updatedUserDto.getId()) && !bindingResult.hasErrors()) {
            userService.update(updatedUserDto);
        } else {
            return "views/admins/edit";
        }
        return "redirect:/manage/admins";
    }
}
