package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties("file")
public class Property {
    String type;
    String pricePath;
    String accountPath;
}
