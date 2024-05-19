package com.example.demo;

import com.example.demo.account.Account;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class MyCommands {
    private PriceService priceService;
    private AuthenticationService authenticationService;

    @ShellMethod(key = "login")
    public String login(Long id, String password){
        return authenticationService.login(id, password).toString();
    }

    @ShellMethod(key = "logout")
    public String logout(){
        authenticationService.logout();
        return "good bye";
    }

    @ShellMethod(key = "current-user")
    public String currentUser(){
        Account account = authenticationService.getCurrentAccount();
        return account != null ? account.toString() : "";
    }

    @ShellMethod(key = "city")
    public String city(){
        return priceService.cities().toString();
    }

    @ShellMethod(key = "sector")
    public String sector(String city){
        return priceService.sectors(city).toString();
    }

    @ShellMethod(key = "price")
    public String price(String city, String sector){
        return priceService.price(city, sector).toString();
    }

    @ShellMethod(key = "bill-total")
    public String billTotal(String city, String sector, int usage){
        return priceService.billTotal(city, sector, usage);
    }
}
