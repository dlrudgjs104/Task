package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.time.LocalDateTime;

@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        DbConnectionThreadLocal.initialize();

        User admin = new User("admin", "admin", "12345", "20240501", User.Auth.ROLE_ADMIN, 1_000_000, LocalDateTime.now(), null);
        User user = new User("user", "user", "12345", "20240501", User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);

        if(userService.getUser(admin.getUserId()) == null){
            userService.saveUser(admin);
            log.info("Admin user created.");
        }

        if(userService.getUser(user.getUserId()) == null){
            userService.saveUser(user);
            log.info("Regular user created.");
        }

        DbConnectionThreadLocal.reset();
    }
}
