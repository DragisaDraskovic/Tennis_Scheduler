package com.backend.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "${payment.microservice.name}")
public interface UtilService {

    @PostMapping("/createCustomers/createCustomer")
    public void createPlayerAcc(@RequestParam("email") String email,@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName);

    @PostMapping("/transactions/payment")
    public String payment();

}
