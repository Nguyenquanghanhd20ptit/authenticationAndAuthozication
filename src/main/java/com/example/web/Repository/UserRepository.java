package com.example.web.Repository;

import com.example.web.data.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.web.data.Entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	@Query("select r from User as u " +
			"inner join UserRole  as ur on u.id = ur.userId " +
			"inner  join  Role as r on r.id = ur.roleId" +
			" where u.id = ?1")
	public List<Role> getRoleById(Long userId);
}