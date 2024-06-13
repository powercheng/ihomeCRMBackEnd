package com.ihomeCabinet.crm.controller;

import com.ihomeCabinet.crm.MsgVO;
import com.ihomeCabinet.crm.model.Customer;
import com.ihomeCabinet.crm.service.UserService;
import com.ihomeCabinet.crm.model.User;
import com.ihomeCabinet.crm.tools.JwtSubject;
import com.ihomeCabinet.crm.tools.ResponseBodyObject;
import com.ihomeCabinet.crm.tools.TokenUtil;
import com.ihomeCabinet.crm.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public Map<String, Object> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 调用 UserService 中的方法进行登录验证
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            user = userService.findByUsername(userDetails.getUsername());
            JwtSubject subject = new JwtSubject(user.getUsername(), user.getRegion(), user.getEmail());
            String token = tokenUtil.generateToken(subject);
            response.put("token", token);

            List<User> users = userService.findByRegion(user.getRegion());
            List<String> coworkers = users.stream()
                    .map(User::getUsername) // 这里假设 MyObject 类有一个名为 getPropertyName 的方法来获取属性值
                    .toList();
            response.put("coworkers", coworkers);
            return response;
        } catch (AuthenticationException e) {
            response.put("message", "Invalid username or password");
            return response;
        }
/*        boolean loginSuccess = userService.login(username, password);
        if (loginSuccess) {
            User user1 = userService.findByUsername(username);
            return ResponseEntity.ok(user1);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid name or password");
        }*/
    }

    @PostMapping("/register")
    @CrossOrigin(origins = Tool.FRONTADDR)
    public ResponseEntity<String> register(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 这里可以根据需要加密密码
        userService.saveUser(user);
        return ResponseEntity.ok("注册成功");
    }

    @CrossOrigin(origins = Tool.FRONTADDR)
    @GetMapping("/region/{region}")
    public List<User> getCustomersByRegion(@PathVariable Integer region) {
        return userService.findByRegion(region);
    }

}
