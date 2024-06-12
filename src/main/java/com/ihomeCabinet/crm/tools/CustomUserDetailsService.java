package com.ihomeCabinet.crm.tools;

import com.ihomeCabinet.crm.model.User;
import com.ihomeCabinet.crm.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    // 注入你的 UserRepository 或者其他用于查找用户数据的依赖
     private final UserRepository userRepository;

     public CustomUserDetailsService(UserRepository userRepository) {
         this.userRepository = userRepository;
     }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查找用户，可以从数据库或其他数据源查找
        User user = userRepository.findByUsername(username);
        if (user == null) {
             throw new UsernameNotFoundException("User not found");
         }
         return new org.springframework.security.core.userdetails.User(
                 user.getUsername(), user.getPassword(), new ArrayList<>()
         );

/*        // 这里只是一个示例返回的 UserDetails，你需要根据你的用户实体来调整
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password("{noop}password") // {noop} 是一个 NoOpPasswordEncoder 用于测试，生产中请使用 BCryptPasswordEncoder 等
                .authorities("USER")
                .build();*/
    }
}
