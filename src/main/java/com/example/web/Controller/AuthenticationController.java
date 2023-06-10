package com.example.web.Controller;

import com.example.web.Service.authentication.IAuthenticationService;
import com.example.web.data.Entity.User;
import com.example.web.data.request.UserLogin;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.example.web.Security.utils.SecurityUtils.getRoles;

@Controller
public class AuthenticationController {
    private final IAuthenticationService authService;

    public AuthenticationController(IAuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/public/home")
    public String viewHomePage() {

        return "LoginView/home";
    }

    @GetMapping("/api/auth/login")
    public String login(Model model) {
        model.addAttribute("user", new UserLogin());
        return "LoginView/login_form";
    }

    @PostMapping("/api/auth/process_login")
    public String processLogin(UserLogin user, Model model, HttpServletResponse response, HttpSession session) {
        try {
            String token = authService.login(user);
            session.setAttribute("Authorization", "Bearer " + token);
            return "redirect:/api/auth/redirect";

        } catch (IllegalArgumentException e) {
            String error = e.getMessage();
            model.addAttribute("error", error);
            model.addAttribute("user",user);
            return "LoginView/login_form";
        }
    }

    @GetMapping("/api/auth/redirect")
    public String redirect(Model model, Authentication authentication) {
        List<String> roles = getRoles(authentication);
        String role = roles != null ? roles.get(0) : null;
        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/api/admin/books";
        }
        return "formUser";
    }


    @GetMapping("/api/auth/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "LoginView/signup_form";
    }

    @PostMapping("/api/auth/process_register")
    public String processRegister(User user, Model model) {
        try {
            String status = authService.register(user);
            return "LoginView/register_success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user",user);
            return "LoginView/signup_form";
        }
    }
}
