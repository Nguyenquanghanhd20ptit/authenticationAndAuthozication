package com.example.web.data.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ShortJwtPayload {
    private Long userId;
    private List<String> roles;
}
