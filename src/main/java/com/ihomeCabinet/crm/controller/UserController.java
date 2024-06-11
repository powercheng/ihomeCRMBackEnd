package com.ihomeCabinet.crm.controller;

import com.ihomeCabinet.crm.model.Customer;
import com.ihomeCabinet.crm.service.UserService;
import com.ihomeCabinet.crm.model.User;
import com.ihomeCabinet.crm.tools.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 调用 UserService 中的方法进行登录验证
        boolean loginSuccess = userService.login(username, password);
        if (loginSuccess) {
            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<String> register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (userService.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }
        String salt = Tool.generateSalt();
        user.setSalt(salt);
        user.setPassword(userService.encryptPassword(password, salt)); // 这里可以根据需要加密密码
        userService.saveUser(user);
        return ResponseEntity.ok("注册成功");
    }

    @GetMapping("/region/{region}")
    public List<User> getCustomersByRegion(@PathVariable Integer region) {
        return userService.findByRegion(region);
    }

}
