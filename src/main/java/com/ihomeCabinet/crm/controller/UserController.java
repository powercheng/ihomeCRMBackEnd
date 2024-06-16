package com.ihomeCabinet.crm.controller;



import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ihomeCabinet.crm.model.UserDto;

import com.ihomeCabinet.crm.service.UserService;
import com.ihomeCabinet.crm.model.User;
import com.ihomeCabinet.crm.tools.JwtSubject;
import com.ihomeCabinet.crm.tools.TokenUtil;
import com.ihomeCabinet.crm.tools.Tool;
import com.ihomeCabinet.crm.tools.response.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        // 调用 UserService 中的方法进行登录验证
        boolean loginSuccess = userService.login(username, password);
        if (loginSuccess) {
            User user1 = userService.findByUsername(username);
            JwtSubject subject = new JwtSubject(user1.getId(), user1.getUsername(), user1.getRegion(), user1.getEmail());
            String token = TokenUtil.generateToken(subject);

            List<User> users = userService.findByRegion(user1.getRegion());
            List<String> coworkers = users.stream()
                    .map(User::getUsername) // 这里假设 MyObject 类有一个名为 getPropertyName 的方法来获取属性值
                    .toList();

            JSONObject jsonObject = new JSONObject();
            jsonObject.set("token", token);
            jsonObject.set("coworkers", coworkers);
            jsonObject.set("region", user1.getRegion());
            return Result.ok(jsonObject);
        } else {
            return Result.fail("Invalid username or password");
        }


    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        User user2 = userService.findByUsername(user.getUsername());
        if (userService.existsByUsername(user.getUsername())) {
            return Result.fail("username exist");
        }
        user.setSalt(Tool.generateSalt());
        user.setPassword(userService.encryptPassword(user.getPassword(), user.getSalt())); // 这里可以根据需要加密密码
        userService.saveUser(user);
        return Result.ok("注册成功");
    }

    @GetMapping("/region/{region}")
    public Result getCustomersByRegion(@PathVariable Integer region) {
        List<User> users = userService.findByRegion(region);
        return Result.ok(users);
    }

}
