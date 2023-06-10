package com.example.web.data.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserLogin {
    private String email;
    private String password;
}
