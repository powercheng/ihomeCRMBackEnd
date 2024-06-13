package com.ihomeCabinet.crm.service;

import com.ihomeCabinet.crm.model.User;
import com.ihomeCabinet.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

/*    public boolean login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 根据数据库中的 salt 对密码进行加密
            String encryptedPassword = encryptPassword(password, user.getSalt());
            // 比较加密后的密码是否和数据库中的密码一致
            return encryptedPassword.equals(user.getPassword());
        }
        return false;
    }*/

    public String encryptPassword(String password, String salt) {
        // 自定义的加密方法，根据需求实现
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String text = password + salt;
            byte[] hash = digest.digest(text.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findByRegion(Integer region) { return userRepository.findByRegion(region); }

    public User findByUsername(String username) { return userRepository.findByUsername(username);
    }
}
