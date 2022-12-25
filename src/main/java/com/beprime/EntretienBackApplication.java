package com.beprime;

import com.beprime.persistance.dao.GroupDao;
import com.beprime.persistance.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EntretienBackApplication {
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Bean
    public BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(EntretienBackApplication.class, args);
    }

}
