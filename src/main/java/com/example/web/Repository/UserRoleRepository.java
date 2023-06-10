package com.example.web.Repository;

import com.example.web.data.Entity.Role;
import com.example.web.data.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

}
