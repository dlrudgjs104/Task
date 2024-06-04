package com.example.demo.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AppStartupRunner implements ApplicationRunner {
    private final ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
