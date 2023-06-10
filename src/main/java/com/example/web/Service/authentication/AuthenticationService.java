package com.example.web.Service.authentication;

import com.example.web.Repository.UserRepository;
import com.example.web.Repository.UserRoleRepository;
import com.example.web.Security.authentication.JwtService;
import com.example.web.data.Entity.Role;
import com.example.web.data.Entity.User;
import com.example.web.data.Entity.UserRole;
import com.example.web.data.pojo.ShortJwtPayload;
import com.example.web.data.request.UserLogin;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.web.data.constant.RoleConstant.ID_USER;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 UserRoleRepository userRoleRepository,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.jwtService = jwtService;
    }

    @Override
    public String login(UserLogin user) {
        User userDb = userRepository.findByEmail(user.getEmail());
        if(ObjectUtils.isEmpty(userDb)){
            throw  new IllegalArgumentException("tai khoan hoac mat khau khong dung");
        }
         checkPassword(userDb,user.getPassword());
        List<Role> roles = userRepository.getRoleById(userDb.getId());
        List<String> roleNames = Optional.ofNullable(roles)
                .orElse(new ArrayList<>())
                .stream().map(Role::getName)
                .collect(Collectors.toList());

        ShortJwtPayload shortJwtPayload = new ShortJwtPayload()
                .setUserId(userDb.getId())
                .setRoles(roleNames);
        String token = jwtService.gennerateJwt(shortJwtPayload);
        return token;
    }
    private Boolean checkPassword(User user,String password){
       final boolean isValidPass = BCrypt.checkpw(password,user.getPassword());
       if(!isValidPass){
            throw  new IllegalArgumentException("tai khoan hoac mat khau khong dung");
       }
       return true;
    }

    @Override
    public String register(User user) {
        User userDb = userRepository.findByEmail(user.getEmail());
        if(!ObjectUtils.isEmpty(userDb)){
            throw  new IllegalArgumentException("email da ton tai");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        User userRq = userRepository.save(user);

        UserRole userRole = new UserRole()
                .setUserId(userRq.getId())
                .setRoleId(ID_USER);
        userRoleRepository.save(userRole);
        return "success";
    }
}
