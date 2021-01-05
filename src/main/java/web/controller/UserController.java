package web.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/{id}")
    public ModelAndView viewUserFrom(Model model, @PathVariable("id") int id) {
        model.addAttribute("listUsers", userService.find(id));
        return new ModelAndView("user-find-by-id");
    }

    @PreAuthorize("hasAuthority(`ADMIN`)")
    @GetMapping("/admin/")
    public ModelAndView printAllUsers(Model model) {
        model.addAttribute("listUsers", userService.findAll());
        return new ModelAndView("all-users");
    }

    @GetMapping("/admin/addNewUser")
    public ModelAndView addNewUser(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("user-info");
    }

    @PostMapping(value = "/admin/saveUser")
    public ModelAndView saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return new ModelAndView("redirect:/admin/");
    }

    @GetMapping("/admin/updateUser/{id}")
    public ModelAndView editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.find(id));
        return new ModelAndView("update-user");
    }

    @PostMapping("/admin/edit/{id}")
    public ModelAndView viewEditUserForm(@ModelAttribute("user") User user) {
        userService.edit(user);
        return new ModelAndView("redirect:/admin/");
    }

    @GetMapping("/admin/delete/{id}")
    public ModelAndView deleteUser(@PathVariable(name = "id") int id) {
        userService.delete(id);
        return new ModelAndView("redirect:/admin/");
    }
}