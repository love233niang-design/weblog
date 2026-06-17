package com.quanxiaoha.weblog.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    // 该方法用于根据用户名加载用户信息逻辑
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 暂时先写死，密码为 hq, 这里填写的密文，数据库中也是存储此种格式
        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername("hq")
                .password("$2a$10$YexPyUwem3aIEIbK4OVBLeaS6bvew7Nk1QhE6LNaGwoRq4HakxDRu")
                .authorities("ADMIN")
                .build();
    }
}
