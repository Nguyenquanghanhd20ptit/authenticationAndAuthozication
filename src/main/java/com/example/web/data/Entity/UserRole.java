package com.example.web.data.Entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_role")
@Data
@Accessors(chain = true)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;
    private Integer roleId;
    private LocalDateTime createdAt;
}
