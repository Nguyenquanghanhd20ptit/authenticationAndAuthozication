package com.example.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/api/public/error/autho")
    public String handleErrorAutho(Model model) {
        model.addAttribute("error","không có quyền truy cập");
        return "invalidAutho";
    }
    @GetMapping("/api/public/error/authen")
    public String handleErrorAuthen(Model model) {
        model.addAttribute("error","lỗi xác thực. hãy thử đăng nhập lại");
        return "invalidAutho";
    }
}
