package com.yahibrobank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceDetails {

    @GetMapping("/myBalance")
    public String getBalanceDetails(){
        return "balance details from the DB";
    }
}
