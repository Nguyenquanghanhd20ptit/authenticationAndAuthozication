package com.example.web.Service.authentication;

import com.example.web.data.Entity.User;
import com.example.web.data.request.UserLogin;
import org.springframework.validation.BindingResult;

public interface IAuthenticationService {
    public String login(UserLogin user);
    public String register(User user);
}
